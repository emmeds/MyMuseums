package model.dao;

import model.bean.TipologiaBiglietto;
import model.utils.ConnPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipologiaBigliettoDAO {

    // In TipologiaBigliettoDAO.java
    public List<TipologiaBiglietto> doRetrieveByMuseoId(int idMuseo) {
        List<TipologiaBiglietto> list = new ArrayList<>();
        String query = "SELECT * FROM tipologia_biglietto WHERE ID_Museo = ?";

        try (Connection con = ConnPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idMuseo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipologiaBiglietto t = new TipologiaBiglietto();
                t.setIdTipologia(rs.getInt("ID_Tipologia"));
                t.setNome(rs.getString("Nome"));
                t.setPrezzo(rs.getBigDecimal("Prezzo"));
                t.setIdMuseo(rs.getInt("ID_Museo"));
                list.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void doSaveTipologiaBiglietto(TipologiaBiglietto tipoBiglietto) {
        Connection connection = null;
        PreparedStatement ps = null;

        String insertSQL = "INSERT INTO tipologia_biglietto (Nome, Prezzo, ID_Museo) VALUES (?, ?, ?)";

        try {
            connection = ConnPool.getConnection();
            ps = connection.prepareStatement(insertSQL);
            ps.setString(1, tipoBiglietto.getNome());
            ps.setBigDecimal(2, tipoBiglietto.getPrezzo());
            ps.setInt(3, tipoBiglietto.getIdMuseo());

            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(TipologiaBigliettoDAO.class.getName()).log(Level.SEVERE, "Errore nel salvataggio della tipologia biglietto", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(TipologiaBigliettoDAO.class.getName()).log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
    }
}
