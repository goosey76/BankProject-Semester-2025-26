package verwaltung;

import bankprojekt.basisdaten.Geldbetrag;
import bankprojekt.basisdaten.Konto;
import bankprojekt.basisdaten.Kunde;
import bankprojekt.basisdaten.UeberweisungsfaehigesKonto;
import bankprojekt.exceptions.GesperrtException;
import bankprojekt.exceptions.UngueltigeKontonummerException;
import bankprojekt.verwaltung.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasse zum Testen der Bank und Ihrer Methoden
 * GeldUeberweisen() und getKontostand()
 */
public class BankTest {
    private Bank neueBank;
    private UeberweisungsfaehigesKonto mockKonto1;
    private UeberweisungsfaehigesKonto mockKonto2;
    private long kontoNummer1;
    private long kontoNummer2;
    private Geldbetrag geldbetrag;
    private String verwendungsZweck;


    @BeforeEach
    void SetUp() throws GesperrtException {
        // Erstelle Bank
        neueBank = new Bank(12345678L);

        // Erstelle MockKontos von den Entsprechenden Klassen
        mockKonto1 = mock(UeberweisungsfaehigesKonto.class);
        mockKonto2 = mock(UeberweisungsfaehigesKonto.class);

        // Testdaten
        geldbetrag = new Geldbetrag(200);
        verwendungsZweck = "Einz, Zwo, Drai";

        // Erstellte Kunden
        Kunde gustav = new Kunde("Gustav", "Goosey", "Die Geburtstags Straße 12", LocalDate.now());
        Kunde emily = new Kunde("Emily", "Entchen", "Die Luststags Straße 12", LocalDate.now());

        // Konfiguriere Mocks
        Mockito.when(mockKonto1.getInhaber()).thenReturn(gustav);
        Mockito.when(mockKonto2.getInhaber()).thenReturn(emily);

        // Füge in Bank ein
        kontoNummer1 = neueBank.mockEinfuegen(mockKonto1);
        kontoNummer2 = neueBank.mockEinfuegen(mockKonto2);
    }

    @Test
    public void getKontostandHappyTest() {
        // 1. Arrange
        Geldbetrag erwarteterGeldbetrag = new Geldbetrag(500);

        // Stubbing
        when(mockKonto1.getKontostand()).thenReturn(erwarteterGeldbetrag);

        // 2. Act
        Geldbetrag realerKontostand =  neueBank.getKontostand(kontoNummer1);

        // 3. Assert
            assertEquals(erwarteterGeldbetrag, realerKontostand, "Kontostände sollten gleichen Betrag haben");

        // 4. Verify
        verify(mockKonto1, times(1)).getKontostand();
    }

    /**
     * Wirft Exception klasse
     */
    @Test
    public void getKontoStandErrorPathTest() {
        // 1. Arrange
        long ungueltigeKontonummer = 3786490L;

        // 2. Act
        // 3. Assert
        assertThrows(UngueltigeKontonummerException.class, () -> {
           neueBank.getKontostand(ungueltigeKontonummer);
        });
    }

    /**
     * Teste die Funktionalität vom überweisen
     * @throws GesperrtException die GesperrtException
     */
    @Test
    public void geldUeberweisenTest() throws GesperrtException {
        // Konfiguriere Mocks
        when(mockKonto1.getKontonummer()).thenReturn(kontoNummer1);
        when(mockKonto2.getKontonummer()).thenReturn(kontoNummer2);

        // the initials
        when(mockKonto1.getKontostand()).thenReturn(new Geldbetrag(500));
        when(mockKonto2.getKontostand()).thenReturn(new Geldbetrag(500));

        // Teste die Methode
        boolean result = neueBank.geldUeberweisen(mockKonto1.getKontonummer(),
                mockKonto2.getKontonummer(), geldbetrag, verwendungsZweck);

        // Verifiziere den Methoden Aufruf
        assertTrue(result);
        verify(mockKonto1, times(1)).abheben(geldbetrag);
        verify(mockKonto2, times(1)).einzahlen(geldbetrag);
    }

    /**
     * Die Konten Befinden sich nicht in der Map test.
     * @throws GesperrtException Die Exception
     */
    @Test
    public void geldUeberweisenKontenNichtInMapTest() throws GesperrtException {
        // Configure - false kontonumbres
        when(mockKonto1.getKontonummer()).thenReturn(123456L);
        when(mockKonto2.getKontonummer()).thenReturn(52535636L);

        // Methode ausführen
        boolean result = neueBank.geldUeberweisen(mockKonto1.getKontonummer(), mockKonto2.getKontonummer(), geldbetrag, verwendungsZweck);

        // Result + Sollten nicht benutzt worden sein
        assertFalse(result);
        verify(mockKonto1, times(0)).abheben(geldbetrag);
        verify(mockKonto2, times(0)).einzahlen(geldbetrag);
    }

    /**
     * Der Betrag ist negativ  Exception soll geworfen werden
     * @throws GesperrtException die Gesperrt Exception
     */
    @Test
    public void geldUeberweisenBetragIstNegativTest() throws GesperrtException {
        // Configure
        Geldbetrag negativ = new Geldbetrag(-300);

        // Assert
        assertThrows(ArithmeticException.class, () -> {
            neueBank.geldUeberweisen(kontoNummer1, kontoNummer2, negativ, verwendungsZweck);
        });
    }

    /**
     * Verwendungszweck ist ungueltig.
     */
    @Test
    public void geldUeberweisenVerwendungszweckUngueltigTest() {
        // SetUp
        String ungueltigerVerwendungszweck = null;

        // Exercise
        assertThrows(NullPointerException.class, () ->{
            neueBank.geldUeberweisen(kontoNummer1, kontoNummer2, geldbetrag, ungueltigerVerwendungszweck);
        });
    }

    /**
     * Die Instanzen werden nie aufgerufen
     * @throws GesperrtException wirft eine GesperrtException
     */
    @Test
    public void geldUeberweisenKontenNichtUeberweisungsfaehigTest() throws GesperrtException {
        // SetUp
        UeberweisungsfaehigesKonto vonKonto = mockKonto1;
        Konto nichtGueltigesKonto = mock(Konto.class);

        long nachKontoNichtGueltig = neueBank.mockEinfuegen(nichtGueltigesKonto);

        // Exercise
        boolean result = neueBank.geldUeberweisen(vonKonto.getKontonummer(), nachKontoNichtGueltig, geldbetrag, verwendungsZweck);

        // Verify
        assertFalse(result);

        verify(vonKonto, never()).abheben(any());
        verify(nichtGueltigesKonto, never()).einzahlen(any());
    }
}
