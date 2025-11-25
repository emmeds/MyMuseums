package controller; // Usa il tuo package corretto

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Questa annotazione crea l'indirizzo pubblico "/faq"
@WebServlet("/faq")
public class FaqServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Il "Sistema" riceve la richiesta e mostra la pagina
        // Assicurati che il percorso "/WEB-INF/GUI/FAQ.jsp" sia corretto rispetto alla tua cartella
        request.getRequestDispatcher("/WEB-INF/GUI/faq/faq.jsp").forward(request, response);
    }
}
