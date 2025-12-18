package com.example.passbook.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.passbook.R;
import com.example.passbook.databinding.RowProductBinding;
import com.example.passbook.retofit.Product;

import java.time.Instant;
import java.util.List;


import com.example.passbook.viewmodel.ProductViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<Product> list;
    private final ProductViewModel viewModel;

    public ProductAdapter(Context context, List<Product> list, ProductViewModel viewModel) {
        this.context = context;
        this.list = list;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowProductBinding binding = RowProductBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);

        holder.binding.txtTitle.setText(product.getTitle());
        holder.binding.txtPrice.setText("$" + product.getPrice());

        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.imgProduct);

        holder.binding.imgFav.setImageResource(
                product.isFavorite() ? R.drawable.favorite : R.drawable.un_favourite
        );

        holder.binding.imgFav.setOnClickListener(v -> viewModel.toggleFavorite(product));

        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RowProductBinding binding;
        ViewHolder(RowProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}





//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
//
//        private List<Product> list;
//        private Context context;
//
//        public ProductAdapter(Context context, List<Product> list) {
//            this.context = context;
//            this.list = list;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context)
//                    .inflate(R.layout.row_product, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//            Product product = list.get(position);
//
//            holder.title.setText(product.getTitle());
//            holder.price.setText("$" + product.getPrice());
//
//            Instant Glide;
//            Glide.with(context)
//                    .load(product.getImage())
//                    .into(holder.image);
//
//            holder.fav.setImageResource(
//                    product.isFavorite() ? R.drawable.favorite : R.drawable.un_favourite
//            );
//
//            holder.fav.setOnClickListener(v -> {
//                product.setFavorite(!product.isFavorite());
//                notifyItemChanged(position);
//            });
//
//            holder.itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("title", product.getTitle());
//                intent.putExtra("desc", product.getDescription());
//                intent.putExtra("image", product.getImage());
//                context.startActivity(intent);
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        static class ViewHolder extends RecyclerView.ViewHolder {
//
//            TextView title, price;
//            ImageView image, fav;
//
//            ViewHolder(View itemView) {
//                super(itemView);
//                title = itemView.findViewById(R.id.txtTitle);
//                price = itemView.findViewById(R.id.txtPrice);
//                image = itemView.findViewById(R.id.imgProduct);
//                fav = itemView.findViewById(R.id.imgFav);
//            }
//        }
//    }


