package news.app.api;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import news.app.BuildConfig;
import news.app.model.beans.News;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API Configuration class.
 */
public class APIConfig {

    private static String TAG = "APIConfig";

    /**
     * This method is used to get instance of retrofit.
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        // setting up for connection timeout
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(BuildConfig.CONNECTION_TIME_OUT, TimeUnit.MINUTES)
                .readTimeout(BuildConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(BuildConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

    }

    /**
     * Use this common method to call api
     *
     * @param api      to be called
     * @param listener call back response listener
     * @return
     */
    public static void callAPI(String api, final APICallResponseListener listener) {
        try {

            APIDefinitionDAO apiConfig = APIConfig.getRetrofit().create(APIDefinitionDAO.class);

            Call<News> retrofit = apiConfig.getFactsData(api);

            retrofit.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {

                    if (response != null) {
                        News responseString = response.body();
                        if (listener != null)
                            listener.onResponseReceived(responseString, null);
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    if (t != null) {
                        if (listener != null)
                            listener.onResponseReceived(null, t.getMessage());
                    }
                }

            });

        } catch (Exception e) {
            Log.e(TAG, "callAPI() : " + e.toString());
        }

    }

    /*Call back response listener*/
    public interface APICallResponseListener {

        public void onResponseReceived(News response, String message);

    }


}
