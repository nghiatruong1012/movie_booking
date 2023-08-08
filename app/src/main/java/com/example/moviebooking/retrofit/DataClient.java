package com.example.moviebooking.retrofit;

import com.example.moviebooking.Cinema;
import com.example.moviebooking.Movie;
import com.example.moviebooking.Seat;
import com.example.moviebooking.Show;
import com.example.moviebooking.Ticket;
import com.example.moviebooking.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataClient {

    @FormUrlEncoded
    @POST("signup.php")
    Call<String> SignupData(@Field("fullname") String fullname
                            , @Field("phone") String phone
                            , @Field("birth") String birth
                            , @Field("gt") String gt
                            , @Field("username") String username
                            , @Field("password") String password
                            , @Field("email") String email);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> LoginData(@Field("username") String username
            , @Field("password") String password);

    @GET("loadMovie.php")
    Call<List<Movie>> loadMovie();
    @FormUrlEncoded
    @POST("loadMovieByID.php")
    Call<List<Movie>> loadMovieByID(@Field("movie_id") String movie_id);

    @GET("loadCinema.php")
    Call<List<Cinema>> loadCinema();
    @FormUrlEncoded
    @POST("loadCinemaByID.php")
    Call<List<Cinema>> loadCinemaByID(@Field("cinema_id") String cinema_id);

    @FormUrlEncoded
    @POST("loadShow.php")
    Call<List<Show>> loadShow(@Field("movie_id") String movie_id
                            , @Field("cinema_id") String cinema_id
                            , @Field("date") String date);
    @FormUrlEncoded
    @POST("loadShowByID.php")
    Call<List<Show>> loadShowByID(@Field("show_id") String show_id);
    @FormUrlEncoded
    @POST("loadSeat.php")
    Call<List<Seat>> loadSeat(@Field("show_id") String show_id);

    @FormUrlEncoded
    @POST("updateSeat.php")
    Call<List<Seat>> updateSeat(@Field("seat_id") String seat_id);
    @FormUrlEncoded
    @POST("insertTicket.php")
    Call<String> insertTicket (@Field("user_id") String user_id, @Field("seat_id") String seat_id);
    @FormUrlEncoded
    @POST("loadTicket.php")
    Call<List<Ticket>> loadTicket(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("insertTicketInfo.php")
    Call<String> insertTicketInfo (@Field("user_id") String user_id,
                                   @Field("show_id") String show_id,
                                   @Field("number_of_seat") String number_of_seat,
                                   @Field("seat") String seat);

    @FormUrlEncoded
    @POST("changePass.php")
    Call<String> changePass (@Field("id") String id, @Field("currentPassword") String currentPassword, @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<String> updateUser (@Field("id") String id,
                             @Field("fullname") String fullname,
                             @Field("phone") String phone,
                             @Field("birth") String birth,
                             @Field("gt") String gt,
                             @Field("email") String email);

}
