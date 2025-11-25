package model.dao;

import model.bean.Utente;
import model.bean.UtenteRegistrato;

import model.utils.ConnPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDAO {

    private static final Logger LOGGER = Logger.getLogger(UtenteDAO.class.getName());

    public Utente doLogin(String email, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ConnPool.getConnection();

            String query = "SELECT * FROM Utente WHERE Email = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {

                String hashedPassword = rs.getString("Password");

                if (BCrypt.checkpw(password, hashedPassword)) {

                    String ruolo = rs.getString("Ruolo");
                    Utente utente = model.factory.UtenteFactory.getUtente(ruolo);

                    if (utente != null) {

                        utente.setIdUtente(rs.getInt("ID_Utente"));
                        utente.setEmail(rs.getString("Email"));
                        utente.setPassword(hashedPassword);

                        if (ruolo.equalsIgnoreCase("REGISTRATO") && utente instanceof UtenteRegistrato) {
                            UtenteRegistrato u = (UtenteRegistrato) utente;
                            u.setNome(rs.getString("Nome"));
                            u.setCognome(rs.getString("Cognome"));
                        }

                        return utente;
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il login", e);
            throw new RuntimeException("Errore durante il login: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore durante la chiusura delle risorse JDBC", e);
            }
        }
    }
    public Utente addUtente(String nome, String cognome, String email, String password) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = ConnPool.getConnection();

            // Hash della password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Inserimento nel DB
            String query = "INSERT INTO Utente (Nome, Cognome, Email, Password, Ruolo) VALUES (?, ?, ?, ?, 'REGISTRATO')";
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, hashedPassword);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creazione utente fallita, nessuna riga inserita.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);

                    // Creo l'utente tramite Factory
                    Utente utente = model.factory.UtenteFactory.getUtente("REGISTRATO");
                    if (utente instanceof UtenteRegistrato) {
                        UtenteRegistrato newUser = (UtenteRegistrato) utente;
                        newUser.setIdUtente(newId);
                        newUser.setEmail(email);
                        newUser.setPassword(hashedPassword);
                        newUser.setNome(nome);
                        newUser.setCognome(cognome);
                        return newUser;
                    } else {
                        throw new RuntimeException("Errore: Factory non ha restituito un UtenteRegistrato.");
                    }

                } else {
                    throw new SQLException("Creazione utente fallita, nessun ID ottenuto.");
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la creazione dell'utente", e);
            throw new RuntimeException("Errore durante la creazione dell'utente: " + e.getMessage(), e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore durante la chiusura delle risorse JDBC", e);
            }
        }
    }

    // Nuovo metodo: controlla se un'email è già presente nel DB
    public boolean emailExists(String email) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ConnPool.getConnection();
            String query = "SELECT 1 FROM Utente WHERE Email = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la verifica email", e);
            throw new RuntimeException("Errore durante la verifica email: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore durante la chiusura delle risorse JDBC", e);
            }
        }
    }

}
