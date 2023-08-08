package com.example.moviebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    RecyclerView ticketRecycleView;
    TicketAdapter adapter;
    TextView noTicketTV;

    ArrayList<Ticket> ticketArrayList;

    ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ticketRecycleView = findViewById(R.id.ticketRecycleView);
        noTicketTV = findViewById(R.id.noTicketTV);
        Intent intent = getIntent();
        userArrayList = intent.getParcelableArrayListExtra("userArray");
        DataClient dataClient = APIUtils.getData();
        Call<List<Ticket>> call = dataClient.loadTicket(userArrayList.get(0).getId());
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                ticketArrayList = (ArrayList<Ticket>) response.body();
                Log.d("ccc", String.valueOf(ticketArrayList.size()) );
                if(ticketArrayList.size() < 1){
                    noTicketTV.setText("Chưa đặt vé");
                }
                adapter = new TicketAdapter(ticketArrayList, TicketActivity.this);
                ticketRecycleView.setAdapter(adapter);
                ticketRecycleView.setLayoutManager(new LinearLayoutManager(TicketActivity.this));
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                noTicketTV.setText("Chưa đặt vé");

            }
        });

    }
}