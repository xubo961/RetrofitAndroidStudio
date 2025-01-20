package com.xubop961.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView principal = findViewById(R.id.principal);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Drinks> call = apiInterface.getDrinksByLicour("Gin");
        call.enqueue(new Callback<Drinks>() {
            @Override
            public void onResponse(Call<Drinks> call, Response<Drinks> response) {
                Log.d("Código", response.code()+"");
                Drinks coctails = response.body();
                String todaLaInformacion = "";
                for (Drinks.Coctail coctail : coctails.drinks) {
                    todaLaInformacion = todaLaInformacion + coctail.coctailName + "\n";
                }
                Log.d("TodaLaInfo", todaLaInformacion);
                principal.setText(todaLaInformacion);
            }

            @Override
            public void onFailure(Call<Drinks> call, Throwable throwable) {
                Log.d("CALL -> mal", throwable.toString());
            }
        });
    }
}