package com.tts.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tts.carwash.network.LoginAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText user_name;
    TextInputEditText email;
    TextInputEditText password;
    public static final String MyPREFERENCES = "user_details";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user_name = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    public void saveProfile(View view){
        Retrofit appURL = new Retrofit.Builder().baseUrl("http://www.ttsecom.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI loginAPIHolder = appURL.create(LoginAPI.class);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Toast.makeText(this, "Thanks " + sharedpreferences.getString("Name", null), Toast.LENGTH_LONG).show();
        String token = sharedpreferences.getString("token", null);
        token = "Bearer "+token;
        Call<Object> listCall2 = loginAPIHolder.saveProfile(token,user_name.getText().toString(), email.getText().toString(), password.getText().toString());

        listCall2.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(i);
                } else {
                    try {
                        if (null != response.errorBody()) {
                            String errorResponse = response.errorBody().string();
                            Log.d("LOG_TAG", "errorResponse: " + errorResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ERR", "R " + t.getMessage());
            }
        });
//        Toast.makeText(getApplicationContext(),"Profile Saved", Toast.LENGTH_SHORT).show();
    }
}
