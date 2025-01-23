package com.xubop961.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoctailDetailsActivity extends AppCompatActivity {

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coctail_details);

        TextView name = findViewById(R.id.coctailDetailName);
        TextView id = findViewById(R.id.coctailDetailId);
        ImageView image = findViewById(R.id.coctailDetailImage);

        String coctailId = getIntent().getStringExtra("COCTAIL_ID");
        if (coctailId == null || coctailId.isEmpty()) {
            Toast.makeText(this, "ID de cóctel no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        fetchCoctailDetails(coctailId, name, id, image);
    }

    private void fetchCoctailDetails(String coctailId, TextView name, TextView id, ImageView image) {
        Call<Drinks> call = apiInterface.getDrinksByLicour(coctailId); // Ajusta la llamada si necesitas otra endpoint.
        call.enqueue(new Callback<Drinks>() {
            @Override
            public void onResponse(Call<Drinks> call, Response<Drinks> response) {
                if (response.body() != null && !response.body().drinks.isEmpty()) {
                    Drinks.Coctail coctail = response.body().drinks.get(0);
                    name.setText(coctail.coctailName);
                    id.setText("ID: " + coctail.coctailId);
                    Glide.with(CoctailDetailsActivity.this).load(coctail.coctailImageUrl).into(image);
                } else {
                    Toast.makeText(CoctailDetailsActivity.this, "No se encontró información", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Drinks> call, Throwable t) {
                Toast.makeText(CoctailDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }
}
