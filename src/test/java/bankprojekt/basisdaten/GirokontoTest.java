package bankprojekt.basisdaten;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GirokontoTest {

    private Girokonto girokonto;
    private final Kunde max = Kunde.MUSTERMANN;
    private final long TEST_KONTONUMMER = 123456789L;
    private final Geldbetrag START_KONTOSTAND = new Geldbetrag(100.0, Waehrung.EUR);
    private final Geldbetrag TEST_DISPO = new Geldbetrag(500.0, Waehrung.EUR);

    // -- Setzt das Girokonto in Startzustand --
    @BeforeEach
    void setUp() {
        try {
            girokonto = new Girokonto(max, TEST_KONTONUMMER, TEST_DISPO);
        } catch (Exception e) {
            fail("Ausnahme beim Setup " + e.getMessage());
        }

        // Setze Start-Kontostand
        girokonto.einzahlen(START_KONTOSTAND);

        // Prüfe, ob der Startzustand korrekt gesetzt wurde
        assertEquals(START_KONTOSTAND, girokonto.getKontostand(), "Kontostand wurde nicht korrekt gesetzt...");
        assertEquals(TEST_DISPO, girokonto.getDispo(), "Dispo wurde nicht korrekt gesetzt...");
    }

    // Teste Franc
    @Test
    void testWaehrungswechselKRONE() {
        // ARRANGE:
        // girokonto hat: 100.00 EUR  Kontostand und 500.0 EUR Dispo
        Waehrung neueWaehrung = Waehrung.KRONE; // Zielwährung

        // ACT
        girokonto.waehrungsWechsel(neueWaehrung);

        // ASSERT

        Geldbetrag erwarteterKontostandKRONE = START_KONTOSTAND.umrechnen(neueWaehrung);
        Geldbetrag erwerteterDispoKRONE = TEST_DISPO.umrechnen(neueWaehrung);

        // Prüfe, ob gewechselt wurde
        assertEquals(neueWaehrung, girokonto.getKontostand().getWaehrung(), "Die Kontowährung sollte gleich sein");

        assertEquals(erwarteterKontostandKRONE, girokonto.getKontostand(), "Der Kontostand sollte korrekt nach KRONE umgerechnet sein");

        assertEquals(erwerteterDispoKRONE, girokonto.getDispo(), "Der Dispo sollte korrekt nach KRONE umgerechnet sein.");
    }

    // EURO
    @Test
    void testWaehrungswechselEURO() {
        // ARRANGE:
        // girokonto hat: 100.00 EUR  Kontostand und 500.0 EUR Dispo
        Waehrung neueWaehrung = Waehrung.EUR; // Zielwährung

        // ACT
        girokonto.waehrungsWechsel(neueWaehrung);

        // ASSERT

        Geldbetrag erwarteterKontostandEURO = START_KONTOSTAND.umrechnen(neueWaehrung);
        Geldbetrag erwerteterDispoEURO = TEST_DISPO.umrechnen(neueWaehrung);

        // Prüfe, ob gewechselt wurde
        assertEquals(neueWaehrung, girokonto.getKontostand().getWaehrung(), "Die Kontowährung sollte gleich sein");

        assertEquals(erwarteterKontostandEURO, girokonto.getKontostand(), "Der Kontostand sollte korrekt nach EURO umgerechnet sein");

        assertEquals(erwerteterDispoEURO, girokonto.getDispo(), "Der Dispo sollte korrekt nach EURO umgerechnet sein.");
    }

    @Test
    void testWaehrungswechselLEW() {
        // ARRANGE:
        // girokonto hat: 100.00 EUR  Kontostand und 500.0 EUR Dispo
        Waehrung neueWaehrung = Waehrung.LEW; // Zielwährung

        // ACT
        girokonto.waehrungsWechsel(neueWaehrung);

        // ASSERT

        Geldbetrag erwarteterKontostandLEW = START_KONTOSTAND.umrechnen(neueWaehrung);
        Geldbetrag erwerteterDispoLEW = TEST_DISPO.umrechnen(neueWaehrung);

        // Prüfe, ob gewechselt wurde
        assertEquals(neueWaehrung, girokonto.getKontostand().getWaehrung(), "Die Kontowährung sollte gleich sein");

        assertEquals(erwarteterKontostandLEW, girokonto.getKontostand(), "Der Kontostand sollte korrekt nach LEW umgerechnet sein");

        assertEquals(erwerteterDispoLEW, girokonto.getDispo(), "Der Dispo sollte korrekt nach LEW umgerechnet sein.");
    }

    @Test
    void testWaehrungswechselKMF() {
        // ARRANGE:
        // girokonto hat: 100.00 EUR  Kontostand und 500.0 EUR Dispo
        Waehrung neueWaehrung = Waehrung.FRANC; // Zielwährung

        // ACT
        girokonto.waehrungsWechsel(neueWaehrung);

        // ASSERT

        Geldbetrag erwarteterKontostandKMF = START_KONTOSTAND.umrechnen(neueWaehrung);
        Geldbetrag erwerteterDispoKMF = TEST_DISPO.umrechnen(neueWaehrung);

        // Prüfe, ob gewechselt wurde
        assertEquals(neueWaehrung, girokonto.getKontostand().getWaehrung(), "Die Kontowährung sollte gleich sein");

        assertEquals(erwarteterKontostandKMF, girokonto.getKontostand(), "Der Kontostand sollte korrekt nach KMF umgerechnet sein");

        assertEquals(erwerteterDispoKMF, girokonto.getDispo(), "Der Dispo sollte korrekt nach KMF umgerechnet sein.");
    }
}
