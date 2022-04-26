package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimaFereastraTest {

    PrimaFereastra obiectFereastra = new PrimaFereastra(null);

    @Test
    void actionPerformed() {
        obiectFereastra.getPassField().setText(null);
        obiectFereastra.getButonAuth().doClick();
        assertEquals("Toate field-urile trebuie completate pentru a va autentifica cu succes!", obiectFereastra.getPentruTestare().toString());

        obiectFereastra.reseteazaRezolvare();
        obiectFereastra.getUserField().setText(null);
        obiectFereastra.getButonAuth().doClick();
        assertEquals("Toate field-urile trebuie completate pentru a va autentifica cu succes!", obiectFereastra.getPentruTestare().toString());

        obiectFereastra.reseteazaRezolvare();
        obiectFereastra.getUserField().setText("Roberta");
        obiectFereastra.getPassField().setText("12345");
        obiectFereastra.getButonAuth().doClick();
        assertEquals("User sau parola introduse gresit!", obiectFereastra.getPentruTestare().toString());

        obiectFereastra.reseteazaRezolvare();
        obiectFereastra.getUserField().setText("Roberta123");
        obiectFereastra.getPassField().setText("1234");
        obiectFereastra.getButonAuth().doClick();
        assertEquals("Utilizatorul nu exista!", obiectFereastra.getPentruTestare().toString());

        obiectFereastra.reseteazaRezolvare();
        obiectFereastra.getUserField().setText("Roberta");
        obiectFereastra.getPassField().setText("1234");
        obiectFereastra.getButonAuth().doClick();
        assertEquals("Roberta 1234 Da Iordan Roberta-Ioana 20 0740475357 Sibiu Str. Teilor Nr. 13 Feminin", obiectFereastra.getPentruTestare().toString());

        obiectFereastra.getButonAnulare().doClick();
        assertFalse(obiectFereastra.isVisible());

        obiectFereastra.getButonInrg().doClick();
        assertTrue(obiectFereastra.getObiectFereastraAutentificat().isVisible());
    }

}