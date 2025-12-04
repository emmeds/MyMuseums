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

    private static final Logger LOGGER = Logger.getLogger(RegistrazioneServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.FINE);
        LOGGER.info("RegistrazioneServlet inizializzato, livello di log impostato a FINE");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.fine("Mostro pagina di registrazione (doGet)");
        request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("RegistrazioneServlet: doPost called");

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");

        LOGGER.fine(() -> String.format("Parametri ricevuti: nome='%s', cognome='%s', email='%s'", nome, cognome, email));

        try {
            // Utilizzo del metodo estratto per la registrazione
            Utente utente = registerUser(nome, cognome, email, password, confermaPassword);

            LOGGER.info("Utente registrato correttamente: email=" + email);
            request.setAttribute("successMessage", "Registrazione avvenuta con successo. Effettua il login.");
            request.getRequestDispatcher("/WEB-INF/GUI/auth/login.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            LOGGER.warning("Registrazione fallita: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione per email=" + email, e);
            request.setAttribute("errorMessage", "Errore durante la registrazione: " + e.getMessage());
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/GUI/auth/registrazione.jsp").forward(request, response);
        }
    }

    /**
     * Metodo estratto per la logica di registrazione.
     * Facilita il testing con Category Partition isolando la business logic.
     * Tutte le validazioni sono contenute in questo metodo.
     *
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param email Email dell'utente
     * @param password Password dell'utente
     * @param confermaPassword Conferma password
     * @return Utente creato
     * @throws IllegalArgumentException se i parametri non sono validi
     * @throws RuntimeException se si verifica un errore durante la creazione dell'utente
     */
    protected Utente registerUser(String nome, String cognome, String email,
                                   String password, String confermaPassword) {

        // Validazione campi obbligatori - Category Partition: null, empty, whitespace, validi
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il campo nome è obbligatorio");
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il campo cognome è obbligatorio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Il campo email è obbligatorio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Il campo password è obbligatorio");
        }
        if (confermaPassword == null || confermaPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Il campo conferma password è obbligatorio");
        }

        // Validazione formato email - Category Partition: formato valido, formato invalido
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato email non valido");
        }

        // Validazione corrispondenza password - Category Partition: corrispondono, non corrispondono
        if (!password.equals(confermaPassword)) {
            throw new IllegalArgumentException("Le password non corrispondono");
        }

        // Validazione policy password
        // Category Partition: Lunghezza (<8, >=8), Maiuscola (presente, assente),
        //                     Numero (presente, assente), Carattere speciale (presente, assente)
        if (password.length() < 8) {
            throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La password deve contenere almeno una lettera maiuscola");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La password deve contenere almeno un numero");
        }
        if (!password.matches(".*[^A-Za-z0-9].*")) {
            throw new IllegalArgumentException("La password deve contenere almeno un carattere speciale");
        }

        // Verifica esistenza email e creazione utente
        LOGGER.fine("Chiamo UtenteDAO.emailExists per verificare duplicati");
        UtenteDAO utenteDAO = new UtenteDAO();

        if (utenteDAO.emailExists(email)) {
            LOGGER.info("Tentativo di registrazione con email già esistente: " + email);
            throw new IllegalArgumentException("Questa email è già associata ad un account");
        }

        LOGGER.fine("Chiamo UtenteDAO.addUtente per creare l'utente");
        return utenteDAO.addUtente(nome, cognome, email, password);
    }
}
