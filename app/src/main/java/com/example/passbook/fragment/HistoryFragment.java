package com.example.passbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.passbook.databinding.FragmentHistoryBinding;

import com.example.passbook.fragment.TransactionAdapter;
import com.example.passbook.viewmodel.TransactionViewModel;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        TransactionAdapter adapter = new TransactionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        new ViewModelProvider(requireActivity())
                .get(TransactionViewModel.class)
                .getAllTransactions()
                .observe(getViewLifecycleOwner(), adapter::submitList);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

