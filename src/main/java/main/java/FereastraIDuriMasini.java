package main.java;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FereastraIDuriMasini extends JFrame {
    private JTextPane textPaneIDuri;
    private JPanel panouPrincipal;

    public FereastraIDuriMasini(String titluFereastra) {
        super(titluFereastra);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(800, 300));
        this.setLocationRelativeTo(null);
        this.add(panouPrincipal);
        creareStringSiAdaugareInPane();
        this.setVisible(true);
    }

    private void creareStringSiAdaugareInPane() {
        centrareText();
        try {
            StringBuilder afisareMasini = new StringBuilder();
            DBConnect dbConnect = new DBConnect();
            Statement statement = dbConnect.SQLConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID_MASINA, MARCA, MODEL FROM MASINI");
            while (resultSet.next())
                afisareMasini.append("IDul ").append(resultSet.getString("ID_MASINA")).append(" pentru ").append(resultSet.getString("MARCA"))
                        .append(", modelul ").append(resultSet.getString("MODEL")).append("\n");
            textPaneIDuri.setText(afisareMasini.deleteCharAt(afisareMasini.length() - 1).toString());
            textPaneIDuri.setEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void centrareText() {
        var styledDocument = textPaneIDuri.getStyledDocument();
        var simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(simpleAttributeSet, StyleConstants.ALIGN_CENTER);
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), simpleAttributeSet, false);
    }

}
