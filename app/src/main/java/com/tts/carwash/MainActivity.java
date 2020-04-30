package com.tts.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tts.carwash.model.LoginModel;
import com.tts.carwash.network.LoginAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private View parent_view;
    public static final String MyPREFERENCES = "user_details";
    private TextInputEditText mobile;
    private TextInputEditText password;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent_view = findViewById(android.R.id.content);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        mobile = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        ((View) findViewById(R.id.sign_up_for_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Sign up for an account", Snackbar.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                searchAction();
            }
        });

//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("Name", "testing");
//        editor.putString("id","123");
//        editor.commit();

//        Retrofit retrofitLogin = new Retrofit.Builder().baseUrl("ttsecom.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        LoginAPI loginAPIHolder = retrofitLogin.create(LoginAPI.class);
//        Call<List<LoginModel>> listCall2 = loginAPIHolder.getLoginResponse(mobile.getText().toString(), generatedPIN);
//
//        listCall2.enqueue(new Callback<List<LoginModel>>() {
//            @Override
//            public void onResponse(Call<List<LoginModel>> call, Response<List<LoginModel>> response) {
//                if (!response.isSuccessful()) {
//                    Log.d("TEST", "Code " + response.code());
//                    return;
//                }
//                List<LoginModel> OTPResponse = response.body();
////                System.out.println("onResponse");
////                System.out.println(response.body().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<List<LoginModel>> call, Throwable t) {
//                Log.d("ERR", "R " + t.getMessage());
//            }
//        });
    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        fab.setAlpha(0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                fab.setAlpha(1f);
//                Snackbar.make(parent_view, "Login data submitted", Snackbar.LENGTH_SHORT).show();

                Retrofit appURL = new Retrofit.Builder().baseUrl("http://www.ttsecom.in/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginAPI loginAPIHolder = appURL.create(LoginAPI.class);
                Call<LoginModel> loginAPI = loginAPIHolder.getLoginResponse(mobile.getText().toString(), password.getText().toString());

                loginAPI.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
//                    Gson gson = new Gson();
//                    String successResponse = gson.toJson(response.body());
//                    Log.d("LOG_TAG", "successResponse: " + successResponse);
//                    try {
//                        JSONObject jsonObject = new JSONObject(successResponse);
//                    }catch (JSONException err){
//                        Log.d("Error", err.toString());
//                    }
                            LoginModel OTPResponse = response.body();
//                    Log.d("TEST", "Test" + OTPResponse.getUser());
                            if (OTPResponse.isSuccess()) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("Name", OTPResponse.getName());
                                editor.putString("id", OTPResponse.getId());
                                editor.putString("token", OTPResponse.getToken());
                                editor.commit();

                                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(i);
                            }
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
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Log.d("ERR", "R " + t.getMessage());
                    }
                });

            }
        }, 1000);
    }

    public void goToSignUp(View view){
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }
    public void goToForgotPassword(View view){
        Intent i = new Intent(this,ForgotPassword.class);
        startActivity(i);
    }
}
