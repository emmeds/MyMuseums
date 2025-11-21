package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostra la pagina di registrazione
        request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gestisce la registrazione
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");

        // TODO: Implementare la logica di registrazione
        // Validare i dati
        // Salvare l'utente nel database

        // Per ora reindirizza al login
        response.sendRedirect(request.getContextPath() + "/login");
    }
}

