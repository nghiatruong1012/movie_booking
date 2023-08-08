package com.example.moviebooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatActivity extends AppCompatActivity{

    private ArrayList<Seat> seatList;
    private ArrayList<Seat> selectedSeats;
    String showId;
    Button reserve_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        GridLayout gridLayout = findViewById(R.id.seatGrid);
        reserve_btn = findViewById(R.id.reserve_button);
        selectedSeats = new ArrayList<>();
        Intent intent = getIntent();
        showId = intent.getStringExtra("showID");
        Log.d("www", showId);
        DataClient dataClient = APIUtils.getData();
        Call<List<Seat>> callBack = dataClient.loadSeat(showId);
        callBack.enqueue(new Callback<List<Seat>>() {
            @Override
            public void onResponse(Call<List<Seat>> call, Response<List<Seat>> response) {
                seatList = (ArrayList<Seat>) response.body();
                if(seatList!= null){
                    Log.d("www", seatList.get(0).getNumber());
                }
// Add seats to the list
//                for (int i = 1; i <= 20; i++) {


                for (Seat seat: seatList
                 ) {
                    Button seatButton = new Button(SeatActivity.this);
                    seatButton.setText(String.valueOf(seat.getNumber()));
                    seatButton.setTag(seat.getNumber());
                    seatButton.setBackgroundResource(R.drawable.seat_button);
//                    seatButton.setBackground(ContextCompat.getDrawable(SeatActivity.this, R.drawable.button_background));
//                    seatButton.setTextColor(Color.WHITE);
//                   seatButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//                  seatButton.setPadding(16, 16, 16, 16);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.setMargins(8, 8, 8, 8); // Set left, top, right, bottom margins
                    layoutParams.width = 0; // Set the width to 0 for equal width distribution
                    layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    seatButton.setLayoutParams(layoutParams);
                    Log.d("nnn", seat.getNumber());
                    Log.d("nnn", seat.getStatus());
                if(seat.getStatus().equals("booked")){
//                    seat2List.add(new Seat2(i, true));
//                    seatButton.setBackgroundColor(Color.RED);
                    seatButton.setBackgroundResource(R.drawable.booked_seat_button);
                }else if(seat.getStatus().equals("available")){
//                    seat2List.add(new Seat2(i, false));
//                    seatButton.setBackgroundColor(Color.GREEN);
                    seatButton.setBackgroundResource(R.drawable.seat_button);
                }
                    seatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            int seatNumber = (int) v.getTag();
                            Seat selectedSeat = getSeatByNumber(Integer.valueOf(seat.getNumber()));

                            if (selectedSeat.getStatus().equals("available")) {
                                if (selectedSeats.contains(selectedSeat)) {
                                    // Seat is already selected, unselect it
                                    selectedSeats.remove(selectedSeat);
                                    v.setBackgroundResource(R.drawable.seat_button);
                                } else {
                                    // Seat is not selected, select it
                                    selectedSeats.add(selectedSeat);
//                                    v.setBackgroundColor(Color.BLUE);
                                    v.setBackgroundResource(R.drawable.select_seat_button);
                                }
//                        selectedSeat.setAvailable(false);
//                        v.setBackgroundColor(Color.YELLOW);
//                        Toast.makeText(SeatActivity.this, "Seat " + seatNumber + " booked.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SeatActivity.this, "Seat " + Integer.valueOf(seat.getNumber()) + " is already booked.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    gridLayout.addView(seatButton);
            }

//            seatButton.setBackgroundColor(Color.GREEN);
           }
//            }

            @Override
            public void onFailure(Call<List<Seat>> call, Throwable t) {
                Log.d("www", t.getMessage());
            }
        });
//        seatList = new ArrayList<>();
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSeats.size() > 0) {
                    for (Seat seat : selectedSeats) {
//                        seat.setAvailable(false);
                        // Update the UI to reflect the change
//                        Button seatButton = getSeatButtonBySeat(seat);
//                        if (seatButton != null) {
//                            seatButton.setBackgroundColor(Color.RED);
//                        }
                        Log.d("qqq", seat.getNumber());
                    }
                    Intent intent1 = new Intent(SeatActivity.this, BookActivity.class);
                    Log.d("qqq", showId);
                    intent1.putExtra("showId", showId);
                    intent1.putExtra("selectedSeat", selectedSeats);
                    startActivity(intent1);
                    Toast.makeText(SeatActivity.this, "Selected seats booked.", Toast.LENGTH_SHORT).show();
                    selectedSeats.clear();
                } else {
                    Toast.makeText(SeatActivity.this, "No seats selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private Seat getSeatByNumber(int seatNumber) {
        for (Seat seat : seatList) {
            if (Integer.valueOf(seat.getNumber())== seatNumber) {
                return seat;
            }
        }
        return null;
    }

}
