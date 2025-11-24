package controller;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Museo;
import model.dao.MuseoDAO;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            System.out.println("HomePageServlet doGet called");
        MuseoDAO museoDAO = new MuseoDAO();
        List<Museo> musei = museoDAO.doRetrieveLimit(5);
        req.setAttribute("musei", musei);
        req.getRequestDispatcher("/WEB-INF/GUI/HomePage.jsp").forward(req, resp);
    }
}
