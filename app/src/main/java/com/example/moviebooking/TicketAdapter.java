package com.example.moviebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private ArrayList<Ticket> tickets;
    private Context context;

    public TicketAdapter(ArrayList<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_ticket, parent, false);
        return new TicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
        holder.seat.setText(tickets.get(position).getSeat());
        DataClient dataClient = APIUtils.getData();
        Call<List<Show>> callBack = dataClient.loadShowByID(tickets.get(position).getShowId());
        callBack.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                ArrayList<Show> shows = (ArrayList<Show>) response.body();
                DataClient dataClient1 = APIUtils.getData();
                Call<List<Movie>> call1 = dataClient1.loadMovieByID(shows.get(0).getMovieId());
                call1.enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        ArrayList<Movie> movies = (ArrayList<Movie>) response.body();
                        Picasso.get().load(APIUtils.Base_url + "poster/" + movies.get(0).getPoster()).into(holder.movieImg);
                        holder.movieName.setText(movies.get(0).getName());
                        holder.showInfo.setText(shows.get(0).getStart() + " - " + shows.get(0).getDate());

                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
                DataClient dataClient2 = APIUtils.getData();
                Call<List<Cinema>> call2 = dataClient2.loadCinemaByID(shows.get(0).getCinemaId());
                call2.enqueue(new Callback<List<Cinema>>() {
                    @Override
                    public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                        ArrayList<Cinema> cinema = (ArrayList<Cinema>) response.body();
                        holder.cinemaName.setText(cinema.get(0).getCinemaName());
                    }

                    @Override
                    public void onFailure(Call<List<Cinema>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(tickets!=null){
            return tickets.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImg;
        TextView movieName, cinemaName, showInfo, seat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.movieImgTicket);
            movieName = itemView.findViewById(R.id.movieNameTVTicket);
            cinemaName = itemView.findViewById(R.id.cinema_name_ticket);
            showInfo = itemView.findViewById(R.id.show_info_ticket);
            seat = itemView.findViewById(R.id.seat_selected_ticket);
        }
    }
}
