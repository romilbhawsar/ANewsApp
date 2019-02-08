package news.app.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import news.app.model.beans.News;
import news.app.model.dao.NewsDAO;
import news.app.utility.Constants;

/**
 * Database class for the application
 */
@Database(entities = {News.class},version = Constants.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance of database.
    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context)
    {
        if(appDatabase == null)
        {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration() // We can add Migration to manage different version of database when we need to modify in database
                    .build();
        }
        return appDatabase;
    }

    public abstract NewsDAO newsDAO();
}
