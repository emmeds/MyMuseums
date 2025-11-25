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
        // Mostriamo la pagina di inserimento (se necessario)
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

        // Validazione minima server-side
        if (isNullOrEmpty(nome) || isNullOrEmpty(via) || isNullOrEmpty(citta) || isNullOrEmpty(cap)
                || isNullOrEmpty(descrizione) || isNullOrEmpty(immagine)
                || isNullOrEmpty(sPrezzoStandard) || isNullOrEmpty(sPrezzoRidotto) || isNullOrEmpty(sPrezzoSaltaFila) || isNullOrEmpty(sPrezzoTour)) {
            request.setAttribute("errorMessage", "Tutti i campi sono obbligatori.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp");
            rd.forward(request, response);
            return;
        }

        BigDecimal prezzoStandard, prezzoRidotto, prezzoSaltaFila, prezzoTour;
        try {
            prezzoStandard = new BigDecimal(sPrezzoStandard);
            prezzoRidotto = new BigDecimal(sPrezzoRidotto);
            prezzoSaltaFila = new BigDecimal(sPrezzoSaltaFila);
            prezzoTour = new BigDecimal(sPrezzoTour);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Prezzi non validi", e);
            request.setAttribute("errorMessage", "I prezzi inseriti non sono validi.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp");
            rd.forward(request, response);
            return;
        }

        Museo museo = new Museo();
        MuseoDAO museoDAO = new MuseoDAO();
        museo.setNome(nome);
        museo.setVia(via);
        museo.setCitta(citta);
        museo.setCap(cap);
        museo.setDescrizione(descrizione);
        museo.setImmagine(immagine);
        museo.setPrezzoTourGuidato(prezzoTour);

        try {
            int idMuseo = museoDAO.doSave(museo);
            if (idMuseo > 0) {
                TipologiaBigliettoDAO tipologiaBigliettoDAO = new TipologiaBigliettoDAO();

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
                t3.setNome("SaltaFila");
                t3.setIdMuseo(idMuseo);
                t3.setPrezzo(prezzoSaltaFila);
                tipologiaBigliettoDAO.doSaveTipologiaBiglietto(t3);

                String success = "Museo aggiunto con successo (id=" + idMuseo + ").";
                logger.info(success);
                request.setAttribute("successMessage", success);
            } else {
                String err = "Errore durante il salvataggio del museo.";
                logger.severe(err);
                request.setAttribute("errorMessage", err);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore interno durante il salvataggio del museo", e);
            request.setAttribute("errorMessage", "Errore interno: " + e.getMessage());
        }

        // Forward alla pagina admin per mostrare il messaggio
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp");
        rd.forward(request, response);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
