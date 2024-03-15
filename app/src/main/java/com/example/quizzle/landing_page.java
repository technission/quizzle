package com.example.quizzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class landing_page extends AppCompatActivity {
    ConnectionClass connectionClass;
    HashPasswordClass hashPasswordClass;
    Connection con;

    EditText email, password;

    //this uses the userLoggedin class from userMain_page
    userMain_page.user userIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        email = findViewById(R.id.edit_Email);
        password = findViewById(R.id.edit_Password);


        
        Button btn_Login = findViewById(R.id.btn_Login);
        Button btn_Register = findViewById(R.id.btn_Register);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //pull strings from the edit texts
            String Email = email.getText().toString();
            String Password = password.getText().toString();
            //create the function of checking the database for user and password for login
                connectionClass = new ConnectionClass();
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    try {
                        con = connectionClass.CONN();
                        String query = "SELECT * FROM quizzle_users";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        hashPasswordClass = new HashPasswordClass();
                        while (rs.next()) {
                            String userEmail = rs.getString("email");
                            String userPassword = rs.getString("password");
                            String userName = rs.getString("username");
                            String userID = rs.getString("userID");
                            StringBuilder db_password = hashPasswordClass.hashPassword(Password);
                            //this will test the password hashed and return true or false
                            Boolean testPassword = db_password.toString().equals(userPassword.toString());
                            System.out.println("user password is: " +userPassword);
                            System.out.println("hashed password is: " +db_password);
                            //have to use .equals because you cannot use == to compare strings
                            if(userEmail.equals(Email) && testPassword) {
                                userIn = new userMain_page.user();
                                userIn.setEmail(Email);
                                userIn.setUsername(userName);
                                userIn.setUserID(userID);
                                startActivity(new Intent(landing_page.this, userMain_page.class));
                            }else if (!userEmail.equals(Email) && !testPassword) {
                                System.out.println("Both Items Not Correct");
                            }else if (!userEmail.equals(Email) && testPassword) {
                                System.out.println("Email is invalid");
                            }

                            System.out.println("userEmail is: " +userEmail);
                            System.out.println("email is: " +Email);

                        }
                    }catch(SQLException e) {
                        throw new RuntimeException(e);
                    }
                    runOnUiThread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println(Email);

                    });
                });
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(landing_page.this, register_page.class));
            }
        });
    }
}