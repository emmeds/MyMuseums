package model.dao;

import model.bean.Biglietto;
import model.bean.Ordine;
import model.utils.ConnPool;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdineDAO {
    private static final Logger LOGGER = Logger.getLogger(OrdineDAO.class.getName());

    public int doSave(Ordine ordine) {
        Connection connection = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psBiglietto = null;
        int idOrdineGenerato = -1;

        String queryOrdine = "INSERT INTO Ordine (DataAcquisto, ImportoTotale, ID_Utente) VALUES (CURRENT_TIMESTAMP, ?, ?)";
        String queryBiglietto = "INSERT INTO Biglietto (DataVisita, Quantita, ID_Ordine, ID_Tipologia) VALUES (?, ?, ?, ?)";

        try {
            connection = ConnPool.getConnection();
            connection.setAutoCommit(false); // Inizio Transazione

            // 1. Salva l'Ordine
            psOrdine = connection.prepareStatement(queryOrdine, Statement.RETURN_GENERATED_KEYS);
            psOrdine.setBigDecimal(1, ordine.getImportoTotale());
            psOrdine.setInt(2, ordine.getIdUtente());

            int affectedRows = psOrdine.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creazione ordine fallita.");

            try (ResultSet generatedKeys = psOrdine.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idOrdineGenerato = generatedKeys.getInt(1);
                    ordine.setIdOrdine(idOrdineGenerato);
                } else {
                    throw new SQLException("Creazione ordine fallita, nessun ID ottenuto.");
                }
            }

            // 2. Salva i Biglietti associati
            psBiglietto = connection.prepareStatement(queryBiglietto);
            for (Biglietto b : ordine.getBiglietti()) {
                psBiglietto.setDate(1, Date.valueOf(b.getDataVisita()));
                psBiglietto.setInt(2, b.getQuantita());
                psBiglietto.setInt(3, idOrdineGenerato); // Lego il biglietto all'ordine appena creato
                psBiglietto.setInt(4, b.getIdTipologia());
                psBiglietto.addBatch(); // Aggiunge al batch per efficienza
            }
            psBiglietto.executeBatch();

            connection.commit(); // Conferma Transazione
            LOGGER.info("Ordine salvato con successo. ID: " + idOrdineGenerato);

        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback(); // Annulla tutto in caso di errore
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Rollback fallito", ex);
            }
            LOGGER.log(Level.SEVERE, "Errore salvataggio ordine", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (psOrdine != null) psOrdine.close();
                if (psBiglietto != null) psBiglietto.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
        return idOrdineGenerato;
    }
}
