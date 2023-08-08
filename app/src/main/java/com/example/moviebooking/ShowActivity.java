package com.example.moviebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.moviebooking.CalendarUtils.daysInWeekArray;
import static com.example.moviebooking.CalendarUtils.monthYearFromDate;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    GridView showGridview;
    private ListView eventListView;

    Spinner cinemaSpinner;



    ArrayList<Cinema> cinemas = new ArrayList<Cinema>();

//    ArrayList<Show> shows = new ArrayList<>();
    public static String movieId, movieName, cinemaId;



    TextView selectDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        movieId =  intent.getStringExtra("movieId");
        movieName = intent.getStringExtra("movieName");
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        if(CalendarUtils.formattedDate(CalendarUtils.selectedDate) != null){
            selectDateTV.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        }


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        selectDateTV = findViewById(R.id.selectDateTV);
        cinemaSpinner = findViewById(R.id.cinemaSpinner);
        showGridview = findViewById(R.id.showGridview);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
//        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
        if(CalendarUtils.formattedDate(CalendarUtils.selectedDate) != null){
            selectDateTV.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        }
        DataClient dataClient = APIUtils.getData();
        Call<List<Cinema>> callBack = dataClient.loadCinema();
        callBack.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                cinemas = (ArrayList<Cinema>) response.body();
                ArrayList<String> cinemaName = new ArrayList<String>();
                for (Cinema cinema:cinemas){
                    cinemaName.add(cinema.getCinemaName());
                }
                ArrayAdapter adapter = new ArrayAdapter(ShowActivity.this, android.R.layout.simple_spinner_item, cinemaName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cinemaSpinner.setAdapter(adapter);
                cinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for (Cinema cinema:cinemas){
                            if(cinemaName.get(i).equals(cinema.getCinemaName())){
                                cinemaId = cinema.getId();
                            }
                        }
                        DataClient dataClient1 = APIUtils.getData();
                        Log.d("ZZZ", movieId);
                        Log.d("ZZZ", cinemaId);
                        Log.d("ZZZ", CalendarUtils.formattedDate(CalendarUtils.selectedDate));

                        Call<List<Show>> callBack1 = dataClient1.loadShow(movieId, cinemaId, CalendarUtils.selectedDate.toString());
                        callBack1.enqueue(new Callback<List<Show>>() {
                            @Override
                            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {

                                ArrayList<Show> shows = new ArrayList<>();
                                shows = (ArrayList<Show>) response.body();
                                Log.d("GGG", String.valueOf(response.body().size()));
//                                Log.d("GGG", shows.get(1).getId());
//                                Log.d("GGG", shows.get(2).getId());
                                if(shows.size() > 0){
                                    ShowAdapter adapter = new  ShowAdapter(ShowActivity.this, shows);
                                    showGridview.setAdapter(adapter);
                                }else {
                                    ShowAdapter adapter = new  ShowAdapter(ShowActivity.this, null);
                                    showGridview.setAdapter(adapter);
                                }


                            }

                            @Override
                            public void onFailure(Call<List<Show>> call, Throwable t) {
                                ShowAdapter adapter = new  ShowAdapter(ShowActivity.this, null);
                                showGridview.setAdapter(adapter);

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Cinema>> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        setEventAdpater();
    }

//    private void setEventAdpater()
//    {
//        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
//        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
//        eventListView.setAdapter(eventAdapter);
//    }

//    public void newEventAction(View view)
//    {
//        startActivity(new Intent(this, EventEditActivity.class));
//    }
}