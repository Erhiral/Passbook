package com.example.passbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.passbook.database.TransactionEntity;
import com.example.passbook.databinding.FragmentSummaryBinding;
import com.example.passbook.viewmodel.TransactionViewModel;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);

        TransactionViewModel vm = new ViewModelProvider(requireActivity())
                        .get(TransactionViewModel.class);

        vm.getAllTransactions().observe(getViewLifecycleOwner(), list -> {
            double credit = 0, debit = 0;

            for (TransactionEntity t : list) {
                if (t.type.equals("CREDIT")) credit += t.amount;
                else debit += t.amount;
            }

            binding.tvCredit.setText("Credit: ₹ " + credit);
            binding.tvDebit.setText("Debit: ₹ " + debit);
            binding.tvBalance.setText("Balance: ₹ " + (credit - debit));
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
