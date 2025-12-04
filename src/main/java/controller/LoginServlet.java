package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Admin;
import model.bean.Utente;
import model.bean.UtenteRegistrato;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostra la pagina di login
        request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera i parametri del form
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        LOGGER.fine(() -> "Login attempt for email=" + email);

        // Validazione input lato server
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "L'email è obbligatoria");
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "La password è obbligatoria");
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
            return;
        }

        // Validazione formato email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("errorMessage", "Formato email non valido");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
            return;
        }

        try {
            Utente utente = doLogin(email, password);

            if (utente != null) {
                // Login riuscito - Crea la sessione
                HttpSession session = request.getSession(true);

                // Salva l'utente nella sessione
                session.setAttribute("utente", utente);
                session.setAttribute("idUtente", utente.getIdUtente());
                session.setAttribute("emailUtente", utente.getEmail());

                // Se è un utente registrato, salva anche nome e cognome
                if (utente instanceof UtenteRegistrato) {
                    UtenteRegistrato utenteReg = (UtenteRegistrato) utente;
                    session.setAttribute("nomeUtente", utenteReg.getNome());
                    session.setAttribute("cognomeUtente", utenteReg.getCognome());
                    session.setAttribute("ruolo", "REGISTRATO");
                } else {
                    session.setAttribute("ruolo", "ADMIN");
                    request.getRequestDispatcher("/WEB-INF/GUI/admin/admin.jsp").forward(request, response);
                    return;
                }


                LOGGER.info("Login riuscito per email=" + email + " idUtente=" + utente.getIdUtente());

                // Redirect alla home page
                response.sendRedirect(request.getContextPath() + "/");

            } else {
                // Login fallito - Email o password errati
                LOGGER.info("Login fallito per email=" + email);
                request.setAttribute("errorMessage", "Email o password non corretti");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
            }

        } catch (RuntimeException e) {
            // Errore durante il login
            LOGGER.log(Level.SEVERE, "Errore durante login per email=" + email, e);
            request.setAttribute("errorMessage", "Errore del server. Riprova più tardi.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);
        }
    }

    /**
     * Effettua il login dell'utente verificando email e password.
     * Metodo package-private per facilità di testing.
     *
     * @param email email dell'utente
     * @param password password dell'utente
     * @return Utente se login riuscito, null se credenziali errate
     */
    Utente doLogin(String email, String password) {
        UtenteDAO utenteDAO = new UtenteDAO();
        return utenteDAO.doLogin(email, password);
    }
}
