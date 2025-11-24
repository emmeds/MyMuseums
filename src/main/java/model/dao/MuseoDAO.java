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
                m.setPrezzoTourGuidato(rs.getDouble("PrezzoTourGuidato"));

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
                m.setPrezzoTourGuidato(rs.getDouble("PrezzoTourGuidato"));

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
}