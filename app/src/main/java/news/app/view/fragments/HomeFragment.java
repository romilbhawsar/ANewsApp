package news.app.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import news.app.R;
import news.app.databinding.FragmentHomeBinding;
import news.app.model.beans.News;
import news.app.utility.CommonMethods;
import news.app.view.HomeActivity;
import news.app.view.adapter.NewsListAdapter;
import news.app.viewmodel.NewsViewModel;


/**
 * A Fragment class is being used to render list of some items.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private boolean isViewCreated;
    private FragmentHomeBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsListAdapter listAdapter;
    private NewsViewModel viewModel;

    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity)getActivity()).setActionBarTitle(getString(R.string.title_home_screen),false);
        /*Initialize views after fragment loaded*/
        if (!isViewCreated) {
            isViewCreated = true;
            initViews();
        }
    }

    /**
     * Use this method to initialize views.
     */
    public void initViews() {
        /*Initialization of list view*/
        recyclerView = binding.rvNewsList;
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        listAdapter = new NewsListAdapter(context, null);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            loading = false;
                            //Do pagination.. i.e. fetch new data

                        }
                    }
                }
            }

        });

        /*Initialization of Swipe to refresh*/
        swipeRefreshLayout = binding.layoutSwipeToRefresh;
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        viewModel = ViewModelProviders.of(this.getActivity()).get(NewsViewModel.class);

        /*Observer to notify message data changed*/
        viewModel.getMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {

                setRefreshingOff();

                if (!TextUtils.isEmpty(message))
                    CommonMethods.showAlertDialog(context, getString(R.string.alert), message);

            }
        });

        // Show progress bar while data loading
        swipeRefreshLayout.setRefreshing(true);

        /*Observer to notify list data changed*/
        viewModel.getNewsList().observe(this.getActivity(), new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {

                if (news != null && news.size()>0) {
                    // Recycler view setup
                    if (listAdapter != null) {
                        listAdapter.setNewsList(news);
                        listAdapter.notifyDataSetChanged();
                    }
                }

                setRefreshingOff();

            }
        });

    }


    @Override
    public void onRefresh() {
        if (viewModel != null) {
            viewModel.loadData();
        }
    }

    /**
     * This method is used to dismiss progress bar.
     */
    public void setRefreshingOff() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_item_search)
        {
            ((HomeActivity)getActivity()).addFragment(new SearchFragment());
        }
        return super.onOptionsItemSelected(item);
    }
}
