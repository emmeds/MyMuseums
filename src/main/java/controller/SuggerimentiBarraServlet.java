package controller;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.dao.MuseoDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SuggerimentiBarraServlet", value = "/SuggerimentiBarraServlet")
public class SuggerimentiBarraServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SuggerimentiBarraServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Ensure proper encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }

        // normalize and limit query length to avoid abuse
        query = query.trim();
        if (query.length() > 100) {
            query = query.substring(0, 100);
        }

        // Log query (non-lambda to avoid effectively-final capture issues)
        logger.info("SuggerimentiBarraServlet.doGet query='" + query + "'");

        // Ensure response encoding explicitly set
        response.setCharacterEncoding("UTF-8");

        MuseoDAO museoDAO = new MuseoDAO();
        try (PrintWriter out = response.getWriter()) {
            String suggerimenti = museoDAO.getSuggerimentiPerBarraRicercaPerCitta(query);

            // If DAO returns null or empty, return empty JSON array
            if (suggerimenti == null || suggerimenti.trim().isEmpty()) {
                suggerimenti = "[]";
            }

            out.write(suggerimenti);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante recupero suggerimenti per query='" + query + "'", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.write("[]");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Delegate POST to GET to allow flexible AJAX methods
        doGet(request, response);
    }
}
