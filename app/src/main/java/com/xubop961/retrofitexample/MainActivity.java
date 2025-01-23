package com.xubop961.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CoctailAdapter adapter;
    private ApiInterface apiInterface;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.coctailTodo);
        searchInput = findViewById(R.id.cocktailSearchInput);
        Button searchButton = findViewById(R.id.coctailBotonBuscar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CoctailAdapter(this);
        recyclerView.setAdapter(adapter);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        searchButton.setOnClickListener(v -> {
            String ingredient = searchInput.getText().toString().trim();
            if (!ingredient.isEmpty()) {
                fetchCocktails(ingredient);
            } else {
                Toast.makeText(this, "Introduce un ingrediente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCocktails(String ingredient) {
        Call<Drinks> call = apiInterface.getDrinksByLicour(ingredient);
        call.enqueue(new Callback<Drinks>() {
            @Override
            public void onResponse(Call<Drinks> call, Response<Drinks> response) {
                if (response.body() != null && response.body().drinks != null) {
                    adapter.setCoctails(response.body().drinks);
                } else {
                    Toast.makeText(MainActivity.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Drinks> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }
}
