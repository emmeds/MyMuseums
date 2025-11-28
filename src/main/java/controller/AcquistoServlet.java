package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Biglietto;
import model.bean.Ordine;
import model.bean.TipologiaBiglietto;
import model.bean.Utente;
import model.dao.MuseoDAO;
import model.dao.OrdineDAO;
import model.dao.TipologiaBigliettoDAO;
import model.bean.Museo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AcquistoServlet")
public class AcquistoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("paga".equals(action)) {
            handlePagamento(req, resp);
        } else {
            // Default: gestisce la selezione biglietti e mostra il riepilogo
            handleRiepilogo(req, resp);
        }
    }

    // --- FASE 1: Calcolo e Validazione Prenotazione ---
    private void handleRiepilogo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            String errorMsg = "Devi effettuare il login per acquistare";
            req.setAttribute("errorMessage", errorMsg);
            req.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(req, resp);
            return;
        }

        int idMuseo = -1;
        try {
            String idMuseoStr = req.getParameter("idMuseo");
            if (idMuseoStr == null) throw new IllegalArgumentException("ID Museo mancante");
            idMuseo = Integer.parseInt(idMuseoStr);

            // Validazione Data
            String dataVisitaStr = req.getParameter("dataVisita");
            if (dataVisitaStr == null || dataVisitaStr.isEmpty()) {
                redirectError(req, resp, idMuseo, "Seleziona una data per la visita.");
                return;
            }
            LocalDate dataVisita = LocalDate.parse(dataVisitaStr);
            if (dataVisita.isBefore(LocalDate.now())) {
                redirectError(req, resp, idMuseo, "La data non può essere nel passato.");
                return;
            }

            // Recupero Dati Form
            String[] idTipologie = req.getParameterValues("idTipologia");
            String[] quantitaStr = req.getParameterValues("quantita");
            boolean tourGuidato = req.getParameter("tourGuidato") != null;

            if (idTipologie == null || quantitaStr == null) {
                redirectError(req, resp, idMuseo, "Dati biglietti non validi.");
                return;
            }

            // Recupero Info DB
            MuseoDAO museoDAO = new MuseoDAO();
            Museo museo = museoDAO.doRetrieveById(idMuseo);
            TipologiaBigliettoDAO tipologiaDAO = new TipologiaBigliettoDAO();
            List<TipologiaBiglietto> tutteTipologie = tipologiaDAO.doRetrieveByMuseoId(idMuseo);

            // Costruzione Ordine Temporaneo
            Ordine ordineTemp = new Ordine();
            ordineTemp.setIdUtente(utente.getIdUtente());
            List<Biglietto> bigliettiSelezionati = new ArrayList<>();
            BigDecimal totale = BigDecimal.ZERO;
            int totaleBiglietti = 0;

            for (int i = 0; i < idTipologie.length; i++) {
                int qta = parseMsg(quantitaStr[i]);
                int idTipo = Integer.parseInt(idTipologie[i]);

                if (qta > 0) {
                    TipologiaBiglietto tipoReale = tutteTipologie.stream()
                            .filter(t -> t.getIdTipologia() == idTipo).findFirst().orElse(null);

                    if (tipoReale == null) {
                        redirectError(req, resp, idMuseo, "Tipologia biglietto non valida.");
                        return;
                    }

                    totale = totale.add(tipoReale.getPrezzo().multiply(new BigDecimal(qta)));

                    Biglietto b = new Biglietto();
                    b.setDataVisita(dataVisita);
                    b.setQuantita(qta);
                    b.setIdTipologia(idTipo);
                    bigliettiSelezionati.add(b);
                    totaleBiglietti += qta;
                }
            }

            if (bigliettiSelezionati.isEmpty()) {
                redirectError(req, resp, idMuseo, "Seleziona almeno un biglietto.");
                return;
            }

            if (tourGuidato) {
                BigDecimal costoTour = museo.getPrezzoTourGuidato().multiply(new BigDecimal(totaleBiglietti));
                totale = totale.add(costoTour);
                req.setAttribute("msgTour", "Tour guidato incluso per " + totaleBiglietti + " persone");
            }

            ordineTemp.setImportoTotale(totale);
            ordineTemp.setBiglietti(bigliettiSelezionati);

            // Salvataggio in sessione e forward al Checkout
            session.setAttribute("ordineInCorso", ordineTemp);
            session.setAttribute("nomeMuseoCheckout", museo.getNome());

            req.getRequestDispatcher("/WEB-INF/GUI/checkout.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore elaborazione richiesta");
        }
    }

    // --- FASE 2: Pagamento e Salvataggio ---
    private void handlePagamento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Ordine ordine = (Ordine) session.getAttribute("ordineInCorso");

        if (ordine == null) {
            resp.sendRedirect(req.getContextPath() + "/"); // Sessione scaduta o accesso diretto
            return;
        }

        String metodo =  req.getParameter("metodoPagamento");

        // CASO 1: PAYPAL -> Salva e basta
        if ("paypal".equals(metodo)) {
            salvaOrdine(ordine, session, req, resp);
            return;
        }

        String intestatario = req.getParameter("intestatario");
        String numeroCarta = req.getParameter("numeroCarta");
        String cvc = req.getParameter("cvc");
        String scadenza = req.getParameter("scadenza");

        boolean cartaOk = (numeroCarta != null && numeroCarta.matches("\\d{16}"));
        boolean cvcOk = (cvc != null && cvc.matches("\\d{3}"));
        boolean datiPresenti = (intestatario != null && !intestatario.isEmpty() && scadenza != null && !scadenza.isEmpty());

        // Validazione Pagamento (Mock)
        if (cartaOk && cvcOk && datiPresenti) {
            salvaOrdine(ordine, session, req, resp);
        } else {
            req.setAttribute("error", "Dati carta non validi (controlla le 16 cifre).");
            req.getRequestDispatcher("/WEB-INF/GUI/checkout.jsp").forward(req, resp);
        }
    }

    //funzione di utilità per salvare
    private void salvaOrdine(Ordine ordine, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OrdineDAO dao = new OrdineDAO();
            int id = dao.doSave(ordine);

            session.removeAttribute("ordineInCorso");
            session.removeAttribute("nomeMuseoCheckout");

            req.setAttribute("idOrdine", id);
            req.getRequestDispatcher("/WEB-INF/GUI/OrdineConfermato.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Errore nel salvataggio ordine.");
            req.getRequestDispatcher("/WEB-INF/GUI/checkout.jsp").forward(req, resp);
        }
    }

    private void redirectError(HttpServletRequest req, HttpServletResponse resp, int idMuseo, String msg) throws ServletException, IOException {
        req.setAttribute("errorMessage", msg);
        req.getRequestDispatcher("/DettagliMuseoServlet?id=" + idMuseo).forward(req, resp);
    }

    private int parseMsg(String num) {
        try { return Integer.parseInt(num); } catch(Exception e) { return 0; }
    }
}