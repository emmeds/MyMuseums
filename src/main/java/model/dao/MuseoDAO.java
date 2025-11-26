package model.dao;

import model.bean.Museo;
import model.utils.ConnPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuseoDAO {

    private static final Logger LOGGER = Logger.getLogger(MuseoDAO.class.getName());

    public List<Museo> doRetrieveAll() {
        List<Museo> musei = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Museo";

        try {
            connection = ConnPool.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Museo m = new Museo();
                m.setIdMuseo(rs.getInt("ID_Museo"));
                m.setNome(rs.getString("Nome"));
                m.setDescrizione(rs.getString("Descrizione"));
                m.setImmagine(rs.getString("Immagine"));
                m.setVia(rs.getString("Via"));
                m.setCitta(rs.getString("Citta"));
                m.setCap(rs.getString("Cap"));
                m.setPrezzoTourGuidato(rs.getBigDecimal("PrezzoTourGuidato"));

                musei.add(m);
            }
            LOGGER.info("Recuperati " + musei.size() + " musei dal DB.");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei musei", e);
            throw new RuntimeException(e);
        } finally {
            // Chiudiamo le risorse manualmente come nel tuo UtenteDAO
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
        return musei;
    }

    public List<Museo> doRetrieveLimit(int limit) {
        List<Museo> musei = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Museo limit " + limit;

        try {
            connection = ConnPool.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Museo m = new Museo();
                m.setIdMuseo(rs.getInt("ID_Museo"));
                m.setNome(rs.getString("Nome"));
                m.setDescrizione(rs.getString("Descrizione"));
                m.setImmagine(rs.getString("Immagine"));
                m.setVia(rs.getString("Via"));
                m.setCitta(rs.getString("Citta"));
                m.setCap(rs.getString("Cap"));
                m.setPrezzoTourGuidato(rs.getBigDecimal("PrezzoTourGuidato"));

                musei.add(m);
            }
            LOGGER.info("Recuperati " + musei.size() + " musei dal DB.");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei musei", e);
            throw new RuntimeException(e);
        } finally {
            // Chiudiamo le risorse manualmente come nel tuo UtenteDAO
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
        return musei;

    }

    public int doSave(Museo museo) {
        String insertSQL = "INSERT INTO Museo (Nome, Descrizione, Immagine, Via, Citta, Cap, PrezzoTourGuidato) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = ConnPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, museo.getNome());
            preparedStatement.setString(2, museo.getDescrizione());
            preparedStatement.setString(3, museo.getImmagine());
            preparedStatement.setString(4, museo.getVia());
            preparedStatement.setString(5, museo.getCitta());
            preparedStatement.setString(6, museo.getCap());
            preparedStatement.setBigDecimal(7, museo.getPrezzoTourGuidato());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating museo failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating museo failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel salvataggio del museo", e);
            throw new RuntimeException(e);
        }

        return generatedId;
    }

    public Museo doRetrieveById(int idMuseo) {
        Museo museo = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Museo WHERE ID_Museo = ?";

        try {
            connection = ConnPool.getConnection();
            ps = connection.prepareStatement(query);

            // Impostiamo il parametro ID nella query (il ? diventa il valore di idMuseo)
            ps.setInt(1, idMuseo);

            rs = ps.executeQuery();

            // Usiamo if invece di while perché ci aspettiamo un solo risultato
            if (rs.next()) {
                museo = new Museo();
                museo.setIdMuseo(rs.getInt("ID_Museo"));
                museo.setNome(rs.getString("Nome"));
                museo.setDescrizione(rs.getString("Descrizione"));
                museo.setImmagine(rs.getString("Immagine"));
                museo.setVia(rs.getString("Via"));
                museo.setCitta(rs.getString("Citta"));
                museo.setCap(rs.getString("Cap"));
                museo.setPrezzoTourGuidato(rs.getBigDecimal("PrezzoTourGuidato"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero del museo id: " + idMuseo, e);
            throw new RuntimeException(e);
        } finally {
            // Chiudiamo le risorse manualmente
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
        return museo; // Ritorna l'oggetto Museo o null se non trovato
    }

    public String getSuggerimentiPerBarraRicercaPerCitta(String query) {
        List<String> suggerimenti = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT DISTINCT Citta FROM Museo WHERE Citta LIKE ? LIMIT 5";

        try {
            connection = ConnPool.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, query + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                suggerimenti.add(rs.getString("Citta"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei suggerimenti per la barra di ricerca", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }

        // Build a JSON array string (e.g. ["City1","City2"]) - escape quotes and backslashes
        if (suggerimenti.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (String s : suggerimenti) {
            if (!first) sb.append(',');
            first = false;
            // simple escape for backslashes and double quotes
            String escaped = s.replace("\\", "\\\\").replace("\"", "\\\"");
            sb.append('"').append(escaped).append('"');
        }
        sb.append(']');
        return sb.toString();

    }
}