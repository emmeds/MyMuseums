package model.bean;

public class Admin extends Utente {

    public Admin() {
        super();
    }

    public Admin(int idUtente, String email, String password) {
        super(idUtente, email, password);
    }

    @Override
    public String toString() {
        return "Admin [" + super.toString() + "]";
    }
}
