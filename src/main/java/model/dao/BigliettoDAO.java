package model.dao;

import model.bean.Biglietto;
import model.utils.ConnPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BigliettoDAO {
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

}
