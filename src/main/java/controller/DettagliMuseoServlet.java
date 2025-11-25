package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Museo;
import model.bean.TipologiaBiglietto;
import model.dao.MuseoDAO;
import model.dao.TipologiaBigliettoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/DettagliMuseoServlet")
public class DettagliMuseoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        MuseoDAO museoDAO = new MuseoDAO();
        TipologiaBigliettoDAO tipologiaBigliettoDAO = new TipologiaBigliettoDAO();


        if(idStr != null) {
            try{
                int id = Integer.parseInt(idStr);

                Museo museoSelezionato = museoDAO.doRetrieveById(id);
                List<TipologiaBiglietto> tipologie = tipologiaBigliettoDAO.doRetrieveByMuseoId(id);

                req.setAttribute("museo", museoSelezionato);
                req.setAttribute("listaTipologie", tipologie);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/GUI/DettaglioMuseo.jsp");
                dispatcher.forward(req, resp);

            }catch (NumberFormatException e){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
