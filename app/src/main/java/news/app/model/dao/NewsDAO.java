package news.app.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import news.app.model.beans.News;

/**
 * Contains the methods used for accessing the database.
 */
@Dao
public interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveNews(News news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveNewsList(List<News> list);

    @Query("SELECT * FROM news WHERE id = :newsId")
    LiveData<News> load(int newsId);

    @Query("SELECT * FROM news LIMIT :position OFFSET  :offset")
    LiveData<List<News>> loadData(int position, int offset);
}
