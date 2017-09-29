package com.keehoo.kree.budgetguru.Rest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUser extends AppCompatActivity {
    private RestInterface restClientImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user);
    }

    @Override
    protected void onResume() {
        super.onResume();

            restClientImpl = RestInterface.retrofit.create(RestInterface.class);
            Call<List<User>> getContacts = restClientImpl.listUsers();
            getContacts.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    List<User> contacts = response.body();
                   // ContactAdapter adapter = new ContactAdapter(MainActivity.this, contacts);
                   // recyclerView.setAdapter(adapter);
                    Toast.makeText(GetUser.this, "Success", Toast.LENGTH_SHORT).show();
                    int size = contacts.size();
                    Toast.makeText(GetUser.this, "Size of the received list : "+ size, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(GetUser.this, "Unfortunately there's a failure with your call: "+call.toString()+"\n the excpetion detail bewlow : \n"+
                            t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    System.out.println(call.toString());
                    System.out.println(t.getLocalizedMessage());
                }
            });
    }
}
