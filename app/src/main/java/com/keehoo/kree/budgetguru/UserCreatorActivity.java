package com.keehoo.kree.budgetguru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.Rest.RestInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreatorActivity extends AppCompatActivity {

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.lastname)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.create_user)
    Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creator);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.create_user)
    protected void createUser() {
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
                Toast.makeText(UserCreatorActivity.this, "success --> response code: "+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserCreatorActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                System.out.println(call.toString());
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


}
