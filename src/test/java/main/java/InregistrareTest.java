package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InregistrareTest {

    Inregistrare obiectInregistrare = new Inregistrare(null);

    void completareCampuri() {
        obiectInregistrare.getFieldUsername().setText("Test");
        obiectInregistrare.getFieldNume().setText("Test1");
        obiectInregistrare.getFieldPrenume().setText("Test2");
        obiectInregistrare.getFieldLocalitate().setText("Test3");
        obiectInregistrare.getFieldNrTelefon().setText("Test4");
        obiectInregistrare.getFieldPass().setText("Test5");
        obiectInregistrare.getFieldVarsta().setText("Test6");
        obiectInregistrare.getFieldAdresa().setText("Test7");
        obiectInregistrare.getButonFeminin().setSelected(true);
        obiectInregistrare.getButonDa().setSelected(true);
    }

    @Test
    void actionPerformed() {
        obiectInregistrare.getButonInrg().doClick();
        assertEquals("Toate campurile trebuie completate!", obiectInregistrare.getPentruTestare().toString());

        completareCampuri();
        obiectInregistrare.getButonInrg().doClick();
        assertTrue(obiectInregistrare.isReturnVerificareCampuri());
        assertFalse(obiectInregistrare.isVisible());

        obiectInregistrare.getButonAnulare().doClick();
        assertFalse(obiectInregistrare.isVisible());
    }

}