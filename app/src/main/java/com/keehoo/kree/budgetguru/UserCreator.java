package com.keehoo.kree.budgetguru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.Rest.RestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
      //  startActivity(new Intent(this, GetUser.class));
    }

    private void createUser() {
        String log = login.getText().toString();
        String nam = name.getText().toString();
        String lan = lastName.getText().toString();
        String ema = email.getText().toString();
        String psw = password.getText().toString();

        User user = new User(log, nam, lan, ema, psw);
        Toast.makeText(this, user.getLastName() + " created", Toast.LENGTH_SHORT).show();

        RestInterface restClientImpl = RestInterface.retrofit.create(RestInterface.class);
        Call<Void> user1 = restClientImpl.createUser(user);
        user1.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UserCreator.this, "success --> response code: "+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserCreator.this, "Failure", Toast.LENGTH_SHORT).show();
                System.out.println(call.toString());
                System.out.println(t.getLocalizedMessage());
            }
        });
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
