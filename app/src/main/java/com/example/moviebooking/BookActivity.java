package com.example.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebooking.fragment.MovieFragment;
import com.example.moviebooking.fragment.UserFragment;
import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;
import com.squareup.picasso.Picasso;
import com.vnpay.authentication.VNP_AuthenticationActivity;
import com.vnpay.authentication.VNP_SdkCompletedCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    TextView userName, cinemaName, showInfo, seatSelected, price_tv, movieNameTV;
    ImageView movieImg;
    String userNameStr, showIdString;
    Button bookBtn;
    ArrayList<Seat> selectedSeat;
    ArrayList<User> userArrayList;
    ArrayList<Show> showArrayList;
    ArrayList<Cinema> cinemaArrayList;
    ArrayList<Movie> movieArrayList;
    String seatStr = "";
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        userName = findViewById(R.id.nameUser);
        cinemaName = findViewById(R.id.cinema_name);
        price_tv = findViewById(R.id.price_tv);
        bookBtn = findViewById(R.id.bookBtn);
        showInfo = findViewById(R.id.show_info);
        seatSelected = findViewById(R.id.seat_selected);
        movieNameTV = findViewById(R.id.movieNameTV);
        movieImg = findViewById(R.id.movieImg);
        userArrayList = MainActivity.getMyData();
        userNameStr = userArrayList.get(0).getFullname();
        userName.setText(userNameStr);
        Intent intent = getIntent();
        selectedSeat = intent.getParcelableArrayListExtra("selectedSeat");
        if(selectedSeat!=null){
            for (Seat seat : selectedSeat
            ) {
                seatStr += " " + seat.getNumber().toString();
                seatSelected.setText(seatStr);
            }
        }else {
            seatSelected.setText("null");
        }
        showIdString = intent.getStringExtra("showId");
        Log.d("mmm", showIdString);
        DataClient dataClient = APIUtils.getData();
        Call<List<Show>> callBack = dataClient.loadShowByID(showIdString);
        callBack.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                showArrayList = (ArrayList<Show>) response.body();
                showInfo.setText(showArrayList.get(0).getStart() + "-" + showArrayList.get(0).getDate());
                price = (selectedSeat.size() * Integer.valueOf(showArrayList.get(0).getPrice()) );
                price_tv.setText(String.valueOf(price));
                DataClient dataClient1 = APIUtils.getData();
                Call<List<Cinema>> callBack1 = dataClient1.loadCinemaByID(showArrayList.get(0).getCinemaId());
                callBack1.enqueue(new Callback<List<Cinema>>() {
                    @Override
                    public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                        cinemaArrayList = (ArrayList<Cinema>) response.body();
                        cinemaName.setText(cinemaArrayList.get(0).getCinemaName());
                    }

                    @Override
                    public void onFailure(Call<List<Cinema>> call, Throwable t) {

                    }
                });
                DataClient dataClient2 = APIUtils.getData();
                Call<List<Movie>> call2 = dataClient2.loadMovieByID(showArrayList.get(0).getMovieId());
                call2.enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieArrayList = (ArrayList<Movie>) response.body();
                        movieNameTV.setText(movieArrayList.get(0).getName());
                        Picasso.get().load(APIUtils.Base_url + "poster/" + movieArrayList.get(0).getPoster()).into(movieImg);
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {

            }
        });

    bookBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            for (Seat seat: selectedSeat
//                 ) {
//                DataClient dataClient1 = APIUtils.getData();
//                Call<List<Seat>> callBack = dataClient1.updateSeat(seat.getId());
//                callBack.enqueue(new Callback<List<Seat>>() {
//                    @Override
//                    public void onResponse(Call<List<Seat>> call, Response<List<Seat>> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Seat>> call, Throwable t) {
//
//                    }
//                });
//                DataClient dataClient2 = APIUtils.getData();
//                Call<String> call = dataClient1.insertTicket(userArrayList.get(0).getId(), seat.getId());
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//            }
//            DataClient dataClient3 = APIUtils.getData();
//            Call<String> call3 = dataClient3.insertTicketInfo(userArrayList.get(0).getId(),
//                    showIdString, String.valueOf(selectedSeat.size()), seatStr);
//            call3.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//
//                }
//            });
////            finish();
////            setContentView(R.layout.activity_main);
//            Intent intent1 = new Intent(BookActivity.this, MainActivity.class);
//            intent1.putExtra("mangUser", userArrayList);
//            startActivity(intent1);
//            finish();

            openSdk();
        }
    });

    }
    public void openSdk() {
        Intent intent = new Intent(this, VNP_AuthenticationActivity.class);
        intent.putExtra("url", APIUtils.Base_url + "vnpay_php/vnpay_create_payment.php?amount=" + price); //bắt buộc, VNPAY cung cấp
        intent.putExtra("tmn_code", "FAHASA03"); //bắt buộc, VNPAY cung cấp
        intent.putExtra("scheme", "resultactivity"); //bắt buộc, scheme để mở lại app khi có kết quả thanh toán từ mobile banking
        intent.putExtra("is_sandbox", true); //bắt buộc, true <=> môi trường test, true <=> môi trường live
        VNP_AuthenticationActivity.setSdkCompletedCallback(new VNP_SdkCompletedCallback() {
            @Override
            public void sdkAction(String action) {
                Log.wtf("SplashActivity", "action: " + action);
                //action == AppBackAction
                //Người dùng nhấn back từ sdk để quay lại

                //action == CallMobileBankingApp
                //Người dùng nhấn chọn thanh toán qua app thanh toán (Mobile Banking, Ví...)
                //lúc này app tích hợp sẽ cần lưu lại cái PNR, khi nào người dùng mở lại app tích hợp thì sẽ gọi kiểm tra trạng thái thanh toán của PNR Đó xem đã thanh toán hay chưa.

                //action == WebBackAction
                //Người dùng nhấn back từ trang thanh toán thành công khi thanh toán qua thẻ khi url có chứa: cancel.sdk.merchantbackapp

                //action == FaildBackAction
                //giao dịch thanh toán bị failed

                if(action == "SuccessBackAction"){
                    for (Seat seat: selectedSeat
                 ) {
                DataClient dataClient1 = APIUtils.getData();
                Call<List<Seat>> callBack = dataClient1.updateSeat(seat.getId());
                callBack.enqueue(new Callback<List<Seat>>() {
                    @Override
                    public void onResponse(Call<List<Seat>> call, Response<List<Seat>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<Seat>> call, Throwable t) {

                    }
                });
                DataClient dataClient2 = APIUtils.getData();
                Call<String> call = dataClient1.insertTicket(userArrayList.get(0).getId(), seat.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
            DataClient dataClient3 = APIUtils.getData();
            Call<String> call3 = dataClient3.insertTicketInfo(userArrayList.get(0).getId(),
                    showIdString, String.valueOf(selectedSeat.size()), seatStr);
            call3.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
                    Toast.makeText(BookActivity.this, "Thanh toan thanh cong", Toast.LENGTH_SHORT).show();
//            finish();
//            setContentView(R.layout.activity_main);
            Intent intent1 = new Intent(BookActivity.this, MainActivity.class);
            intent1.putExtra("mangUser", userArrayList);
            startActivity(intent1);
            finish();
                } else if (action == "FaildBackAction") {
                    Toast.makeText(BookActivity.this, "Thanh that bai", Toast.LENGTH_SHORT).show();
                } else if (action == "WebBackAction") {
                    Toast.makeText(BookActivity.this, "Huy thanh toan", Toast.LENGTH_SHORT).show();
                }

                //thanh toán thành công trên webview

            }
        });
        startActivity(intent);
    }

}