
        package controller;

import model.bean.*;
import model.dao.MuseoDAO;
import model.dao.TipologiaBigliettoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Abilita l'uso di Mockito con JUnit 5
class AcquistoServletTest {

    // System Under Test
    private AcquistoServlet servlet;

    // Mocks
    @Mock private MuseoDAO museoDAOMock;
    @Mock private TipologiaBigliettoDAO tipologiaBigliettoDAOMock;

    // Dati Comuni Valid
    private UtenteRegistrato utenteTest;
    private final int VALID_ID_MUSEO = 1;
    private final String VALID_DATE = LocalDate.now().plusDays(5).toString(); // Data futura
    private final String VALID_TIME = "10:00";

    // Messaggi Errore Attesi (Costanti)
    private static final String ERR_ID = "ID Museo non valido";
    private static final String ERR_DT_MISSING = "Seleziona una data per la visita.";
    private static final String ERR_DT_FMT = "Formato data non valido.";
    private static final String ERR_DT_PAST = "La data non può essere nel passato.";
    private static final String ERR_ARR = "Dati biglietti non validi o mancanti.";
    private static final String ERR_MUS_NOT_FOUND = "Museo non trovato.";
    private static final String ERR_TIPO_INVALID = "Tipologia biglietto non valida per questo museo.";
    private static final String ERR_QTY_ZERO = "Seleziona almeno un biglietto.";

    @BeforeEach
    void setUp() {
        // Creiamo la servlet iniettando i mock tramite override dei metodi factory
        servlet = new AcquistoServlet() {
            @Override
            protected MuseoDAO getMuseoDAO() {
                return museoDAOMock;
            }

            @Override
            protected TipologiaBigliettoDAO getTipologiaBigliettoDAO() {
                return tipologiaBigliettoDAOMock;
            }
        };

        utenteTest = new UtenteRegistrato();
        utenteTest.setIdUtente(100);
        utenteTest.setNome("Mario");

    }

    // --- Helper per Dati DB ---
    private Museo getMockMuseo() {
        Museo m = new Museo();
        m.setIdMuseo(VALID_ID_MUSEO);
        m.setNome("Museo Test");
        m.setPrezzoTourGuidato(new BigDecimal("10.00"));
        return m;
    }

    private List<TipologiaBiglietto> getMockTipologie() {
        TipologiaBiglietto t1 = new TipologiaBiglietto();
        t1.setIdTipologia(1);
        t1.setPrezzo(new BigDecimal("20.00"));

        TipologiaBiglietto t2 = new TipologiaBiglietto();
        t2.setIdTipologia(2);
        t2.setPrezzo(new BigDecimal("10.00"));

        return Arrays.asList(t1, t2);
    }

    // =================================================================================
    // TEST CASES
    // =================================================================================

