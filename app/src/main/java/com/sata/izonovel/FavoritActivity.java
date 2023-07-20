package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sata.izonovel.Model.FavoriteNovelRequest;
import com.sata.izonovel.Model.FavoriteNovelResponse;
import com.sata.izonovel.Model.ListNovelResponseModel;
import com.sata.izonovel.Retrofit.APIService;
import com.sata.izonovel.adpter.DaftarNovelAdapter;
import com.sata.izonovel.adpter.FavoritNovelAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritActivity extends AppCompatActivity {
    EditText fav;
    private RecyclerView recyclerView;

    private FavoritNovelAdapter favNovelAdapter;
    private EditText etCari;
    private Button btnSearch;

    private ProgressDialog progressDialog;
    private List<FavoriteNovelResponse.Documents> documents;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("Novel Favorit");

        recyclerView = findViewById(R.id.reycle_favorite_novel);
        etCari = findViewById(R.id.cari);
        btnSearch = findViewById(R.id.btnSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        onLoadData();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etCari.getText().toString().trim();
                if (!query.isEmpty()) {
                    performSearch(query);
                } else {
                    onLoadData();
                }
            }
        });
    }

    private void onLoadData(){
        FavoriteNovelRequest favoriteNovelRequest=new FavoriteNovelRequest();
        favoriteNovelRequest.setCollection("novel");
        favoriteNovelRequest.setDatabase("izonovel");
        favoriteNovelRequest.setDataSource("Cluster0");





        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        APIService.endpoint().FavNovel(favoriteNovelRequest).enqueue(new Callback<FavoriteNovelResponse>() {
            @Override
            public void onResponse(Call<FavoriteNovelResponse> call, Response<FavoriteNovelResponse> response) {
                List<FavoriteNovelResponse.Documents> documents = response.body().getDocuments();

                favNovelAdapter = new FavoritNovelAdapter(FavoritActivity.this, documents);
                recyclerView.setAdapter(favNovelAdapter);


                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FavoriteNovelResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void performSearch(String query) {
        List<FavoriteNovelResponse.Documents> filter = new ArrayList<>();
        for (FavoriteNovelResponse.Documents document : documents) {
            String judulNovel = document.getJudul().toLowerCase();
            if (judulNovel.contains(query.toLowerCase())) {
                filter.add(document);
            }
        }
//        FavoritNovelAdapter.setFilteredList(filter);
    }

}