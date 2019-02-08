package news.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import news.app.api.APIConfig;
import news.app.api.APIDefinitionDAO;
import news.app.model.AppDatabase;
import news.app.model.beans.News;
import news.app.utility.CommonMethods;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSyncing extends IntentService
{
    private String TAG = DataSyncing.class.getSimpleName();
    private int[] topStories;
    private List<News> newsList;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DataSyncing(String name) {
        super(name);
    }

    public DataSyncing() {
        super(DataSyncing.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getTopStories();
    }



    public void getTopStories()
    {
        try {
            if(CommonMethods.isInternetConnected(this))
            {
                APIDefinitionDAO apiConfig = APIConfig.getRetrofit().create(APIDefinitionDAO.class);

                Call<int[]> retrofit = apiConfig.getTopStories();

                retrofit.enqueue(new Callback<int[]>() {
                    @Override
                    public void onResponse(Call<int[]> call, Response<int[]> response) {
                        if (response != null) {
                            int[] responseArray = response.body();
                            topStories = responseArray;
                            getAllNews();
                        }
                    }

                    @Override
                    public void onFailure(Call<int[]> call, Throwable t) {
                        if (t != null) {
                            t.printStackTrace();
                        }
                    }
                });

            }else
            {

            }

        } catch (Exception e) {
            Log.e(TAG, "callAPI() : " + e.toString());
        }
    }


    public void getAllNews()
    {
        if(topStories !=null)
        {
            for(int i=0 ; i<topStories.length; i++)
            {
                int newsId = topStories[i];
                String api = "item/"+newsId+".json?print=pretty";
                APIConfig.callAPI(api, new APIConfig.APICallResponseListener() {
                    @Override
                    public void onResponseReceived(News response, String message) {
                        if(response !=null)
                        {
                            if(newsList == null)
                                newsList = new ArrayList<News>();

                            newsList.add(response);
                        }
                    }
                });

                if(i == topStories.length-1)
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            if(newsList !=null && newsList.size()>0)
                            AppDatabase.getInstance(DataSyncing.this).newsDAO().saveNewsList(newsList);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
