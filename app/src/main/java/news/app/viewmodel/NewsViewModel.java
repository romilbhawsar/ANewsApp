package news.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import news.app.model.beans.News;
import news.app.repository.NewsRepository;

public class NewsViewModel extends ViewModel{

    private LiveData<List<News>> newsList;

    private MutableLiveData<String> message;

    private NewsRepository newsRepository;

    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void init(int userId) {
        if (this.newsList != null) {
            return;
        }
        //newsList = newsRepository.getUser(userId);
    }

    public LiveData<List<News>> getNewsList() {
        return this.newsList;
    }

    public void loadData()
    {

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
