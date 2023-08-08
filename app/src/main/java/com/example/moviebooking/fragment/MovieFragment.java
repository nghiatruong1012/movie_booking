package com.example.moviebooking.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviebooking.Movie;
import com.example.moviebooking.Photo;
import com.example.moviebooking.PhotoAdapter;
import com.example.moviebooking.R;
import com.example.moviebooking.RecycleDataAdapter;
import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Movie> movies = new ArrayList<>();

    RecycleDataAdapter adapter;

    ViewPager slider;
    PhotoAdapter photoAdapter;
    CircleIndicator circleIndicator;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        // Inflate the layout for this fragment
          //      setContentView(R.layout.activity_main);
        recyclerView = view.findViewById(R.id.recycleView);
        slider = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleIndicator);
        photoAdapter = new PhotoAdapter(getActivity(), getListPhoto());
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
                adapter = new RecycleDataAdapter(movies, getActivity());
                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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

}