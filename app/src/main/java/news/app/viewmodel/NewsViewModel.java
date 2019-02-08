package news.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import news.app.R;
import news.app.model.AppDatabase;
import news.app.model.beans.News;
import news.app.repository.NewsRepository;
import news.app.utility.CommonMethods;

public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<List<News>> newsList;

    private MutableLiveData<String> message;

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<News>> getNewsList() {
        if(this.newsList == null)
        {
          this.newsList = new MutableLiveData<List<News>>();
          loadData();
        }
        return this.newsList;
    }

    public void loadData()
    {
        if (CommonMethods.isInternetConnected(getApplication())) {

            new AsyncTask<Void, Void, Void>() {

                List<News> data = null;

                @Override
                protected Void doInBackground(Void... voids) {
                    data = AppDatabase.getInstance(getApplication()).newsDAO().loadData(0,50);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(data != null)
                        newsList.setValue(data);
                }

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            message.setValue(getApplication().getString(R.string.msg_no_internet_connection));
        }

    }

    public MutableLiveData<String> getMessage() {

        if (message == null) {
            message = new MutableLiveData<String>();
        }
        return message;
    }

    public void setMessage(MutableLiveData<String> message) {
        this.message = message;
    }

}
