package main.java;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SuppressWarnings("unused")
public class PrimaFereastra extends JFrame implements ActionListener {

    private final java.util.List<JButton> listaButoane;
    private final StringBuilder pentruTestare;
    private JPanel fereastraPrincipala;
    private JLabel labelInregistrare, labelEroareAutentificare;
    private JTextField userField;
    private JPasswordField passField;
    private JButton butonAuth, butonInrg, butonAnulare;
    private FereastraAutentificat obiectFereastraAutentificat;

    public PrimaFereastra(String titluFereastra) throws HeadlessException {
        super(titluFereastra);
        pentruTestare = new StringBuilder();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(900, 450));
        this.setMinimumSize(new Dimension(400, 250));
        this.setLocationRelativeTo(null);

        listaButoane = List.of(butonAuth, butonInrg, butonAnulare);
        modificareButoane();
        labelInregistrare.setVisible(false);
        labelEroareAutentificare.setVisible(false);

        this.add(fereastraPrincipala);
        this.setVisible(true);
    }

    private void modificareButoane() {
        listaButoane.forEach(butonCurent -> {
            butonCurent.addActionListener(this);
            butonCurent.setFocusable(false);
        });
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource().equals(butonAuth)) {
            if (verificaCampuri()) {
                String usernameIntrodus = userField.getText();
                String parolaIntrodusa = new String(passField.getPassword());
                if (usernameIntrodus.equals("Admin") && parolaIntrodusa.equals("Admin")) {
                    new FereastraAdministrator("Aplicatie Inchirieri Auto");
                    this.dispose();
                } else {
                    try {
                        verificareAutentificare(usernameIntrodus, parolaIntrodusa);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else afisareEroareAutentificare();
        }
        if (e.getSource().equals(butonInrg)) {
            this.dispose();
            new Inregistrare("Aplicatie Inchirieri Auto");
        }
        if (e.getSource().equals(butonAnulare))
            this.dispose();
    }

    private void verificareAutentificare(String usernameIntrodus, String parolaIntrodusa) throws SQLException {
        ResultSet resultSet = creareConexiuneSiQuery();
        while (resultSet.next()) {
            if (resultSet.getString("USERNAME").equals(usernameIntrodus) && resultSet.getString("PAROLA").equals(parolaIntrodusa)) {
                new FereastraAutentificat("Aplicatie Inchirieri Auto", creareUtilizator(resultSet));
                this.dispose();
            } else if (resultSet.getString("USERNAME").equals(usernameIntrodus) && !resultSet.getString("PAROLA").equals(parolaIntrodusa))
                afisareMesaj("Parola pentru " + usernameIntrodus + " introdusa gresit!");
            else afisareMesaj("Utilizatorul cu numele de " + usernameIntrodus + " nu exista!");
        }
    }

    private ResultSet creareConexiuneSiQuery() throws SQLException {
        DBConnect dbConnect = new DBConnect();
        Statement statement = dbConnect.SQLConnection.createStatement();
        return statement.executeQuery("SELECT * FROM UTILIZATORI");
    }

    private void afisareEroareAutentificare() {
        labelEroareAutentificare.setText("Toate field-urile trebuie sa fie completate pentru a te autentifica!");
        labelEroareAutentificare.setVisible(true);
    }

    private void afisareMesaj(String stringPentruAfisat) {
        labelEroareAutentificare.setText(stringPentruAfisat);
        labelEroareAutentificare.setVisible(true);
    }

    @Contract("_ -> new")
    private @NotNull Utilizator creareUtilizator(@NotNull ResultSet resultSet) throws SQLException {
        return new Utilizator(resultSet.getString("ID_UTILIZATOR"), resultSet.getString("USERNAME"), resultSet.getString("NUME"), resultSet.getString("PRENUME"),
                resultSet.getString("PAROLA"), resultSet.getString("NUMAR_TELEFON"), resultSet.getString("SEX"),
                resultSet.getString("ARE_PERMIS"), resultSet.getString("LOCALITATE"), resultSet.getString("ADRESA"), resultSet.getString("VARSTA"),
                (resultSet.getString("ID_MASINA_INCHIRIATA") == null ? "0" : resultSet.getString("ID_MASINA_INCHIRIATA")));
    }

    private boolean verificaCampuri() {
        return !(userField.getText().isEmpty()) && passField.getPassword().length > 0;
    }

    public JLabel getLabelInregistrare() {
        return labelInregistrare;
    }

    public JButton getButonAuth() {
        return butonAuth;
    }

    public JButton getButonInrg() {
        return butonInrg;
    }

    public JButton getButonAnulare() {
        return butonAnulare;
    }

    public JTextField getUserField() {
        return userField;
    }

    public JPasswordField getPassField() {
        return passField;
    }

    public StringBuilder getPentruTestare() {
        return pentruTestare;
    }

    public FereastraAutentificat getObiectFereastraAutentificat() {
        return obiectFereastraAutentificat;
    }

    public void reseteazaRezolvare() {
        pentruTestare.setLength(0);
    }

}
