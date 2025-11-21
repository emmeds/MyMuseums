package model.bean;

import java.time.LocalDate;

public class Biglietto {

    private int idBiglietto;
    private LocalDate dataVisita;
    private int quantita;
    private int idOrdine;
    private int idTipologia;

    public Biglietto() {}

    public Biglietto(int idBiglietto, LocalDate dataVisita, int quantita, int idOrdine, int idTipologia) {
        this.idBiglietto = idBiglietto;
        this.dataVisita = dataVisita;
        this.quantita = quantita;
        this.idOrdine = idOrdine;
        this.idTipologia = idTipologia;
    }

    public int getIdBiglietto() {
        return idBiglietto;
    }

    public void setIdBiglietto(int idBiglietto) {
        this.idBiglietto = idBiglietto;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(int idTipologia) {
        this.idTipologia = idTipologia;
    }

    @Override
    public String toString() {
        return "Biglietto [idBiglietto=" + idBiglietto + ", quantita=" + quantita + "]";
    }
}

