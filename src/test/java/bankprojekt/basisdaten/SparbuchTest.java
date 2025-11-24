package bankprojekt.basisdaten;

import bankprojekt.exceptions.GesperrtException;
import bankprojekt.nuetzliches.Kalender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SparbuchTest {
    private Kalender kalenderMock;
    private Kunde inhaber;
    private Sparbuch sparbuch;
    private long kontonummer = 1234567L;

    // Wiederverwendbare Beträge
    private final Geldbetrag ABHEBE_BETRAG_KLEIN = new Geldbetrag(500);
    private final Geldbetrag ABHEBE_BETRAG_GROß = new Geldbetrag(2500);

    @BeforeEach
    void SetUp() {
        // 1. Mock-Kalendar
        kalenderMock = mock(Kalender.class);
        // 2. Erstelle Kunden
        inhaber = mock(Kunde.class);
        // 3. Heutiges Datum
        LocalDate startDatum = LocalDate.of(2025, 1, 10);
        when(kalenderMock.getHeutigesDatum()).thenReturn(startDatum);

        // 4. Erstelle Sparbuch mit Mock
        sparbuch = new Sparbuch(inhaber, kontonummer, kalenderMock);

        // Sparbuch Geld einzahlen
        sparbuch.einzahlen(new Geldbetrag(5000));
    }

    /**
     * Geld Abheben eines Sparbuchs happy Paths
     * @throws GesperrtException  die Exception falls Konto gesperrt ist.
     */
    @Test
    public void geldabhebenHappyPath() throws GesperrtException {
        // 1. SetUp 10/01/2025
        Geldbetrag geldbetrag = new Geldbetrag(1500);
        Geldbetrag limitBetrag = new Geldbetrag(501);
        // 1.ter Monat
        assertTrue(sparbuch.abheben(geldbetrag), "1. Abhebung sollte erfolgreich sein");
        assertEquals(new Geldbetrag(3500), sparbuch.getKontostand(), "Kontostand nach 1. Abhebung falsch");

        // ABehung von 2001 über dem Limit
        assertFalse(sparbuch.abheben(limitBetrag), " 2. Abhebung im Januar sollte über 2000 liegen und falsch sein");
        assertEquals(new Geldbetrag(3500), sparbuch.getKontostand(), "Kontostand nach 2. Abhebung falsch");

        // Monats wechsel
        LocalDate nächsterMonat = LocalDate.of(2025, 2, 10);
        when(kalenderMock.getHeutigesDatum()).thenReturn(nächsterMonat);

        // 3. Abhebung 2. Monat
        // ABehung von 2001 über dem Limit
        assertTrue(sparbuch.abheben(limitBetrag), " 1. Abhebung im Februar sollte wieder bei 0 anfangen");
        assertEquals(new Geldbetrag(2999), sparbuch.getKontostand(), "Kontostand nach 1. Abhebung Richtig");

        Mockito.verify(kalenderMock, times(4)).getHeutigesDatum();
    }
}
