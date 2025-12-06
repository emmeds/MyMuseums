package controller;

import model.bean.Museo;
import model.bean.TipologiaBiglietto;
import model.dao.MuseoDAO;
import model.dao.TipologiaBigliettoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddMuseoServletTest {

    private AddMuseoServlet servlet;

    @Mock private MuseoDAO museoDAOMock;
    @Mock private TipologiaBigliettoDAO tipologiaBigliettoDAOMock;

    // Costanti per input validi
    private static final String P_STD = "10.00";
    private static final String P_RID = "5.00";
    private static final String P_SKIP = "15.00";
    private static final String P_TOUR = "20.00";
    private static final String ERR_REQUIRED = "Tutti i campi sono obbligatori.";
    private static final String ERR_FORMAT = "I prezzi inseriti non sono validi.";

    private AutoCloseable mocksCloseable;

    @BeforeEach
    void setUp() {
        // Inizializza i mock manualmente (compatibile anche senza mockito-junit-jupiter)
        mocksCloseable = MockitoAnnotations.openMocks(this);
        // Sovrascrittura dei metodi factory per iniettare i mock
        servlet = new AddMuseoServlet() {
            @Override
            protected MuseoDAO createMuseoDAO() {
                return museoDAOMock;
            }

            @Override
            protected TipologiaBigliettoDAO createTipologiaBigliettoDAO() {
                return tipologiaBigliettoDAOMock;
            }
        };
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocksCloseable != null) {
            mocksCloseable.close();
        }
    }

    // Helper per creare un museo valido (base per i test negativi)
    private Museo getValidMuseo() {
        Museo m = new Museo();
        m.setNome("Museo Test");
        m.setVia("Via Test");
        m.setCitta("Città Test");
        m.setCap("12345");
        m.setDescrizione("Descrizione Test");
        m.setImmagine("img.jpg");
        return m;
    }

    // --- GRUPPO: Museo Object ---

    @Test
    void testAddMuseo_MuseoNull_TC01() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(null, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo Nome ---

    @Test
    void testAddMuseo_NomeNull_TC02() {
        Museo m = getValidMuseo();
        m.setNome(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_NomeEmpty_TC03() {
        Museo m = getValidMuseo();
        m.setNome("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_NomeWhitespace_TC04() {
        Museo m = getValidMuseo();
        m.setNome("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo Via ---

    @Test
    void testAddMuseo_ViaNull_TC05() {
        Museo m = getValidMuseo();
        m.setVia(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_ViaEmpty_TC06() {
        Museo m = getValidMuseo();
        m.setVia("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_ViaWhitespace_TC07() {
        Museo m = getValidMuseo();
        m.setVia("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo Città ---

    @Test
    void testAddMuseo_CittaNull_TC08() {
        Museo m = getValidMuseo();
        m.setCitta(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_CittaEmpty_TC09() {
        Museo m = getValidMuseo();
        m.setCitta("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_CittaWhitespace_TC10() {
        Museo m = getValidMuseo();
        m.setCitta("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo CAP ---

    @Test
    void testAddMuseo_CapNull_TC11() {
        Museo m = getValidMuseo();
        m.setCap(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_CapEmpty_TC12() {
        Museo m = getValidMuseo();
        m.setCap("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_CapWhitespace_TC13() {
        Museo m = getValidMuseo();
        m.setCap("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo Descrizione ---

    @Test
    void testAddMuseo_DescrizioneNull_TC14() {
        Museo m = getValidMuseo();
        m.setDescrizione(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_DescrizioneEmpty_TC15() {
        Museo m = getValidMuseo();
        m.setDescrizione("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_DescrizioneWhitespace_TC16() {
        Museo m = getValidMuseo();
        m.setDescrizione("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Campo Immagine ---

    @Test
    void testAddMuseo_ImmagineNull_TC17() {
        Museo m = getValidMuseo();
        m.setImmagine(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_ImmagineEmpty_TC18() {
        Museo m = getValidMuseo();
        m.setImmagine("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_ImmagineWhitespace_TC19() {
        Museo m = getValidMuseo();
        m.setImmagine("   ");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Prezzi Presenza (Stringhe) ---

    @Test
    void testAddMuseo_PrezziInputNull_TC20() {
        Museo m = getValidMuseo();
        // Testiamo il Null su un parametro rappresentativo (es. sPrezzoStandard)
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, null, P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_PrezziInputEmpty_TC21() {
        Museo m = getValidMuseo();
        // Testiamo Empty su un parametro rappresentativo (es. sPrezzoRidotto)
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, "", P_SKIP, P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    @Test
    void testAddMuseo_PrezziInputWhitespace_TC22() {
        Museo m = getValidMuseo();
        // Testiamo Whitespace su un parametro rappresentativo (es. sPrezzoSaltaFila)
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, P_STD, P_RID, "   ", P_TOUR));
        assertEquals(ERR_REQUIRED, e.getMessage());
    }

    // --- GRUPPO: Formato Prezzi ---

    @Test
    void testAddMuseo_PrezziFormatoNonNumerico_TC23() {
        Museo m = getValidMuseo();
        // Passiamo una stringa non numerica
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                servlet.addMuseo(m, "abc", P_RID, P_SKIP, P_TOUR));
        assertEquals(ERR_FORMAT, e.getMessage());
        verifyNoInteractions(museoDAOMock);
    }

    // --- GRUPPO: DAO Fallimento ---

    @Test
    void testAddMuseo_DaoSaveFail_TC24() {
        Museo m = getValidMuseo();

        // Mock comportamento DB: fallimento (ritorna 0)
        when(museoDAOMock.doSave(m)).thenReturn(0);

        int result = servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR);

        // Verifica: return 0
        assertEquals(0, result);
        // Verifica: I biglietti NON devono essere salvati
        verifyNoInteractions(tipologiaBigliettoDAOMock);
    }

    // --- GRUPPO: Successo (Happy Path) ---

    @Test
    void testAddMuseo_Success_TC25() {
        Museo m = getValidMuseo();
        int simulatedId = 99;

        // Mock comportamento DB: successo (ritorna ID > 0)
        when(museoDAOMock.doSave(m)).thenReturn(simulatedId);

        // Esecuzione
        int result = servlet.addMuseo(m, P_STD, P_RID, P_SKIP, P_TOUR);

        // Verifica: ID restituito corretto
        assertEquals(simulatedId, result);

        // Verifica: Prezzo Tour settato nel bean
        assertEquals(new BigDecimal(P_TOUR), m.getPrezzoTourGuidato());

        // Verifica: Salvataggio 3 biglietti
        ArgumentCaptor<TipologiaBiglietto> captor = ArgumentCaptor.forClass(TipologiaBiglietto.class);
        verify(tipologiaBigliettoDAOMock, times(3)).doSaveTipologiaBiglietto(captor.capture());

        List<TipologiaBiglietto> biglietti = captor.getAllValues();
        assertEquals(3, biglietti.size());

        // Controllo rapido che siano stati creati corretti
        assertEquals("Standard", biglietti.get(0).getNome());
        assertEquals(simulatedId, biglietti.get(0).getIdMuseo());

        assertEquals("Ridotto", biglietti.get(1).getNome());
        assertEquals("Salta la Fila", biglietti.get(2).getNome());
    }
}

