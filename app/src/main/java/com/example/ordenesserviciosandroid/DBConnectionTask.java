package com.example.ordenesserviciosandroid;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionTask extends AsyncTask<Void, Void, Connection> {

    private final String url;
    private final String username;
    private final String password;
    private final DBConnectionListener listener;

    public interface DBConnectionListener {
        void onDBConnectionSuccess(Connection con);
        void onDBConnectionError(Exception e);
    }

    public DBConnectionTask(String url, String username, String password, DBConnectionListener listener) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.listener = listener;
    }

    protected Connection doInBackground(Void... params) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Connection con) {
        if (con != null) {
            // La conexión se realizó correctamente
            listener.onDBConnectionSuccess(con);
        } else {
            // Hubo un error en la conexión
            listener.onDBConnectionError(new Exception("Error al conectar a la base de datos"));
        }
    }
}
