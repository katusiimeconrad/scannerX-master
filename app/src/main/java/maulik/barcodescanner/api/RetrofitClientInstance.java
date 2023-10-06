package maulik.barcodescanner.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An instance of this class is used to create a retrofit instance
 *
 * @author Conrad
 * @version 1.1
 */
public class RetrofitClientInstance {
    private static final String BASE = "test.pahappa.net/hashsecurity";
    private static final String BASE_URL = "https://" + BASE + "/api/v1/";
    /*
     * Creates an Http logging interceptor to log the request and response body
     */
    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

//    private static final String TEST_URL = "https://test.pahappa.net/mildi/api/v1/";
//    private static final String TEST_URL = "http://mildi.pahappa.net/mildi/api/v1/";
    /*
     * Creates an instance of the OkHttp client to be used by retrofit
     */
    static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private static Retrofit retrofit;

    /**
     * This method is used to create an instance of the retrofit object
     *
     * @return Retrofit - Retrofit object
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * This method is used to create an instance of the Routes interface which is used to make API calls
     *
     * @return Routes - Retrofit Interface for API calls
     */
    public static Routes getRoutes() {
        return getRetrofitInstance().create(Routes.class);
    }
}
