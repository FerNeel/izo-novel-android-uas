package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailNovelActivity extends AppCompatActivity {

        private TextView tvJudul;
        private TextView tvSinopsis;
        private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_novel);

        //digunakan untuk mengambil data kiriman dari activity intent, sebelum nya
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String judul = intent.getStringExtra("judul");
        String sinopsis = intent.getStringExtra("sinopsis");
        int gambarId = getIntent().getIntExtra("gambar", 0);

        setTitle("Detail Novel - "+judul);

        tvJudul = findViewById(R.id.tvJudulNovel);
        tvSinopsis = findViewById(R.id.tvSinopsis);
        imageView = findViewById(R.id.image_poster);

        tvJudul.setText(judul);
        tvSinopsis.setText(sinopsis);
        imageView.setImageResource(gambarId);

        Log.d("INFO-id",id );
        Log.d("INFO-judul",judul );

    }
}