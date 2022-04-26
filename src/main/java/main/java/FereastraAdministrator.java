package main.java;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FereastraAdministrator extends JFrame implements ActionListener {
    private final List<JButton> listaButoane;
    private JPanel panouPrincipal;
    private JTabbedPane tabbedPane1;
    private JTextField fieldMarcaMasina, fieldModelMasina, fieldAnMasina;
    private JButton butonAdauga, butonInapoi, butonIesire, butonIDuri, butonStergeMasina, butonInapoi2, butonIesire2;
    private JTextField fieldIDMasina;
    private JLabel labelEroareStergere;
    private JTextField fieldLocatiePoza;
    private JLabel labelEroriAdaugare;

    public FereastraAdministrator(String titluFereastra) {
        super(titluFereastra);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 500));
        this.setLocationRelativeTo(null);

        labelEroareStergere.setVisible(false);
        labelEroriAdaugare.setVisible(false);
        listaButoane = new ArrayList<>(List.of(butonAdauga, butonInapoi, butonIesire, butonIDuri, butonStergeMasina, butonInapoi2, butonIesire2));
        adaugaFunctionalitateLaButoane();

        this.add(panouPrincipal);
        this.setVisible(true);
    }

    private void adaugaFunctionalitateLaButoane() {
        listaButoane.forEach(butonCurent -> {
            butonCurent.setFocusable(false);
            butonCurent.addActionListener(this);
        });
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource().equals(butonAdauga)) {
            if (verificaCampuriCompletate()) {
                String marcaMasina = fieldMarcaMasina.getText();
                String modelMasina = fieldModelMasina.getText();
                String anMasina = fieldAnMasina.getText();
                String locatiePoza = fieldLocatiePoza.getText();
                try {
                    DBConnect dbConnect = new DBConnect();
                    Statement statement = dbConnect.SQLConnection.createStatement();
                    String comandaSQL = "INSERT INTO MASINI (MARCA, MODEL, AN, PATH_POZA, ESTE_INCHIRIATA) VALUES ('" + marcaMasina + "', '" + modelMasina + "', '"
                            + anMasina + "', '" + locatiePoza + "', 0)";
                    statement.executeUpdate(comandaSQL);
                    afisareMesaj(labelEroriAdaugare, "Masina a fost adaugata cu succes!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else afisareMesajEroare(labelEroriAdaugare, "Toate campurile trebuie sa fie completate inainte de a adauga masina!");
        }
        if (e.getSource().equals(butonInapoi)) {
            this.dispose();
            new PrimaFereastra("Aplicatie Inchirieri Auto");
        }
        if (e.getSource().equals(butonIesire)) this.dispose();
        if (e.getSource().equals(butonIDuri)) {
            new FereastraIDuriMasini("Aplicatie Inchirieri Auto");
        }
        if (e.getSource().equals(butonStergeMasina)) {
            if (!fieldIDMasina.getText().isEmpty()) {
                boolean conditieContinuare;
                try {
                    DBConnect dbConnect = new DBConnect();
                    Statement statement = dbConnect.SQLConnection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT ID_MASINA, ESTE_INCHIRIATA FROM MASINI");
                    conditieContinuare = verificaDacaExistaID(resultSet);
                    if (conditieContinuare) {
                        if (resultSet.getString("ID_MASINA").equals(fieldIDMasina.getText()) && resultSet.getString("ESTE_INCHIRIATA").equals("0")) {
                            System.out.println("DELETE FROM MASINI WHERE ID_MASINA = " + fieldIDMasina.getText() + ";");
                            statement.executeUpdate("DELETE FROM MASINI WHERE ID_MASINA = " + fieldIDMasina.getText() + ";");
                            afisareMesaj(labelEroareStergere, "Masina cu IDul " + fieldIDMasina.getText() + " a fost stearsa cu succes!");
                        } else
                            afisareMesajEroare(labelEroareStergere, "Masina cu IDul " + fieldIDMasina.getText() + " nu poate fi stearsa deoarece este momentan inchiriata!");
                    } else afisareMesajEroare(labelEroareStergere, "Masina cu IDul " + fieldIDMasina.getText() + " nu a fost gasita!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else afisareMesajEroare(labelEroareStergere, "Introdu un ID pentru a sterge masina!");
        }
        if (e.getSource().equals(butonInapoi2)) {
            this.dispose();
            new PrimaFereastra("Aplicatie Inchirieri Auto");
        }
        if (e.getSource().equals(butonIesire2)) this.dispose();
    }

    private void afisareMesaj(@NotNull JLabel labelulCurent, String mesajulPentruAfisat) {
        labelulCurent.setText(mesajulPentruAfisat);
        labelulCurent.setForeground(new Color(188, 91, 255));
        labelulCurent.setVisible(true);
    }

    private void afisareMesajEroare(@NotNull JLabel labelulCurent, String mesajulErorii) {
        labelulCurent.setText(mesajulErorii);
        labelulCurent.setForeground(new Color(173, 0, 49));
        labelulCurent.setVisible(true);
    }

    private boolean verificaDacaExistaID(@NotNull ResultSet resultSet) throws SQLException {
        while (resultSet.next())
            if (Objects.equals(resultSet.getString("ID_MASINA"), fieldIDMasina.getText())) return true;
        return false;
    }

    private boolean verificaCampuriCompletate() {
        return !fieldMarcaMasina.getText().isEmpty() && !fieldModelMasina.getText().isEmpty() && !fieldAnMasina.getText().isEmpty() && !fieldLocatiePoza.getText().isEmpty();
    }
}
