package com.example.sistem03user.noticiasretrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sistem03user.noticiasretrofit.R;
import com.example.sistem03user.noticiasretrofit.adapter.MoviesAdapter;
import com.example.sistem03user.noticiasretrofit.model.Movie;
import com.example.sistem03user.noticiasretrofit.model.MoviesResponse;
import com.example.sistem03user.noticiasretrofit.rest.ApiClient;
import com.example.sistem03user.noticiasretrofit.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG= MainActivity.class.getSimpleName();

    private final static String API_KEY="84021f6725ccd7b9f876727e5604a43c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(API_KEY.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please obtain your APIKEY from themovied", Toast.LENGTH_SHORT).show();
            return;

        }

        final RecyclerView recyclerView= findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);


        Call<MoviesResponse> call= apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode =response.code();
                List<Movie> movies= response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

                Log.e(TAG, t.toString());


            }
        });
    }
}
