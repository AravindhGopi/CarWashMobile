package com.tts.carwash.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(final Response<?> response) {

        JSONObject bodyObj = null;

        boolean success;

        ArrayList messages = new ArrayList<>();


        try {

            String errorBody = response.errorBody().string();


            if (errorBody != null) {

                bodyObj = new JSONObject(errorBody);


                success = bodyObj.getBoolean("success");

                JSONArray errors = bodyObj.getJSONArray("errors");


                for (int i = 0; i < errors.length(); i++) {

                    messages.add(errors.get(i));

                }

            } else {

                success = false;

                messages.add("Unable to parse error");

            }

        } catch (Exception e) {

            e.printStackTrace();


            success = false;

            messages.add("Unable to parse error");

        }


        return new APIError.Builder()

                .success(false)
                .messages(messages)
                .build();
    }
}