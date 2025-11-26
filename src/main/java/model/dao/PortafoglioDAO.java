package model.dao;

import model.bean.Biglietto;
import model.bean.Ordine;
import model.bean.TipologiaBiglietto;
import model.utils.ConnPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PortafoglioDAO {

    /**
     * Recupera tutti gli ordini effettuati da uno specifico utente.
     */
    public List<Ordine> doRetrieveOrdersById(int idUtente) {
        List<Ordine> list = new ArrayList<>();
        String query = "SELECT * FROM ordine WHERE ID_Utente = ?";

        try (Connection con = ConnPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine o = new Ordine();
                o.setIdOrdine(rs.getInt("ID_Ordine"));

                // Conversione da SQL Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("DataAcquisto");
                if (timestamp != null) {
                    o.setDataAcquisto(timestamp.toLocalDateTime());
                }

                o.setImportoTotale(rs.getBigDecimal("ImportoTotale"));
                o.setIdUtente(rs.getInt("ID_Utente"));

                list.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Recupera tutti i biglietti associati a uno specifico ordine.
     */
    public List<Biglietto> doRetrieveBigliettiById(int idOrdine) {
        List<Biglietto> list = new ArrayList<>();
        String query = "SELECT * FROM biglietto WHERE ID_Ordine = ?";

        try (Connection con = ConnPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdine);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Biglietto b = new Biglietto();
                b.setIdBiglietto(rs.getInt("ID_Biglietto"));

                // Conversione da SQL Date a LocalDate
                java.sql.Date dataSql = rs.getDate("DataVisita");
                if (dataSql != null) {
                    b.setDataVisita(dataSql.toLocalDate());
                }

                b.setQuantita(rs.getInt("Quantita"));
                b.setIdOrdine(rs.getInt("ID_Ordine"));
                b.setIdTipologia(rs.getInt("ID_Tipologia"));

                list.add(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public TipologiaBiglietto doRetrieveTipologiaById(int idTipologia) {
        TipologiaBiglietto t = null;
        String query = "SELECT * FROM tipologia_biglietto WHERE ID_Tipologia = ?";

        try (Connection con = ConnPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idTipologia);
            ResultSet rs = ps.executeQuery();

            // Uso IF perchè l'ID è univoco (ne esiste massimo uno)
            if (rs.next()) {
                t = new TipologiaBiglietto();
                t.setIdTipologia(rs.getInt("ID_Tipologia"));
                t.setNome(rs.getString("Nome"));
                t.setPrezzo(rs.getBigDecimal("Prezzo"));
                t.setIdMuseo(rs.getInt("ID_Museo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t; // Ritorna l'oggetto o null se non trovato
    }
}
