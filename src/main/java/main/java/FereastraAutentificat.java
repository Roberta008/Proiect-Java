package main.java;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FereastraAutentificat extends JFrame implements ActionListener, MouseListener {
    private final Utilizator utilizatorAutentificat;
    private final List<JButton> listaButoane;
    List<Masina> listaMasiniDinDB;
    private JPanel fereastraPrincipala;
    private JLabel labelInformativ;
    private JButton butonDeconectare, butonIesire, butonInchiriaza;
    private JList<Masina> listaMasini;
    private Masina masinaPentruInchiriat;
    private JLabel labelPozaMasina, labelInformatiiMasina;
    private JLabel labelInformatie;
    private JLabel labelEroareInchiriere;
    private JLabel labelIconitaCont;

    FereastraAutentificat(String titluFereastra, @NotNull Utilizator utilizatorAutentificat) {
        super(titluFereastra);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);
        listaButoane = List.of(butonDeconectare, butonIesire, butonInchiriaza);
        this.utilizatorAutentificat = utilizatorAutentificat;
        diverseModificari(utilizatorAutentificat);
        creareIconitaContulTau();
        this.add(fereastraPrincipala);
        this.setVisible(true);
    }

    private void creareIconitaContulTau() {
        ImageIcon imagineaPrincipala = new ImageIcon("Iconite/iconita_cont.png");
        Image imagineTemporara = imagineaPrincipala.getImage();
        Image imagineaRedimensionata = imagineTemporara.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        imagineaPrincipala.setImage(imagineaRedimensionata);
        labelIconitaCont.setIcon(imagineaPrincipala);
    }

    private void diverseModificari(@NotNull Utilizator utilizatorAutentificat) {
        aliniereLaMijlocInJList();
        listaMasiniDinDB = creareListaMasini();
        adaugaElementeInJList();
        labelInformativ.setText("Buna " + utilizatorAutentificat.getUsernameUtilizator() + "!");
        mofificareButoane();
        listaMasini.addMouseListener(this);
        butonInchiriaza.setVisible(false);
        labelEroareInchiriere.setVisible(false);
        labelIconitaCont.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelIconitaCont.addMouseListener(this);
    }

    private void aliniereLaMijlocInJList() {
        DefaultListCellRenderer listRenderer = (DefaultListCellRenderer) listaMasini.getCellRenderer();
        listRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void mofificareButoane() {
        listaButoane.forEach(butonCurent -> {
            butonCurent.addActionListener(this);
            butonCurent.setFocusable(false);
        });
    }

    private void adaugaElementeInJList() {
        DefaultListModel<Masina> demoList = new DefaultListModel<>();
        for (Masina masinaCurenta : listaMasiniDinDB) demoList.addElement(masinaCurenta);
        listaMasini.setModel(demoList);
    }

    private @Nullable List<Masina> creareListaMasini() {
        try {
            return creareConexiuneSiRulareQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private @NotNull List<Masina> creareConexiuneSiRulareQuery() throws SQLException {
        List<Masina> pentruReturnat = new ArrayList<>();
        DBConnect dbConnect = new DBConnect();
        Statement statement = dbConnect.SQLConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MASINI");
        while (resultSet.next())
            pentruReturnat.add(new Masina(resultSet.getString("ID_MASINA"), resultSet.getString("MARCA"), resultSet.getString("MODEL"),
                    resultSet.getString("AN"), resultSet.getString("ESTE_INCHIRIATA"), resultSet.getString("PATH_POZA")));
        return pentruReturnat;
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource().equals(butonDeconectare)) {
            this.dispose();
            new PrimaFereastra("Aplicatie Inchirieri Auto");
        }
        if (e.getSource().equals(butonInchiriaza)) {
            if (utilizatorAutentificat.getIdMasinaInchiriata().equals("0")) {
                utilizatorAutentificat.setIdMasinaInchiriata(masinaPentruInchiriat.getIdMasina());
                try {
                    DBConnect dbConnect = new DBConnect();
                    Statement statement = dbConnect.SQLConnection.createStatement();
                    statement.executeUpdate("UPDATE UTILIZATORI SET ID_MASINA_INCHIRIATA = " + masinaPentruInchiriat.getIdMasina() + " WHERE ID_UTILIZATOR = " + utilizatorAutentificat.getIdUtilizator() + ";");
                    statement.executeUpdate("UPDATE MASINI SET ESTE_INCHIRIATA = '1' WHERE ID_MASINA = " + masinaPentruInchiriat.getIdMasina() + ";");
                    labelInformatiiMasina.setText(masinaPentruInchiriat + " " + (masinaPentruInchiriat.getEsteInchiriata().equals("0") ? "este disponibila pentru inchiriat" : "ne pare rau, este inchiriata"));
                    labelEroareInchiriere.setText("Masina a fost inchiriata cu succes!");
                    labelEroareInchiriere.setForeground(new Color(188, 91, 255));
                    labelEroareInchiriere.setVisible(true);
                    utilizatorAutentificat.setIdMasinaInchiriata(masinaPentruInchiriat.getIdMasina());
                    butonInchiriaza.setEnabled(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                labelEroareInchiriere.setText("Nu mai poti inchiria o alta masina deoarece momentan ai inchiriata o alta masina!");
                labelEroareInchiriere.setForeground(new Color(173, 0, 49));
                labelEroareInchiriere.setVisible(true);
                butonInchiriaza.setEnabled(false);
            }
        }
        if (e.getSource().equals(butonIesire))
            this.dispose();
    }

    public JButton getButonIesire() {
        return butonIesire;
    }

    public Utilizator getUtilizatorAutentificat() {
        return utilizatorAutentificat;
    }

    @Override
    public void mouseClicked(@NotNull MouseEvent e) {
        labelEroareInchiriere.setVisible(false);
        labelInformatie.setVisible(false);
        if (e.getSource().equals(listaMasini)) {
            listaMasiniDinDB.stream()
                    .filter(masinaCurenta -> Integer.parseInt(listaMasiniDinDB.get(listaMasini.getSelectedIndex()).getIdMasina()) == Integer.parseInt(masinaCurenta.getIdMasina()))
                    .forEach(masinaCurenta -> {
                        labelPozaMasina.setIcon(creareImagine(masinaCurenta.getPathImagineMasina()));
                        labelInformatiiMasina.setText(masinaCurenta + " " + (masinaCurenta.getEsteInchiriata().equals("0") ? "este disponibila pentru inchiriat" : "ne pare rau, este inchiriata"));
                        butonInchiriaza.setVisible(true);
                        butonInchiriaza.setEnabled(masinaCurenta.getEsteInchiriata().equals("0"));
                        masinaPentruInchiriat = masinaCurenta;
                    });
        }
        if (e.getSource().equals(labelIconitaCont)) {
            this.dispose();
            new FereastraCont("Aplicatie Inchiriere Auto", utilizatorAutentificat);
        }
    }

    @NotNull
    private ImageIcon creareImagine(String pathImagine) {
        ImageIcon imagineaPrincipala = new ImageIcon(pathImagine);
        Image imagineTemporara = imagineaPrincipala.getImage();
        Image imagineaRedimensionata = imagineTemporara.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        return new ImageIcon(imagineaRedimensionata);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