    // TC-01: ID Museo <= 0
    @Test
    void testElabora_TC01_IdMuseoInvalid() {
        // [ID_MUS] = <= 0
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, 0, VALID_DATE,
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_ID, e.getMessage());
    }

    // TC-02: Data Visita Null/Empty
    @Test
    void testElabora_TC02_DataMissing() {

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, null,
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_DT_MISSING, e1.getMessage());


        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, "",
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_DT_MISSING, e2.getMessage());
    }

    // TC-03: Formato Data Errato=Malformato
    @Test
    void testElabora_TC03_DataMalformed() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, "01-01-2025", // Formato errato (serve YYYY-MM-DD)
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_DT_FMT, e.getMessage());
    }

    // TC-04: Validità Temporale (Passato)
    @Test
    void testElabora_TC04_DataPassata() {

        String yesterday = LocalDate.now().minusDays(1).toString();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, yesterday,
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_DT_PAST, e.getMessage());
    }

    // TC-05: Input Arrays Null
    @Test
    void testElabora_TC05_ArraysNull() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                        null, null, false, VALID_TIME)
        );
        assertEquals(ERR_ARR, e.getMessage());
    }

    // TC-06: Input Arrays Lunghezza Diversa-Missmatch
    @Test
    void testElabora_TC06_ArraysMismatch() {

        String[] ids = {"1", "2"};
        String[] qts = {"1"}; // Manca una quantità

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                        ids, qts, false, VALID_TIME)
        );
        assertEquals(ERR_ARR, e.getMessage());
    }

    // TC-07: DB Museo Not Found
    @Test
    void testElabora_TC07_MuseoNotFound() {

        when(museoDAOMock.doRetrieveById(VALID_ID_MUSEO)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                        new String[]{"1"}, new String[]{"1"}, false, VALID_TIME)
        );
        assertEquals(ERR_MUS_NOT_FOUND, e.getMessage());
    }

    // TC-08: Match Tipologia (ID nel form non esiste nel DB)
    @Test
    void testElabora_TC08_TipologiaInvalid() {
        when(museoDAOMock.doRetrieveById(VALID_ID_MUSEO)).thenReturn(getMockMuseo());
        // Il DB restituisce solo tipologie 1 e 2
        when(tipologiaBigliettoDAOMock.doRetrieveByMuseoId(VALID_ID_MUSEO)).thenReturn(getMockTipologie());

        // L'utente chiede ID 99 (non esiste)
        String[] ids = {"99"};
        String[] qts = {"1"};

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                        ids, qts, false, VALID_TIME)
        );
        assertEquals(ERR_TIPO_INVALID, e.getMessage());
    }

    // TC-09: Quantità Totale Zero
    @Test
    void testElabora_TC09_QtyZero() {

        when(museoDAOMock.doRetrieveById(VALID_ID_MUSEO)).thenReturn(getMockMuseo());
        when(tipologiaBigliettoDAOMock.doRetrieveByMuseoId(VALID_ID_MUSEO)).thenReturn(getMockTipologie());

        // L'utente invia tipologia valida ma quantità 0
        String[] ids = {"1"};
        String[] qts = {"0"};

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                        ids, qts, false, VALID_TIME)
        );
        assertEquals(ERR_QTY_ZERO, e.getMessage());
    }

    // TC-10: Successo Senza Tour Guidato
    @Test
    void testElabora_TC10_SuccessNoTour() {

        when(museoDAOMock.doRetrieveById(VALID_ID_MUSEO)).thenReturn(getMockMuseo());
        when(tipologiaBigliettoDAOMock.doRetrieveByMuseoId(VALID_ID_MUSEO)).thenReturn(getMockTipologie());

        // Acquisto: 2 biglietti di Tipo 1 (20.00 l'uno) = 40.00
        String[] ids = {"1"};
        String[] qts = {"2"};

        Ordine risultato = servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                ids, qts, false, VALID_TIME);

        assertNotNull(risultato);
        assertEquals(new BigDecimal("40.00"), risultato.getImportoTotale());
        assertEquals(1, risultato.getBiglietti().size()); // Una tipologia aggiunta
        assertEquals(2, risultato.getBiglietti().get(0).getQuantita()); // Quantità 2
    }

    // TC-11: Successo Con Tour
    @Test
    void testElabora_TC11_SuccessWithTour() {

        Museo m = getMockMuseo(); // Prezzo tour = 10.00
        when(museoDAOMock.doRetrieveById(VALID_ID_MUSEO)).thenReturn(m);
        when(tipologiaBigliettoDAOMock.doRetrieveByMuseoId(VALID_ID_MUSEO)).thenReturn(getMockTipologie());

        // Acquisto: 1 biglietto Tipo 1 (20.00) + 1 biglietto Tipo 2 (10.00)
        // Totale Biglietti = 2
        // Costo Biglietti = 30.00
        // Costo Tour = 10.00 * 2 persone = 20.00
        // Totale Atteso = 50.00
        String[] ids = {"1", "2"};
        String[] qts = {"1", "1"};

        Ordine risultato = servlet.elaboraCreazioneOrdine(utenteTest, VALID_ID_MUSEO, VALID_DATE,
                ids, qts, true, VALID_TIME);

        assertNotNull(risultato);
        assertEquals(new BigDecimal("50.00"), risultato.getImportoTotale());
        assertEquals(2, risultato.getBiglietti().size());

        // Verifica corretto assegnamento ID Utente e Orario
        assertEquals(utenteTest.getIdUtente(), risultato.getIdUtente());
        assertEquals(VALID_TIME, risultato.getOrarioVisita());
    }
}