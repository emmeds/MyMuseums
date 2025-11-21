package model.dao;

import model.bean.Utente;
import model.bean.UtenteRegistrato;
import model.utils.ConnPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {

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
            e.printStackTrace();
            throw new RuntimeException("Errore durante il login: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
