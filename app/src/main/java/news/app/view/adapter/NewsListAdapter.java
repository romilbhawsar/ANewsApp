package news.app.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import news.app.R;
import news.app.databinding.ListItemBinding;
import news.app.model.beans.News;


/**
 * Adapter class for news list.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListItemViewHolder> {

    private String TAG = "NewsListAdapter";
    private Context context;
    private List<News> newsList;
    private LayoutInflater layoutInflater;

    public NewsListAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public NewsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(context);
        ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false);
        return new NewsListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListItemViewHolder holder, int position) {
        try {
            //Bind data.
            holder.bindData(newsList.get(position));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public int getItemCount() {
        if (newsList != null)
            return newsList.size();
        return 0;
    }

    /* ViewHolder class for list items.*/
    public class NewsListItemViewHolder extends RecyclerView.ViewHolder {

        private ListItemBinding binding;

        public NewsListItemViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Provide data to view
        public void bindData(News news) {
           this.binding.setNews(news);
        }

    }


}
