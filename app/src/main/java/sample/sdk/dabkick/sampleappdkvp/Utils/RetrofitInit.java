package sample.sdk.dabkick.sampleappdkvp.Utils;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {

    private Context mContext;
    public static String BASE_URL = " https://stagingquery.dabkick.com/";
    private String basicAuth = "";

    public RetrofitInit(Context context) {
        this.mContext = context;
    }

    public retrofit2.Retrofit initializeRetrofit() {
        //code added to make a common request while calling post and get requests using retrofit
        //Adding header to retrofit request using okHttpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        client.readTimeoutMillis();
        client.connectTimeoutMillis();
        //Here we will handle the http request to insert user to server
        //Creating a RetrofitAdapter
        return new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)//Setting the Root URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
