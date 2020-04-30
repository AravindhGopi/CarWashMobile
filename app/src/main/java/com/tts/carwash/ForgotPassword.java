package com.tts.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tts.carwash.model.LoginModel;
import com.tts.carwash.model.SMSModel;
import com.tts.carwash.network.LoginAPI;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {
    private LinearLayout firstStep, secondStep;
    private FloatingActionButton next;
    private Button verifyBtn;
    private PinView pinView;
    private TextView textU;
    private TextInputEditText mobile;
    private String generatedPIN;
    public static final String MyPREFERENCES = "user_details";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firstStep = findViewById(R.id.first_step);
        secondStep = findViewById(R.id.second_step);
        next = findViewById(R.id.next);
        verifyBtn = findViewById(R.id.verify_btn);
        pinView = findViewById(R.id.pinView);
        textU = findViewById(R.id.textView_noti);
        mobile = findViewById(R.id.mobile_number);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Toast.makeText(this, "Thanks " + sharedpreferences.getString("Name", null), Toast.LENGTH_LONG).show();
        firstStep.setVisibility(View.VISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }

        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    public void next() {
        firstStep.setVisibility(View.GONE);
        secondStep.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.textlocal.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI jsonPlaceHolderApi = retrofit.create(LoginAPI.class);

        Random random = new Random();
        generatedPIN = String.format("%04d", random.nextInt(10000));
        Toast.makeText(this, mobile.getText().toString(), Toast.LENGTH_SHORT).show();

        Call<List<SMSModel>> listCall = jsonPlaceHolderApi.getOTPResponse(mobile.getText().toString(), generatedPIN);

        listCall.enqueue(new Callback<List<SMSModel>>() {
            @Override
            public void onResponse(Call<List<SMSModel>> call, Response<List<SMSModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("TEST", "Code " + response.code());
                    return;
                }
                List<SMSModel> OTPResponse = response.body();
                System.out.println("onResponse");
                System.out.println(response.body().toString());

            }

            @Override
            public void onFailure(Call<List<SMSModel>> call, Throwable t) {
                Log.d("ERR", "R " + t.getMessage());
            }
        });

        Retrofit appURL = new Retrofit.Builder().baseUrl("http://www.ttsecom.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI loginAPIHolder = appURL.create(LoginAPI.class);
        Call<Object> listCall2 = loginAPIHolder.forgotPassword(mobile.getText().toString(), generatedPIN);

        listCall2.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "OTP SENT", Toast.LENGTH_SHORT).show();
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
    }

    public void verify() {
        String OTP = pinView.getText().toString();

        Retrofit appURL = new Retrofit.Builder().baseUrl("http://www.ttsecom.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI verifyOTPAPI = appURL.create(LoginAPI.class);
//        Toast.makeText(this,OTP,Toast.LENGTH_SHORT).show();
        Call<LoginModel> listCall3 = verifyOTPAPI.verifyOTP(mobile.getText().toString(), OTP);

        listCall3.enqueue(new Callback<LoginModel>() {
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

                        pinView.setLineColor(Color.GREEN);
                        textU.setText("OTP Verified");
                        textU.setTextColor(Color.GREEN);
                        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(i);
                    } else {
                        pinView.setLineColor(Color.RED);
                        textU.setText("X Incorrect OTP");
                        textU.setTextColor(Color.RED);
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

    public void goToLogin(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
