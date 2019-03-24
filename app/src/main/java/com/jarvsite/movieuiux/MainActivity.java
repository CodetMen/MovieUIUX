package com.jarvsite.movieuiux;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.jarvsite.movieuiux.adapter.ViewPagerAdapter;
import com.jarvsite.movieuiux.fragment.NowplayingFragment;
import com.jarvsite.movieuiux.fragment.UpcomingFragment;
import com.jarvsite.movieuiux.notification.AlarmPreference;
import com.jarvsite.movieuiux.notification.AlarmReceiver;
import com.jarvsite.movieuiux.notification.SchedulerTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tablayout) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    SchedulerTask schedulerTask;
    AlarmPreference alarmPreference;
    AlarmReceiver alarmReceiver;
    public Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // TabLayout and viewpager set
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        // set to show notification
        schedulerTask = new SchedulerTask(this);
        schedulerTask.createPeriodTask();

        calendar = Calendar.getInstance();
        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        startAlarm();
    }

    private void startAlarm(){
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 25);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String repeatTime = timeFormat.format(calendar.getTime());
        String repeatMessage = "See your favorite movie !";

        alarmPreference.setRepeatingTime(repeatTime);
        alarmPreference.setMovieReminder(repeatMessage);

        alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.REPEATING_ALARM, alarmPreference.getRepeatingTime(), alarmPreference.getMovieReminder());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowplayingFragment(), getString(R.string.now_playing));
        adapter.addFragment(new UpcomingFragment(), getString(R.string.upcoming));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // sending query from searchview
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.EXTRAS_JUDUL, query);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nowplaying) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_upcoming) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_favorite){
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_languages){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    This for searching
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}