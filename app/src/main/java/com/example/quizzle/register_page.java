package com.example.quizzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class register_page extends AppCompatActivity {
    HashPasswordClass hashPasswordClass;
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    EditText edt_Email, edt_Password, edt_Username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Button btn_Reg = findViewById(R.id.btn_RegisterReg);
        edt_Email = findViewById(R.id.edit_EmailReg);
        edt_Password = findViewById(R.id.edit_PasswordReg);
        edt_Username = findViewById(R.id.edit_UsernameReg);

        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionClass = new ConnectionClass();
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    try {
                        String Email = edt_Email.getText().toString();
                        String Password = edt_Password.getText().toString();
                        String username = edt_Username.getText().toString();
                        //hash code for passwords
                        hashPasswordClass = new HashPasswordClass();
                        hashPasswordClass.hashPassword(Password);

                        con = connectionClass.CONN();
                        String query = "INSERT INTO `quizzle_users` (`email`, `password`, `username`) VALUES (" + "'" + Email + "'" + "," + "'" +hashPasswordClass.hashPassword(Password) + "'" + "," + "'" + username + "'" + ")";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.executeUpdate();
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
                        //after user is registered then send user back to the login page
                        Toast.makeText(register_page.this, "User Registered Please Log In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(register_page.this, landing_page.class));
                    });
                });
            }
        });


    }
}