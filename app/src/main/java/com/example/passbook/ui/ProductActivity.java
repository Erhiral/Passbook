package com.example.passbook.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.passbook.R;
import com.example.passbook.databinding.ActivityMainBinding;
import com.example.passbook.retofit.Product;
import com.example.passbook.viewmodel.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();
    private ProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, productList, viewModel);
        binding.recyclerView.setAdapter(adapter);

        // Observe LiveData
        viewModel.getProducts().observe(this, products -> {
            productList.clear();
            if (products != null) {
                productList.addAll(products);
            }
            adapter.notifyDataSetChanged();
        });

        // Search
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.filterProducts(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}

