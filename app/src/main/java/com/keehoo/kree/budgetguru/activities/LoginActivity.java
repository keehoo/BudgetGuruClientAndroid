package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.User;
import com.keehoo.kree.budgetguru.repositories.SessionData;
import com.keehoo.kree.budgetguru.rest.RestInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_id)
    EditText loginEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.sign_in_button)
    SignInButton loginButton;

    @BindView(R.id.details_id)
    TextView detailsTextView;

    @BindView(R.id.user_image)
    ImageView imageView;

    @BindView(R.id.logout)
    Button logoutButton;

    public static final int RC_SIGN_IN = 991;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                 .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                     @Override
                     public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                         Toast.makeText(LoginActivity.this, "connection failed", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                 .build();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Login Activity", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
           // updateUI(true);
            Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();

            String detail = acct.getDisplayName()+"\n"+
                    acct.getEmail()+"\n"+
                    acct.getGivenName()+"\n"+
                    acct.getAccount().toString()+"\n"+
                    acct.getPhotoUrl();
            detailsTextView.setText(detail);
            if (acct.getPhotoUrl() != null) {
                Picasso.with(this).load(acct.getPhotoUrl()).into(imageView);
            }

            final User user = new User(acct.getGivenName()+"@"+acct.getEmail(), acct.getGivenName(), acct.getFamilyName(), acct.getEmail(), acct.getDisplayName());
            RestInterface restClientImpl = RestInterface.retrofit.create(RestInterface.class);
            final Call<User> user1 = restClientImpl.createUser(user);
            user1.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User retrieved = response.body();

                    Toast.makeText(LoginActivity.this, "Retrieved user id : "+retrieved.getId(), Toast.LENGTH_LONG).show();

                    new SessionData(LoginActivity.this).saveCurrentLoggedInUser(retrieved.getId());
                    new SessionData(LoginActivity.this).saveCurrentUserLogin(retrieved.getName());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    System.out.println(call.toString());
                    System.out.println(t.getLocalizedMessage());
                }
            });


        } else {
            Status status = result.getStatus();
            Toast.makeText(this, status.getStatusMessage()+" "+status.getStatusCode(), Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
          //  updateUI(false);
        }
    }

    @OnClick(R.id.logout)
    void logout() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        showToast("Logged out");
                    }
                });
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
