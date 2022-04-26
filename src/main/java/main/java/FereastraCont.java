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

public class FereastraCont extends JFrame implements ActionListener {
    private final List<JButton> listaButoane;
    private final Utilizator utilizatorAutentificat;
    private JPanel panouPrincipal;
    private JLabel labelUsername, labelNume, labelPrenume, labelNrTelefon, labelLocalitate, labelAdresa, labelSex, labelMasinaInchiriata;
    private JButton butonAnulare, butonInapoi, butonIesire;

    public FereastraCont(String titluFereastra, Utilizator utilizatorAutentificat) {
        super(titluFereastra);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 500));
        this.setLocationRelativeTo(null);
        listaButoane = new ArrayList<>(List.of(butonAnulare, butonInapoi, butonIesire));
        this.utilizatorAutentificat = utilizatorAutentificat;
        adaugaFunctionalitateLaButoane();
        try {
            completareDateUtilizator(utilizatorAutentificat);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        this.add(panouPrincipal);
        this.setVisible(true);
    }

    private void completareDateUtilizator(@NotNull Utilizator utilizatorAutentificat) throws SQLException {
        labelUsername.setText(utilizatorAutentificat.getUsernameUtilizator());
        labelNume.setText(utilizatorAutentificat.getNumeUtilizator());
        labelPrenume.setText(utilizatorAutentificat.getPrenumeUtilizator());
        labelSex.setText(utilizatorAutentificat.getSexUtilizator());
        labelNrTelefon.setText(utilizatorAutentificat.getNrTelefonUtilizator());
        labelLocalitate.setText(utilizatorAutentificat.getLocatieUtilizator());
        labelAdresa.setText(utilizatorAutentificat.getAdresaUtilizator());
        DBConnect dbConnect = new DBConnect();
        Statement statement = dbConnect.SQLConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT T1.*, T2.* FROM UTILIZATORI T1 LEFT JOIN MASINI T2 ON T1.ID_MASINA_INCHIRIATA = T2.ID_MASINA WHERE T1.ID_UTILIZATOR = " + utilizatorAutentificat.getIdUtilizator() + ";");
        resultSet.next();
        if (!(resultSet.getString("ID_MASINA_INCHIRIATA") == null)) {
            String afisareMasina = resultSet.getString("MARCA") + ", modelul " + resultSet.getString("MODEL") + " din anul " + resultSet.getString("AN");
            labelMasinaInchiriata.setText(afisareMasina);
        } else labelMasinaInchiriata.setText("Nicio masina inchiriata!");
    }

    private void adaugaFunctionalitateLaButoane() {
        listaButoane.forEach(butonCurent -> {
            butonCurent.setFocusable(false);
            butonCurent.addActionListener(this);
        });
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource().equals(butonAnulare)) {
            try {
                if (!labelMasinaInchiriata.getText().equals("Nicio masina inchiriata!")) {
                    creareConexiuneSiExecutareQuerries();
                    this.dispose();
                    new FereastraAutentificat("Aplicatie Inchirieri Auto", utilizatorAutentificat);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(butonInapoi)) {
            this.dispose();
            new FereastraAutentificat("Aplicatie Inchirieri Auto", utilizatorAutentificat);
        }
        if (e.getSource().equals(butonIesire)) this.dispose();
    }

    private void creareConexiuneSiExecutareQuerries() throws SQLException {
        DBConnect dbConnect = new DBConnect();
        Statement statement = dbConnect.SQLConnection.createStatement();
        statement.executeUpdate("UPDATE MASINI SET ESTE_INCHIRIATA = 0 WHERE ID_MASINA = " + utilizatorAutentificat.getIdMasinaInchiriata() + ";");
        statement.executeUpdate("UPDATE UTILIZATORI SET ID_MASINA_INCHIRIATA = NULL WHERE ID_UTILIZATOR = " + utilizatorAutentificat.getIdUtilizator() + ";");
        utilizatorAutentificat.setIdMasinaInchiriata("0");
    }
}
