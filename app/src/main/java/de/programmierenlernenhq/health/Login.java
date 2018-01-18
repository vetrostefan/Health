package de.programmierenlernenhq.health;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final EditText etName = (EditText) findViewById(R.id.name);
        final EditText etPassword = (EditText) findViewById(R.id.passwort);
        Button btnLogin = (Button) findViewById(R.id.patientButton);
        Button btnRegister = (Button) findViewById(R.id.registerButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etName.getText().toString();
                String password = etPassword.getText().toString();
                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

                //String savedPassword = preferences.getString(password, "");
                //String savedUserName = preferences.getString(user, "");

                String userDetails = preferences.getString(user + password + "data","No information on that user.");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("display",userDetails);
                editor.commit();

                if(userDetails.contains("tp.Reha")){
                    Intent tp = new Intent(Login.this, MainActivity.class);
                    startActivity(tp);
                }else {
                    Intent displayScreen = new Intent(Login.this, search.class);

                    EditText sucheEingabe = findViewById(R.id.name);
                    String sucheString = sucheEingabe.getText().toString();
                    displayScreen.putExtra("eingabe",sucheString);
                    startActivity(displayScreen);
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerScreen = new Intent(Login.this, Register.class);
                startActivity(registerScreen);
            }
        });


    }


    public void patient(View view) {
        Intent intent = new Intent(Login.this, search.class);

        EditText sucheEingabe = findViewById(R.id.name);
        String sucheString = sucheEingabe.getText().toString();
        intent.putExtra("eingabe", sucheString);
        startActivity(intent);
    }

}
