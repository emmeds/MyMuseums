package model.bean;

public class TipologiaBiglietto {

    private int idTipologia;
    private String nome;
    private double prezzo;
    private int idMuseo;

    public TipologiaBiglietto() {}

    public TipologiaBiglietto(int idTipologia, String nome, double prezzo, int idMuseo) {
        this.idTipologia = idTipologia;
        this.nome = nome;
        this.prezzo = prezzo;
        this.idMuseo = idMuseo;
    }

    public int getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(int idTipologia) {
        this.idTipologia = idTipologia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(int idMuseo) {
        this.idMuseo = idMuseo;
    }

    @Override
    public String toString() {
        return "TipologiaBiglietto [idTipologia=" + idTipologia + ", nome=" + nome + "]";
    }
}
