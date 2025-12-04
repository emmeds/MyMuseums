package controller;

import model.bean.Utente;
import model.bean.UtenteRegistrato;
import model.dao.UtenteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class per RegistrazioneServlet usando Category Partition.
 * I test sono organizzati per categoria di validazione.
 * Il metodo registerUser ritorna Utente e lancia IllegalArgumentException per errori di validazione.
 */
public class RegistrazioneTest {

    private RegistrazioneServlet servlet;

    @BeforeEach
    public void setUp() {
        servlet = new RegistrazioneServlet();
    }

    // ========== CATEGORIA 1: VALIDAZIONE CAMPI OBBLIGATORI - NOME ==========

    @Test
    public void testRegisterUser_NomeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser(null, "Rossi", "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo nome è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_NomeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("", "Rossi", "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo nome è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_NomeWhitespace() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("   ", "Rossi", "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo nome è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 2: VALIDAZIONE CAMPI OBBLIGATORI - COGNOME ==========

    @Test
    public void testRegisterUser_CognomeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", null, "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo cognome è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_CognomeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "", "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo cognome è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_CognomeWhitespace() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "  ", "email@test.it", "Pass123!", "Pass123!"));
        assertEquals("Il campo cognome è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 3: VALIDAZIONE CAMPI OBBLIGATORI - EMAIL ==========

    @Test
    public void testRegisterUser_EmailNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", null, "Pass123!", "Pass123!"));
        assertEquals("Il campo email è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmailEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "", "Pass123!", "Pass123!"));
        assertEquals("Il campo email è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmailWhitespace() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "   ", "Pass123!", "Pass123!"));
        assertEquals("Il campo email è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 4: VALIDAZIONE CAMPI OBBLIGATORI - PASSWORD ==========

    @Test
    public void testRegisterUser_PasswordNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", null, "Pass123!"));
        assertEquals("Il campo password è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_PasswordEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "", "Pass123!"));
        assertEquals("Il campo password è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_PasswordWhitespace() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "   ", "Pass123!"));
        assertEquals("Il campo password è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 5: VALIDAZIONE CAMPI OBBLIGATORI - CONFERMA PASSWORD ==========

    @Test
    public void testRegisterUser_ConfermaPasswordNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Pass123!", null));
        assertEquals("Il campo conferma password è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_ConfermaPasswordEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Pass123!", ""));
        assertEquals("Il campo conferma password è obbligatorio", exception.getMessage());
    }

    @Test
    public void testRegisterUser_ConfermaPasswordWhitespace() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Pass123!", "  "));
        assertEquals("Il campo conferma password è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 6: VALIDAZIONE FORMATO EMAIL ==========

    @Test
    public void testRegisterUser_EmailSenzaChiocciola() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "emailsenzachiocciola.it", "Pass123!", "Pass123!"));
        assertEquals("Formato email non valido", exception.getMessage());
    }



    // ========== CATEGORIA 7: VALIDAZIONE CORRISPONDENZA PASSWORD ==========

    @Test
    public void testRegisterUser_PasswordNonCorrispondono() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Password1!", "Password2!"));
        assertEquals("Le password non corrispondono", exception.getMessage());
    }



    // ========== CATEGORIA 8: VALIDAZIONE POLICY PASSWORD - LUNGHEZZA ==========

    @Test
    public void testRegisterUser_PasswordTroppoCorta_7Caratteri() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Pass1!", "Pass1!"));
        assertEquals("La password deve contenere almeno 8 caratteri", exception.getMessage());
    }



    @Test
    public void testRegisterUser_PasswordVuota() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "", ""));
        assertEquals("Il campo password è obbligatorio", exception.getMessage());
    }

    // ========== CATEGORIA 9: VALIDAZIONE POLICY PASSWORD - MAIUSCOLA ==========

