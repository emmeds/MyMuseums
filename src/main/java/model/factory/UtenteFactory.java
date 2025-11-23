package model.factory;

import model.bean.Admin;
import model.bean.Utente;
import model.bean.UtenteRegistrato;

public class UtenteFactory {

    // Questo è il metodo magico
    public static Utente getUtente(String tipo) {
        if (tipo.equalsIgnoreCase("ADMIN")) {
            return new Admin(); // Crea un Admin vuoto
        } else if (tipo.equalsIgnoreCase("REGISTRATO")) {
            return new UtenteRegistrato(); // Crea un UtenteRegistrato vuoto
        }
        return null; // O lancia eccezione se il tipo non esiste
    }
}
