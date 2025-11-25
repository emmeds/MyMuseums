package model.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ordine {

    private int idOrdine;
    private LocalDateTime dataAcquisto;
    private BigDecimal importoTotale;
    private int idUtente;

    // Relazione 1 a * --> Ordine contiene Biglietti
    private List<Biglietto> biglietti = new ArrayList<>();

    public Ordine() {}

    public Ordine(int idOrdine, LocalDateTime dataAcquisto, BigDecimal importoTotale, int idUtente) {
        this.idOrdine = idOrdine;
        this.dataAcquisto = dataAcquisto;
        this.importoTotale = importoTotale;
        this.idUtente = idUtente;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public LocalDateTime getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(LocalDateTime dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "Ordine [idOrdine=" + idOrdine + ", importoTotale=" + importoTotale + "]";
    }
}
