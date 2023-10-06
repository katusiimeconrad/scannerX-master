package maulik.barcodescanner.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import maulik.barcodescanner.api.RetrofitClientInstance;
import maulik.barcodescanner.api.Routes;
import maulik.barcodescanner.constants.AppConstants;
import maulik.barcodescanner.constants.StringConstants;
import maulik.barcodescanner.databinding.ActivityLoginBinding;
import maulik.barcodescanner.ui.MainActivity;
import maulik.barcodescanner.user.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LOGIN ACTIVITY";
    private ActivityLoginBinding binding;
    private Routes api;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCE, MODE_PRIVATE);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialize View Items
        initViews(binding);
    }

    /**
     * Initializes the View Items. Previously -> findViewById
     *
     * @param binding - Activity Binding
     */
    private void initViews(ActivityLoginBinding binding) {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        api = retrofit.create(Routes.class);

        Button login = binding.buttonLogin;

        TextView username = binding.loginUsername;
        TextView password = binding.loginPassword;


        login.setOnClickListener(v -> {
            doAuth(username.getText().toString(), password.getText().toString());
        });


    }

    private void updateUiWithUser(String name) {
        String welcome = "Welcome" + name;
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    /**
     * Upon successful authentication, redirects the user into the app
     */
    private void launchMainActivity() {
        //Launch Main Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        //Remove Login Activity from Stack
        finish();
    }

    private void doAuth(String username, String password) {
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("username", username);
        paramObject.addProperty("password", password);

        Call<UserResponse> call = api.login(paramObject);

        Log.d("TAG", "doAuth: " + paramObject);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("User logged in ", response.toString());

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    sharedPreferences.edit().putString(StringConstants.USERNAME, response.body().getUser().getUsername()).apply();
                    sharedPreferences.edit().putString(StringConstants.PASSWORD, password).apply();
                    sharedPreferences.edit().putString(StringConstants.EMAIL, response.body().getUser().getEmail()).apply();
                    sharedPreferences.edit().putString(StringConstants.TOKEN, response.body().getUser().getToken()).apply();
                    sharedPreferences.edit().putString(StringConstants.PROFILE_PICTURE_URL, response.body().getUser().getImageUrl()).apply();
                    sharedPreferences.edit().putBoolean(StringConstants.IS_LOGGED_IN, true).apply();

                    performTaskOnSuccessfulLogin(response);
                } else {
                    Toast.makeText(getApplicationContext(), "Log in Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Login Activity", "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void performTaskOnSuccessfulLogin(Response<UserResponse> response) {
        launchMainActivity();
    }


}