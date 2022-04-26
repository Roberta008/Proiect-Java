package main.java;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SuppressWarnings("unused")
public class Inregistrare extends JFrame implements ActionListener {
    private JTextField fieldNume, fieldPrenume, fieldUsername, fieldVarsta, fieldNrTelefon, fieldLocalitate, fieldAdresa;
    private JPasswordField fieldPass;
    private JRadioButton butonMasculin, butonFeminin, butonAltceva, butonDa, butonNu;
    private JButton butonInrg, butonAnulare;
    private JPanel fereastraPrincipala;
    private JLabel labelInformatie;
    private final List<JButton> listaButoane;
    private final List<JRadioButton> primaListaButoaneRadio;
    private final List<JRadioButton> aDouaListaButoaneRadio;
    private final List<JTextField> listaFields;
    private final ButtonGroup primulGrup, alDoileaGrup;
    private final StringBuilder pentruTestare;
    private boolean returnVerificareCampuri;

    private void modificareButoane() {
        listaButoane.forEach(butonCurent -> {
            butonCurent.addActionListener(this);
            butonCurent.setFocusable(false);
        });
    }

    private void grupareButoane() {
        primaListaButoaneRadio.forEach(butonCurent -> {
            primulGrup.add(butonCurent);
            butonCurent.addActionListener(this);
            butonCurent.setFocusable(false);
        });
        aDouaListaButoaneRadio.forEach(butonCurent -> {
            alDoileaGrup.add(butonCurent);
            butonCurent.addActionListener(this);
            butonCurent.setFocusable(false);
        });
    }

    public Inregistrare(String titluFereastra) {
        super(titluFereastra);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(900, 750));
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        pentruTestare = new StringBuilder();

        listaButoane = List.of(butonInrg, butonAnulare);
        primaListaButoaneRadio = List.of(butonFeminin, butonMasculin, butonAltceva);
        aDouaListaButoaneRadio = List.of(butonDa, butonNu);
        listaFields = List.of(fieldUsername, fieldNume, fieldPrenume, fieldVarsta, fieldNrTelefon, fieldLocalitate, fieldAdresa);
        modificareButoane();
        primulGrup = new ButtonGroup();
        alDoileaGrup = new ButtonGroup();
        grupareButoane();
        labelInformatie.setVisible(false);

        this.add(fereastraPrincipala);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource().equals(butonInrg)) {
            if (verificareCampuri()) {
                try {
                    creareConexiuneSiRulareQuery();
                    finalizareInregistrare();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                labelInformatie.setText("Toate field-urile trebuie completate!");
                labelInformatie.setVisible(true);
            }
        }
        if (e.getSource().equals(butonAnulare)) {
            this.dispose();
            new PrimaFereastra("Aplicatie Inchirieri Auto");
        }
    }

    private void finalizareInregistrare() {
        PrimaFereastra primaFereastra = new PrimaFereastra("Aplicatie Inchirieri Auto");
        primaFereastra.getLabelInregistrare().setVisible(true);
        this.dispose();
    }

    private void creareConexiuneSiRulareQuery() throws SQLException {
        String pentruInserat = creareStringPentruInserat();
        DBConnect dbConnect = new DBConnect();
        Statement statement = dbConnect.SQLConnection.createStatement();
        statement.executeUpdate(pentruInserat);
    }

    @NotNull
    private String creareStringPentruInserat() {
        return "INSERT INTO UTILIZATORI (NUME, PRENUME, USERNAME, PAROLA, SEX, VARSTA, ARE_PERMIS, NUMAR_TELEFON, LOCALITATE, ADRESA) " +
                "VALUES ('" + fieldNume.getText() + "', '" + fieldPrenume.getText() + "', '" + fieldUsername.getText() + "', '" + new String(fieldPass.getPassword())
                + "', '" + (butonMasculin.isSelected() ? "Masculin" : "Feminin") + "' , '" + fieldVarsta.getText() + "', '" + (butonNu.isSelected() ? "0" : "1")
                + "', '" + fieldNrTelefon.getText() + "', '" + fieldLocalitate.getText() + "', '" + fieldAdresa.getText() + "')";
    }

    private boolean verificareCampuri() {
        boolean conditieCampuri = listaFields.stream().noneMatch(fieldCurent -> fieldCurent.getText().isEmpty());
        int contorButon = (int) (primaListaButoaneRadio.stream().filter(AbstractButton::isSelected).count() + aDouaListaButoaneRadio.stream().filter(AbstractButton::isSelected).count());
        if (contorButon == 2 && conditieCampuri) return true;
        labelInformatie.setVisible(true);
        return false;
    }

    public StringBuilder getPentruTestare() {
        return pentruTestare;
    }

    public boolean isReturnVerificareCampuri() {
        return returnVerificareCampuri;
    }

    public JButton getButonInrg() {
        return butonInrg;
    }

    public JButton getButonAnulare() {
        return butonAnulare;
    }

    public void reseteazaRezolvare() {
        pentruTestare.setLength(0);
    }

    public JTextField getFieldNume() {
        return fieldNume;
    }

    public JTextField getFieldPrenume() {
        return fieldPrenume;
    }

    public JTextField getFieldUsername() {
        return fieldUsername;
    }

    public JTextField getFieldVarsta() {
        return fieldVarsta;
    }

    public JTextField getFieldNrTelefon() {
        return fieldNrTelefon;
    }

    public JTextField getFieldLocalitate() {
        return fieldLocalitate;
    }

    public JTextField getFieldAdresa() {
        return fieldAdresa;
    }

    public JPasswordField getFieldPass() {
        return fieldPass;
    }

    public JRadioButton getButonMasculin() {
        return butonMasculin;
    }

    public JRadioButton getButonFeminin() {
        return butonFeminin;
    }

    public JRadioButton getButonAltceva() {
        return butonAltceva;
    }

    public JRadioButton getButonDa() {
        return butonDa;
    }

    public JRadioButton getButonNu() {
        return butonNu;
    }

}
