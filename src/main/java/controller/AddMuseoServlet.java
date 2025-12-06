package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.bean.Museo;
import model.bean.TipologiaBiglietto;
import model.dao.MuseoDAO;
import model.dao.TipologiaBigliettoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddMuseoServlet", value = "/AddMuseoServlet")
public class AddMuseoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddMuseoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String via = request.getParameter("via");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String descrizione = request.getParameter("descrizione");
        String immagine = request.getParameter("imageUrl");
        String sPrezzoStandard = request.getParameter("prezzoStandard");
        String sPrezzoRidotto = request.getParameter("prezzoRidotto");
        String sPrezzoSaltaFila = request.getParameter("prezzoSaltaFila");
        String sPrezzoTour = request.getParameter("prezzoTourGuidato");

        Museo museo = new Museo();
        museo.setNome(nome);
        museo.setVia(via);
        museo.setCitta(citta);
        museo.setCap(cap);
        museo.setDescrizione(descrizione);
        museo.setImmagine(immagine);
        // prezzoTour verrà parsato dentro addMuseo

        try {
            int idMuseo = addMuseo(museo, sPrezzoStandard, sPrezzoRidotto, sPrezzoSaltaFila, sPrezzoTour);
            if (idMuseo > 0) {
                String success = "Museo aggiunto con successo (id=" + idMuseo + ").";
                logger.info(success);
                request.setAttribute("successMessage", success);
            } else {
                String err = "Errore durante il salvataggio del museo.";
                logger.severe(err);
                request.setAttribute("errorMessage", err);
            }
        } catch (IllegalArgumentException iae) {
            // validation error
            logger.log(Level.WARNING, "Dati non validi: " + iae.getMessage());
            request.setAttribute("errorMessage", iae.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore interno durante il salvataggio del museo", e);
            request.setAttribute("errorMessage", "Errore interno: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp");
        rd.forward(request, response);
    }

    /**
     * Salva il museo con le relative tipologie biglietto.
     * Metodo package-private per facilità di testing.
     * Ora include anche i controlli di validità dei campi e il parsing dei prezzi.
     *
     * @param museo il museo (campi principali già impostati)
     * @param sPrezzoStandard prezzo biglietto standard (stringa da parsare)
     * @param sPrezzoRidotto prezzo biglietto ridotto (stringa da parsare)
     * @param sPrezzoSaltaFila prezzo biglietto salta la fila (stringa da parsare)
     * @param sPrezzoTour prezzo tour guidato (stringa da parsare)
     * @return ID del museo salvato (> 0 se successo, -1 se errore)
     * @throws IllegalArgumentException in caso di dati non validi
     */
    int addMuseo(Museo museo, String sPrezzoStandard, String sPrezzoRidotto, String sPrezzoSaltaFila, String sPrezzoTour) {
        // controlli sui campi obbligatori
        if (museo == null
                || isNullOrEmpty(museo.getNome())
                || isNullOrEmpty(museo.getVia())
                || isNullOrEmpty(museo.getCitta())
                || isNullOrEmpty(museo.getCap())
                || isNullOrEmpty(museo.getDescrizione())
                || isNullOrEmpty(museo.getImmagine())
                || isNullOrEmpty(sPrezzoStandard)
                || isNullOrEmpty(sPrezzoRidotto)
                || isNullOrEmpty(sPrezzoSaltaFila)
                || isNullOrEmpty(sPrezzoTour)) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }

        BigDecimal prezzoStandard, prezzoRidotto, prezzoSaltaFila, prezzoTour;
        try {
            prezzoStandard = new BigDecimal(sPrezzoStandard.trim());
            prezzoRidotto = new BigDecimal(sPrezzoRidotto.trim());
            prezzoSaltaFila = new BigDecimal(sPrezzoSaltaFila.trim());
            prezzoTour = new BigDecimal(sPrezzoTour.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Prezzi non validi", e);
            throw new IllegalArgumentException("I prezzi inseriti non sono validi.");
        }

        museo.setPrezzoTourGuidato(prezzoTour);

        MuseoDAO museoDAO = createMuseoDAO();
        int idMuseo = museoDAO.doSave(museo);

        if (idMuseo > 0) {
            TipologiaBigliettoDAO tipologiaBigliettoDAO = createTipologiaBigliettoDAO();

            TipologiaBiglietto t1 = new TipologiaBiglietto();
            t1.setNome("Standard");
            t1.setIdMuseo(idMuseo);
            t1.setPrezzo(prezzoStandard);
            tipologiaBigliettoDAO.doSaveTipologiaBiglietto(t1);

            TipologiaBiglietto t2 = new TipologiaBiglietto();
            t2.setNome("Ridotto");
            t2.setIdMuseo(idMuseo);
            t2.setPrezzo(prezzoRidotto);
            tipologiaBigliettoDAO.doSaveTipologiaBiglietto(t2);

            TipologiaBiglietto t3 = new TipologiaBiglietto();
            t3.setNome("Salta la Fila");
            t3.setIdMuseo(idMuseo);
            t3.setPrezzo(prezzoSaltaFila);
            tipologiaBigliettoDAO.doSaveTipologiaBiglietto(t3);
        }

        return idMuseo;
    }

    // Factory methods - protected so tests can override or extend the servlet to inject mocks
    protected MuseoDAO createMuseoDAO() {
        return new MuseoDAO();
    }

    protected TipologiaBigliettoDAO createTipologiaBigliettoDAO() {
        return new TipologiaBigliettoDAO();
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
