package model.bean;

public class UtenteRegistrato extends Utente {

    private String nome;
    private String cognome;

    public UtenteRegistrato() {
        super();
    }

    public UtenteRegistrato(int idUtente, String email, String password, String nome, String cognome) {
        super(idUtente, email, password);
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "UtenteRegistrato [" + super.toString() +
                ", nome=" + nome + ", cognome=" + cognome + "]";
    }
}
