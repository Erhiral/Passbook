package com.example.passbook.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.passbook.R;
import com.example.passbook.databinding.ActivityDetailsBinding;
import com.example.passbook.retofit.Product;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            bindData();
        }

        binding.imgProduct.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());
            updateFavIcon();
        });

        binding.imgBack.setOnClickListener(v -> finishWithResult());
    }

    private void bindData() {
        binding.txtTitle.setText(product.getTitle());
        binding.txtDesc.setText(product.getDescription());
      //  binding.txtPrice.setText("$" + product.getPrice());

        Glide.with(this)
                .load(product.getImage())
                .into(binding.imgProduct);

        updateFavIcon();
    }

    private void updateFavIcon() {
        binding.imgFav.setImageResource(
                product.isFavorite()
                        ? R.drawable.favorite
                        : R.drawable.un_favourite
        );
    }

    private void finishWithResult() {
        Intent data = new Intent();
        data.putExtra("product", product);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWithResult();
    }
}

