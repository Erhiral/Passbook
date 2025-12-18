package com.example.passbook.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passbook.database.TransactionEntity;
import com.example.passbook.databinding.ItemTransactionBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

 class TransactionAdapter extends ListAdapter<TransactionEntity, TransactionAdapter.TransactionViewHolder> {

     TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TransactionEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TransactionEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
                    return oldItem.amount == newItem.amount &&
                            oldItem.type.equals(newItem.type) &&
                            oldItem.timestamp == newItem.timestamp;
                }
            };

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionEntity transaction = getItem(position);
        holder.bind(transaction);
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final ItemTransactionBinding binding;

        public TransactionViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TransactionEntity transaction) {
            binding.tvType.setText(transaction.type);
            binding.tvAmount.setText((transaction.type.equals("CREDIT") ? "+ " : "- ") + "₹" + transaction.amount);

            // Color coding
            int color = transaction.type.equals("CREDIT") ?
                    Color.parseColor("#4CAF50") : // green for credit
                    Color.parseColor("#F44336"); // red for debit
            binding.tvAmount.setTextColor(color);
            binding.tvType.setTextColor(color);

            // Format timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            String date = sdf.format(new Date(transaction.timestamp));
            binding.tvDate.setText(date);
        }
    }
}
