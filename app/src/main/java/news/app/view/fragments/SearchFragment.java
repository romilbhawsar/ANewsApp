package news.app.view.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import news.app.R;
import news.app.databinding.FragmentSearchBinding;
import news.app.view.HomeActivity;


/**
 * A Fragment class is being used to render list of searched items.
 */
public class SearchFragment extends Fragment{

    private Context context;
    private boolean isViewCreated;
    private FragmentSearchBinding binding;

    public SearchFragment() {
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
             binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity)getActivity()).setActionBarTitle("",true);
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

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {

        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        // Expand programmatically
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);

        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        MenuItemCompat.expandActionView(menuItem);

        // Set hint and the text colors
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint(getString(R.string.title_search));
        txtSearch.setHintTextColor(getResources().getColor(R.color.colorSearchHint));
        txtSearch.setTextColor(Color.WHITE);
        txtSearch.setEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                //searchingFor(query);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_item_search)
        {

        }
        return super.onOptionsItemSelected(item);
    }


}
