package main.java;

import java.sql.*;

public class DBConnect {
    public Connection SQLConnection;
    public DBConnect() throws SQLException {
        String URL = "jdbc:sqlserver://bd2021.database.windows.net;databaseName=ionutmercescudb";
        String Username = "ionutmercescuuser";
        String Parola = "RAKdMn48d-h*=Gb9";
        SQLConnection = DriverManager.getConnection(URL, Username, Parola);
    }
}
