package com.example.moviebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebooking.fragment.UserFragment;
import com.example.moviebooking.fragment.ViewPagerAdapter;
import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ArrayList<Movie> movies = new ArrayList<>();

    RecycleDataAdapter adapter;

    TextView name_tv;


    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    static ArrayList<User> userArrayList;

    ViewPager slider;
    PhotoAdapter photoAdapter;
    CircleIndicator circleIndicator;

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        viewPager = findViewById(R.id.view_pager);
//        bottomNavigationView = findViewById(R.id.bottom_nav);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.menu_movie).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.menu_news).setChecked(true);
//                        break;
//                    case 2:
//                        bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menu_movie:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.menu_news:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case R.id.menu_user:
//                        viewPager.setCurrentItem(2);
//                        break;
//                }
//                return true;
//            }
//        });

        Intent intent = getIntent();
        userArrayList = intent.getParcelableArrayListExtra("mangUser");
        Log.d("NNN", userArrayList.get(0).getFullname());
        recyclerView = findViewById(R.id.recycleView);
        slider = findViewById(R.id.viewPager);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.mainLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        circleIndicator = findViewById(R.id.circleIndicator);
        View head_nav = navigationView.getHeaderView(0);
        name_tv = head_nav.findViewById(R.id.name_tv);
        name_tv.setText(userArrayList.get(0).getFullname());
        photoAdapter = new PhotoAdapter(this, getListPhoto());
        slider.setAdapter(photoAdapter);
        circleIndicator.setViewPager(slider);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        DataClient dataClient = APIUtils.getData();
        Call<List<Movie>> callback = dataClient.loadMovie();
        callback.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movies = (ArrayList<Movie>) response.body();
                Log.d("BBB", movies.get(0).getName());
                Log.d("BBB", movies.get(0).getDescription());
                Log.d("BBB", movies.get(0).getPoster());
                Log.d("BBB", String.valueOf(movies.size()));
                adapter = new RecycleDataAdapter(movies, MainActivity.this);
                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        //return inflater.inflate(R.layout.fragment_movie, container, false);
    }
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.ads1));
        list.add(new Photo(R.drawable.ads2));
        list.add(new Photo(R.drawable.ads3));
        list.add(new Photo(R.drawable.ads4));
        list.add(new Photo(R.drawable.ads5));
        return list;
    }

    public static ArrayList getMyData() {
        return userArrayList;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putParcelableArrayListExtra("userArray", userArrayList);
            startActivity(intent);
        }else if(id == R.id.menu_change_pass){
            Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
            intent.putParcelableArrayListExtra("userArray", userArrayList);
            startActivity(intent);
        }else if(id == R.id.menu_history){
            Intent intent = new Intent(MainActivity.this, TicketActivity.class);
            intent.putParcelableArrayListExtra("userArray", userArrayList);
            startActivity(intent);
        }else if(id == R.id.menu_log_ou){
            MainActivity.this.finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}