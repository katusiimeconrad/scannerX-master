package maulik.barcodescanner.api;

import com.google.gson.JsonObject;

import maulik.barcodescanner.user.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Routes to the API endpoints
 */
public interface Routes {

    /*------------------------Auth Routes--------------------------------*/
    @Headers("Content-Type: application/json")
    @POST("Users/LoginUser")
    Call<UserResponse> login(@Body JsonObject body);

}
