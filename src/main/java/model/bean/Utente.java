package model.bean;

public abstract class Utente {
    private int idUtente;
    private String email;
    private String password;

    public Utente() {}

    public Utente(int idUtente, String email, String password) {
        this.idUtente = idUtente;
        this.email = email;
        this.password = password;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utente [idUtente=" + idUtente + ", email=" + email + "]";
    }
}
