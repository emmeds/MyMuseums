package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Museo;
import model.bean.TipologiaBiglietto;
import model.dao.MuseoDAO;
import model.dao.TipologiaBigliettoDAO;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            System.out.println("HomePageServlet doGet called");
        MuseoDAO museoDAO = new MuseoDAO();
        TipologiaBigliettoDAO tipologiaBigliettoDAO = new TipologiaBigliettoDAO();
        List<Museo> musei = museoDAO.doRetrieveLimit(5);

        List<TipologiaBiglietto> tipologieRid = new ArrayList<TipologiaBiglietto>();
        for(Museo museo : musei) {
            TipologiaBiglietto tipCurr = tipologiaBigliettoDAO.doRetrieveTipologiaRidByMuseoId(museo.getIdMuseo());
            tipologieRid.add(tipCurr);
        }

        req.setAttribute("musei", musei);
        req.setAttribute("tipologieRid", tipologieRid);
        req.getRequestDispatcher("/WEB-INF/GUI/HomePage.jsp").forward(req, resp);
    }
}
