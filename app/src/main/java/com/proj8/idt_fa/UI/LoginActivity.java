package com.proj8.idt_fa.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.proj8.idt_fa.ApiHandling.API;
import com.proj8.idt_fa.Basic.HelperFunctions;
import com.proj8.idt_fa.Model.Login.LoginModel;
import com.proj8.idt_fa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    private EditText password,username;
    String BaseUrl;
    String usernameStr, passwordStr;
    HelperFunctions helperFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password= findViewById(R.id.password);
        loginbtn=(Button) findViewById(R.id.loginbtn);
        helperFunctions=new HelperFunctions();
        loginbtn.setOnClickListener(v -> {
            usernameStr = username.getText().toString().trim();
            passwordStr = password.getText().toString().trim();
            if (username.length() > 0) {
                if (password.length() > 0) {
                    callLoginApi(usernameStr,passwordStr);
                } else {
                    helperFunctions.showToastMessage(LoginActivity.this, "Enter Password");
                }

            } else {
                helperFunctions.showToastMessage(LoginActivity.this, "Enter UserName");
            }
        });
    }

    private void callLoginApi(String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HelperFunctions.BaseURL)
//                .client(SSLCertificate.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API apiService = retrofit.create(API.class);

        Call<LoginModel> call = apiService.getloginmodels(
                username,
                password
        );
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel responseObject = response.body();
                    if (responseObject != null) {
                        Log.d("User",username+"pass"+password);
                        Toast.makeText(getApplicationContext(), "You are Successfully Logged in", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = getSharedPreferences("IdtUser", MODE_PRIVATE).edit();
                        editor.putString("Username",response.body().getData().getUserName());
                        editor.putString("UPlantName",response.body().getData().getPlantName());
                        editor.putString("UPlantID",response.body().getData().getPlant());
                        editor.putBoolean("LoggedIn", true);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        finish();
                    }
                } else {
                    Log.e("LoginOldActivity", "Request failed with code: " + response.code());
                    HelperFunctions.showToastMessage(LoginActivity.this,"Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("LoginActivity", t.toString());
                HelperFunctions.showToastMessage(LoginActivity.this,"You have entered Wrong Email and Password");
            }
        });
    }
}