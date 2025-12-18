package com.example.passbook.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.passbook.retofit.Product;
import com.example.passbook.retofit.ProductRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private final ProductRepository repository;
    private final MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private final List<Product> allProducts = new ArrayList<>();

    public ProductViewModel() {
        repository = new ProductRepository();
        loadProducts();
    }

    /**
     * Fetch products from repository
     */
    private void loadProducts() {
        repository.getProducts().observeForever(products -> {
            if (products != null) {
                allProducts.clear();
                allProducts.addAll(products);
                productsLiveData.setValue(new ArrayList<>(allProducts));
            }
        });
    }

    /**
     * Return LiveData to observe in Activity
     */
    public LiveData<List<Product>> getProducts() {
        return productsLiveData;
    }

    /**
     * Search filter
     */
    public void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            filteredList.addAll(allProducts);
        } else {
            for (Product p : allProducts) {
                if (p.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(p);
                }
            }
        }
        productsLiveData.setValue(filteredList);
    }

    /**
     * Toggle favorite/unfavorite
     */
    public void toggleFavorite(Product product) {
        product.setFavorite(!product.isFavorite());

        // Update filtered list
        List<Product> currentList = productsLiveData.getValue();
        if (currentList != null) {
            int index = currentList.indexOf(product);
            if (index != -1) {
                currentList.set(index, product);
                productsLiveData.setValue(currentList);
            }
        }

        // Update master list
        int masterIndex = allProducts.indexOf(product);
        if (masterIndex != -1) {
            allProducts.set(masterIndex, product);
        }
    }
}



//public class ProductViewModel  extends ViewModel {
//
//    private ProductRepository repository = new ProductRepository();
//
//    public LiveData<List<Product>> getProducts() {
//        return repository.getProducts();
//    }
//
//}
