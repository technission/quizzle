package com.example.quizzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class userMain_page extends AppCompatActivity  {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    TextView txtList;
    Boolean records = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        Button btn_Create = findViewById(R.id.btn_Create);
        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionClass = new ConnectionClass();
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    try {
                        con = connectionClass.CONN();
                        System.out.println(user.userID);
                        String query = "SELECT * FROM " +"`" +user.userID +"`";
                        System.out.println(query);
                        PreparedStatement stmt = con.prepareStatement(query);
                        //had to wrap this in a try to change the error and the app crashing to just displaying data to the screen
                        try (ResultSet rs = stmt.executeQuery()) {
                            records = true;
                            if (records) {
                                System.out.println("Records = TRUE");
                            }
                        } catch(SQLException e) {
                            runOnUiThread(() -> {
                                Toast.makeText(userMain_page.this, "No existing quizzes found", Toast.LENGTH_SHORT).show();
                            });

                        }




                    } catch(SQLException e) {
                        throw new RuntimeException(e);
                    }
                    runOnUiThread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            con.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            }
        });

        System.out.println("User logged in is: " +user.email);
        txtList = findViewById(R.id.txt_LoggedIn);
        txtList.setText("Welcome " +user.username);



    }

    public static class user{
        private static String userID;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private static String email;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        private static String username;
    }
//    public static class userLoggedin{
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        private static String email;
//        private static String password;
//
//        public static String getUserID() {
//            return userID;
//        }
//
//        public static void setUserID(String userID) {
//            userLoggedin.userID = userID;
//        }
//
//        private static String userID;
//
//        public static String getUsername() {
//            return username;
//        }
//
//        public static void setUsername(String username) {
//            userLoggedin.username = username;
//        }
//
//        private static String username;
//
//
//    }
}

