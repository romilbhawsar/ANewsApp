package news.app.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import news.app.R;
import news.app.api.APIConfig;
import news.app.api.APIDefinitionDAO;
import news.app.service.DataSyncing;
import news.app.view.fragments.HomeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private String TAG = HomeActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,R.layout.activity_home);

        // actionbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add home fragment as a base fragment
        addBaseFragment(new HomeFragment());

        // sync data
        startService(new Intent(this, DataSyncing.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /**
     * This method is used to add base fragment
     *
     * @param fragment to be added
     */
    private void addBaseFragment(Fragment fragment) {

        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName().toString()).commit();

    }


    /**
     * This method is used to add fragment
     *
     * @param fragment to be added
     */
    public void addFragment(Fragment fragment) {

        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
        .replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName().toString())
        .addToBackStack(fragment.getClass().getSimpleName().toString())
        .commit();
    }

    /**
     * This method is used to set action bar title.
     *
     * @param title to be set on actionbar
     */
    public void setActionBarTitle(String title,boolean isBackEnable) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isBackEnable);
        }
    }

    /**
     * Manage actionbar back press
      * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }



}
