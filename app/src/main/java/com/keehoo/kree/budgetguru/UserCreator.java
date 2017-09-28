package com.keehoo.kree.budgetguru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserCreator extends AppCompatActivity {
    EditText login;
    EditText name;
    EditText lastName;
    EditText email;
    EditText password;
    Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creator);
        initViews();
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {
        String log = login.getText().toString();
        String nam = name.getText().toString();
        String lan = lastName.getText().toString();
        String ema = email.getText().toString();
        String psw = password.getText().toString();

        User user = new User(log, nam, lan, ema, psw);
        Toast.makeText(this, user.getLastName() + " created", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        login = (EditText) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        createUserButton = (Button) findViewById(R.id.create_user);
    }
}
