package controller;

import jakarta.servlet.RequestDispatcher;
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
import model.dao.PortafoglioDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/PortafoglioServlet")
public class PortafoglioServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/GUI/Portafoglio.jsp");
        PortafoglioDAO portafoglioDAO = new PortafoglioDAO();

        //Controlla se l'utente è loggato
        if(session != null && session.getAttribute("utente") != null) {

            Utente utenteLoggato = (Utente) session.getAttribute("utente");

            int idUtente = utenteLoggato.getIdUtente();

            List<Ordine> ordini = portafoglioDAO.doRetrieveOrdersById(idUtente);


            for(Ordine ordine : ordini) {
                List<Biglietto> biglietti = portafoglioDAO.doRetrieveBigliettiById(ordine.getIdOrdine());
                ordine.setBiglietti(biglietti);

                for(Biglietto biglietto : biglietti) {
                    // Chiamo il metodo che ora restituisce un SINGOLO oggetto
                    TipologiaBiglietto tipologia = portafoglioDAO.doRetrieveTipologiaById(biglietto.getIdTipologia());

                    // Aggiungo la singola tipologia alla lista dell'ordine
                    if (tipologia != null) {
                        ordine.getTipologie().add(tipologia);
                    }
                }
            }

            request.setAttribute("ordini", ordini);

            dispatcher.forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
