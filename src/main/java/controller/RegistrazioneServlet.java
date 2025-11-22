package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Utente;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    // Aggiunto logger per tracciare eventi e errori
    private static final Logger LOGGER = Logger.getLogger(RegistrazioneServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        // Imposta il livello del logger per questo servlet.
        LOGGER.setLevel(Level.FINE);
        LOGGER.info("RegistrazioneServlet inizializzato, livello di log impostato a FINE");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostra la pagina di registrazione
        LOGGER.fine("Mostro pagina di registrazione (doGet)");
        request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("RegistrazioneServlet: doPost called");

        // Recupera i parametri dal form
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");

        // Log dei parametri non sensibili
        LOGGER.fine(() -> String.format("Parametri ricevuti: nome='%s', cognome='%s', email='%s'", nome, cognome, email));

        // Validazioni lato server
        if (nome == null || nome.trim().isEmpty() ||
                cognome == null || cognome.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confermaPassword == null || confermaPassword.trim().isEmpty()) {

            LOGGER.warning("Validazione fallita: campi obbligatori mancanti");
            request.setAttribute("errorMessage", "Tutti i campi sono obbligatori");
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
            return;
        }

        // Controllo formato email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            LOGGER.warning("Validazione fallita: formato email non valido per email=" + email);
            request.setAttribute("errorMessage", "Formato email non valido");
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
            return;
        }

        // Controllo conferma password
        if (!password.equals(confermaPassword)) {
            LOGGER.warning("Validazione fallita: password e confermaPassword non corrispondono per email=" + email);
            request.setAttribute("errorMessage", "Le password non corrispondono");
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
            return;
        }

        try {
            LOGGER.fine("Chiamo UtenteDAO.emailExists per verificare duplicati");
            UtenteDAO utenteDAO = new UtenteDAO();

            if (utenteDAO.emailExists(email)) {
                LOGGER.info("Tentativo di registrazione con email già esistente: " + email);
                request.setAttribute("errorMessage", "Questa email è già associata ad un account");
                request.setAttribute("nome", nome);
                request.setAttribute("cognome", cognome);
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
                return;
            }

            LOGGER.fine("Chiamo UtenteDAO.addUtente per creare l'utente");
            Utente utente = utenteDAO.addUtente(nome, cognome, email, password);

            LOGGER.info("Utente registrato correttamente: email=" + email + ", utente=" + (utente != null ? utente.toString() : "null"));

            // Impostiamo un messaggio di successo e forwards al login
            request.setAttribute("successMessage", "Registrazione avvenuta con successo. Effettua il login.");
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);

        } catch (RuntimeException e) {
            // Se c'è un errore durante l'inserimento nel DB (es. email già esistente)
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione per email=" + email, e);
            request.setAttribute("errorMessage", "Errore durante la registrazione: " + e.getMessage());
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
        }
    }

}