    @Test
    public void testRegisterUser_PasswordSenzaMaiuscola() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "password1!", "password1!"));
        assertEquals("La password deve contenere almeno una lettera maiuscola", exception.getMessage());
    }



    // ========== CATEGORIA 10: VALIDAZIONE POLICY PASSWORD - NUMERO ==========

    @Test
    public void testRegisterUser_PasswordSenzaNumero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Password!", "Password!"));
        assertEquals("La password deve contenere almeno un numero", exception.getMessage());
    }



    // ========== CATEGORIA 11: VALIDAZIONE POLICY PASSWORD - CARATTERE SPECIALE ==========

    @Test
    public void testRegisterUser_PasswordSenzaCarattereSpeciale() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                servlet.registerUser("Mario", "Rossi", "email@test.it", "Password1", "Password1"));
        assertEquals("La password deve contenere almeno un carattere speciale", exception.getMessage());
    }



    // ========== CATEGORIA 12: VALIDAZIONE EMAIL ESISTENTE NEL DB ==========

    @Test
    public void testRegisterUser_EmailGiaEsistente() {
        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,  //MockedConstruction per mockare il costruttore di UtenteDAO
                (mock, context) -> {
                    when(mock.emailExists("existing@test.it")).thenReturn(true);
                })) {

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    servlet.registerUser("Mario", "Rossi", "existing@test.it", "Password1!", "Password1!"));

            assertEquals("Questa email è già associata ad un account", exception.getMessage());

            // Verifica che emailExists sia stato chiamato
            UtenteDAO mockDAO = mocked.constructed().get(0);
            verify(mockDAO, times(1)).emailExists("existing@test.it");
            verify(mockDAO, never()).addUtente(anyString(), anyString(), anyString(), anyString());
        }
    }

    // ========== CATEGORIA 13: CREAZIONE UTENTE SUCCESSO ==========

    @Test
    public void testRegisterUser_SuccessoRegistrazione() {
        UtenteRegistrato mockUtente = new UtenteRegistrato();
        mockUtente.setIdUtente(1);
        mockUtente.setNome("Mario");
        mockUtente.setCognome("Rossi");
        mockUtente.setEmail("new@test.it");

        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,
                (mock, context) -> {
                    when(mock.emailExists("new@test.it")).thenReturn(false);
                    when(mock.addUtente("Mario", "Rossi", "new@test.it", "Password1!"))
                            .thenReturn(mockUtente);
                })) {

            Utente result = servlet.registerUser(
                    "Mario", "Rossi", "new@test.it", "Password1!", "Password1!");

            assertNotNull(result);
            assertEquals("Mario", ((UtenteRegistrato)result).getNome());
            assertEquals("Rossi", ((UtenteRegistrato)result).getCognome());
            assertEquals("new@test.it", result.getEmail());

            // Verifica che i metodi del DAO siano stati chiamati nell'ordine corretto
            UtenteDAO mockDAO = mocked.constructed().get(0);
            verify(mockDAO, times(1)).emailExists("new@test.it");
            verify(mockDAO, times(1)).addUtente("Mario", "Rossi", "new@test.it", "Password1!");
        }
    }


    // ========== CATEGORIA 14: ERRORI DATABASE ==========

    @Test
    public void testRegisterUser_ErroreDatabaseDuranteVerificaEmail() {
        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,
                (mock, context) -> {
                    when(mock.emailExists(anyString()))
                            .thenThrow(new RuntimeException("Errore di connessione al database"));
                })) {

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    servlet.registerUser("Mario", "Rossi", "error@test.it", "Password1!", "Password1!"));

            assertTrue(exception.getMessage().contains("Errore di connessione al database"));
        }
    }

    @Test
    public void testRegisterUser_ErroreDatabaseDuranteCreazioneUtente() {
        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,
                (mock, context) -> {
                    when(mock.emailExists(anyString())).thenReturn(false);
                    when(mock.addUtente(anyString(), anyString(), anyString(), anyString()))
                            .thenThrow(new RuntimeException("Errore durante l'inserimento"));
                })) {

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    servlet.registerUser("Mario", "Rossi", "error@test.it", "Password1!", "Password1!"));

            assertTrue(exception.getMessage().contains("Errore durante l'inserimento"));
        }
    }

    // ========== CATEGORIA 15: TEST BOUNDARY VALUES ==========

    @Test
    public void testRegisterUser_PasswordEsattamente8Caratteri() {
        UtenteRegistrato mockUtente = new UtenteRegistrato();
        mockUtente.setIdUtente(3);

        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,
                (mock, context) -> {
                    when(mock.emailExists(anyString())).thenReturn(false);
                    when(mock.addUtente(anyString(), anyString(), anyString(), anyString()))
                            .thenReturn(mockUtente);
                })) {

            // Password di esattamente 8 caratteri con tutti i requisiti
            Utente result = servlet.registerUser(
                    "Mario", "Rossi", "test@test.it", "Passw0rd!", "Passw0rd!");

            assertNotNull(result);
            assertEquals(3, result.getIdUtente());
        }
    }

    @Test
    public void testRegisterUser_NomeConSpazi() {
        UtenteRegistrato mockUtente = new UtenteRegistrato();
        mockUtente.setIdUtente(4);

        try (MockedConstruction<UtenteDAO> mocked = mockConstruction(UtenteDAO.class,
                (mock, context) -> {
                    when(mock.emailExists(anyString())).thenReturn(false);
                    when(mock.addUtente(anyString(), anyString(), anyString(), anyString()))
                            .thenReturn(mockUtente);
                })) {

            Utente result = servlet.registerUser(
                    "Mario Giuseppe", "Rossi Verdi", "test@test.it", "Password1!", "Password1!");

            assertNotNull(result);
        }
    }



}
