package controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.bean.Museo;
import model.dao.MuseoDAO;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure correct encoding
        request.setCharacterEncoding("UTF-8");

        String query = request.getParameter("query");
        if (query == null) query = "";
        query = query.trim();

        // limit query length to avoid abuse
        if (query.length() > 100) query = query.substring(0, 100);

        // Log query
        logger.info("SearchServlet.doGet query='" + query + "'");

        try {
            MuseoDAO museoDao = new MuseoDAO();
            List<Museo> risultati;

            if (query.isEmpty()) {
                // when no query, show a limited set of featured/top museums
                risultati = museoDao.doRetrieveLimit(20);
            } else {
                risultati = museoDao.cercaMusei(query);
            }

            request.setAttribute("searchQuery", query);
            request.setAttribute("risultati", risultati);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/GUI/ElencoMusei.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante la ricerca per query='" + query + "'", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
