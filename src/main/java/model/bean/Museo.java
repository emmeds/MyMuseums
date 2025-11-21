package model.bean;

public class Museo {

    private int idMuseo;
    private String nome;
    private String descrizione;
    private String immagine;
    private String via;
    private String citta;
    private String cap;
    private double prezzoTourGuidato;

    public Museo() {}

    public Museo(int idMuseo, String nome, String descrizione, String immagine,
                 String via, String citta, String cap, double prezzoTourGuidato) {
        this.idMuseo = idMuseo;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.via = via;
        this.citta = citta;
        this.cap = cap;
        this.prezzoTourGuidato = prezzoTourGuidato;
    }

    public int getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(int idMuseo) {
        this.idMuseo = idMuseo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public double getPrezzoTourGuidato() {
        return prezzoTourGuidato;
    }

    public void setPrezzoTourGuidato(double prezzoTourGuidato) {
        this.prezzoTourGuidato = prezzoTourGuidato;
    }

    @Override
    public String toString() {
        return "Museo [idMuseo=" + idMuseo + ", nome=" + nome + "]";
    }
}

