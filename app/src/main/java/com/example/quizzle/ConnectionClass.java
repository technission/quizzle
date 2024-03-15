package com.example.quizzle;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {
    protected static String db = "sql3689744";

    protected static  String ip = "sql3.freemysqlhosting.net";

    protected static String port = "3306";

    protected static String username = "sql3689744";

    protected static String password = "20Directv1";

    public Connection CONN() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" +ip + ":" +port +"/" +db;
            conn = DriverManager.getConnection(connectionString, username, password);

        }catch(Exception e) {
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }
}
