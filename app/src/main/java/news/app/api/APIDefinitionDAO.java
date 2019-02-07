package news.app.api;

import news.app.model.beans.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * This class is being used to define API definition.
 */
public interface APIDefinitionDAO {

    @GET("topstories.json?print=pretty")
    public Call<int[]> getTopStories();

    @GET("{api}")
    public Call<News> getFactsData(@Path("api") String api);

}
