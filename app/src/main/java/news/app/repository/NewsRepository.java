package news.app.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import java.util.List;
import news.app.model.AppDatabase;
import news.app.model.beans.News;
import news.app.model.dao.NewsDAO;

public class NewsRepository {

    private NewsDAO newsDAO;

    public NewsRepository(NewsDAO newsDAO){
        this.newsDAO = newsDAO;
    }

    public LiveData<List<News>> getData()
    {
        LiveData<List<News>> data = newsDAO.loadLiveData(0,50);

        if(data !=null)
            return data;

        MutableLiveData<List<News>> list = new MutableLiveData<List<News>>();

        return data;
    }

}
