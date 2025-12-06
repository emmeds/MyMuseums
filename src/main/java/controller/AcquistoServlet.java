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
import java.time.LocalDate;
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
            handleRiepilogo(req, resp);
        }
    }

    // --- FASE 1: Gestione HTTP (Controller Layer) ---
    private void handleRiepilogo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            req.setAttribute("errorMessage", "Devi effettuare il login per acquistare");
            req.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(req, resp);
            return;
        }

        // Recupero parametri grezzi
        String idMuseoStr = req.getParameter("idMuseo");
        String dataVisitaStr = req.getParameter("dataVisita");
        String[] idTipologie = req.getParameterValues("idTipologia");
        String[] quantitaStr = req.getParameterValues("quantita");
        String orarioVisita = req.getParameter("orarioVisita");
        boolean tourGuidato = req.getParameter("tourGuidato") != null;

        int idMuseo = -1;
        try {
            if (idMuseoStr != null) idMuseo = Integer.parseInt(idMuseoStr);

            // CHIAMATA AL METODO ISOLATO (Logic Layer)
            Ordine ordineTemp = elaboraCreazioneOrdine(utente, idMuseo, dataVisitaStr, idTipologie, quantitaStr, tourGuidato, orarioVisita);

            // Se successo: recupero nome museo per UI e salvo in sessione
            Museo museo = getMuseoDAO().doRetrieveById(idMuseo); // Solo per il nome da mostrare
            session.setAttribute("ordineInCorso", ordineTemp);
            session.setAttribute("nomeMuseoCheckout", museo.getNome());

            // Messaggio opzionale per il tour
            if (tourGuidato) {
                req.setAttribute("msgTour", "Tour guidato incluso.");
            }

            req.getRequestDispatcher("/WEB-INF/GUI/checkout.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            // Errori di validazione (input utente) gestiti tornando alla pagina museo
            redirectError(req, resp, idMuseo, e.getMessage());
        } catch (Exception e) {
            // Errori di sistema
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore elaborazione richiesta");
        }
    }

    /**
     * METODO DA TESTARE CON CATEGORY PARTITION.
     * Isola completamente la logica di validazione e calcolo dell'ordine.
     * Non dipende da HttpServletRequest/Response.
     * Usa i DAO factory methods per permettere il mocking.
     */
    protected Ordine elaboraCreazioneOrdine(Utente utente, int idMuseo, String dataVisitaStr,
                                            String[] idTipologie, String[] quantitaStr,
                                            boolean tourGuidato, String orarioVisita) {

        // 1. Validazione ID Museo
        if (idMuseo <= 0) {
            throw new IllegalArgumentException("ID Museo non valido");
        }

        // 2. Validazione Data
        if (dataVisitaStr == null || dataVisitaStr.isEmpty()) {
            throw new IllegalArgumentException("Seleziona una data per la visita.");
        }
        LocalDate dataVisita;
        try {
            dataVisita = LocalDate.parse(dataVisitaStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato data non valido.");
        }

        if (dataVisita.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data non può essere nel passato.");
        }

        // 3. Validazione Input Array
        if (idTipologie == null || quantitaStr == null || idTipologie.length != quantitaStr.length) {
            throw new IllegalArgumentException("Dati biglietti non validi o mancanti.");
        }

        // 4. Recupero dati DB tramite Factory Methods (Mockabili)
        Museo museo = getMuseoDAO().doRetrieveById(idMuseo);
        if (museo == null) {
            throw new IllegalArgumentException("Museo non trovato.");
        }
        List<TipologiaBiglietto> tutteTipologie = getTipologiaBigliettoDAO().doRetrieveByMuseoId(idMuseo);

        // 5. Calcolo Totali e Costruzione Liste
        Ordine ordineTemp = new Ordine();
        ordineTemp.setIdUtente(utente.getIdUtente());
        List<Biglietto> bigliettiSelezionati = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        int totaleBiglietti = 0;

        for (int i = 0; i < idTipologie.length; i++) {
            int qta = parseMsg(quantitaStr[i]);
            int idTipo = Integer.parseInt(idTipologie[i]);

            if (qta > 0) {
                // Trova la tipologia corrispondente verificando che appartenga al museo
                TipologiaBiglietto tipoReale = tutteTipologie.stream()
                        .filter(t -> t.getIdTipologia() == idTipo)
                        .findFirst()
                        .orElse(null);

                if (tipoReale == null) {
                    throw new IllegalArgumentException("Tipologia biglietto non valida per questo museo.");
                }

                // Calcolo parziale
                BigDecimal costoRiga = tipoReale.getPrezzo().multiply(new BigDecimal(qta));
                totale = totale.add(costoRiga);

                // Creazione oggetti biglietto
                Biglietto b = new Biglietto();
                b.setDataVisita(dataVisita);
                b.setQuantita(qta);
                b.setIdTipologia(idTipo);
                bigliettiSelezionati.add(b);

                totaleBiglietti += qta;
            }
        }

        // 6. Validazione Quantità Minima
        if (bigliettiSelezionati.isEmpty()) {
            throw new IllegalArgumentException("Seleziona almeno un biglietto.");
        }

        // 7. Calcolo Tour Guidato
        if (tourGuidato) {
            if (museo.getPrezzoTourGuidato() != null) {
                BigDecimal costoTour = museo.getPrezzoTourGuidato().multiply(new BigDecimal(totaleBiglietti));
                totale = totale.add(costoTour);
            }
        }

        // 8. Finalizzazione Oggetto
        ordineTemp.setImportoTotale(totale);
        ordineTemp.setBiglietti(bigliettiSelezionati);
        ordineTemp.setOrarioVisita(orarioVisita);

        return ordineTemp;
    }

    // --- FASE 2: Pagamento (invariata ma usa i factory) ---
    private void handlePagamento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Ordine ordine = (Ordine) session.getAttribute("ordineInCorso");

        if (ordine == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String metodo = req.getParameter("metodoPagamento");

        if ("paypal".equals(metodo)) {
            salvaOrdine(ordine, session, req, resp);
            return;
        }

        // Validazione Carta Semplificata
        String numeroCarta = req.getParameter("numeroCarta");
        String cvc = req.getParameter("cvc");

        boolean cartaOk = (numeroCarta != null && numeroCarta.matches("\\d{16}"));
        boolean cvcOk = (cvc != null && cvc.matches("\\d{3}"));

        if (cartaOk && cvcOk) {
            salvaOrdine(ordine, session, req, resp);
        } else {
            req.setAttribute("error", "Dati carta non validi.");
            req.getRequestDispatcher("/WEB-INF/GUI/checkout.jsp").forward(req, resp);
        }
    }

    private void salvaOrdine(Ordine ordine, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = getOrdineDAO().doSave(ordine); // Usa factory
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

    // --- Metodi Factory Protected per Mocking ---
    protected MuseoDAO getMuseoDAO() {
        return new MuseoDAO();
    }

    protected TipologiaBigliettoDAO getTipologiaBigliettoDAO() {
        return new TipologiaBigliettoDAO();
    }

    protected OrdineDAO getOrdineDAO() {
        return new OrdineDAO();
    }

    // --- Helper Utils ---
    private void redirectError(HttpServletRequest req, HttpServletResponse resp, int idMuseo, String msg) throws ServletException, IOException {
        req.setAttribute("errorMessage", msg);
        // Fallback se idMuseo non valido, rimanda alla home o gestisci come preferisci
        String target = (idMuseo > 0) ? "/DettagliMuseoServlet?id=" + idMuseo : "/index.jsp";
        req.getRequestDispatcher(target).forward(req, resp);
    }

    private int parseMsg(String num) {
        try { return Integer.parseInt(num); } catch (Exception e) { return 0; }
    }
}