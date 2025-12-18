package com.example.passbook.retofit;

import androidx.lifecycle.MutableLiveData;


import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Product>> getProducts() {

        RetrofitClient.getApiService().getProducts()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        productsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        productsLiveData.setValue(null);
                    }
                });

        return productsLiveData;
    }
}

