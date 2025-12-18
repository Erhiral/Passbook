package com.example.passbook.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.passbook.R;
import com.example.passbook.database.TransactionEntity;
import com.example.passbook.databinding.FragmentHomeBinding;
import com.example.passbook.viewmodel.TransactionViewModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TransactionViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);

        setupTypeDropdown();
        setupSaveButton();

        return binding.getRoot();
    }

    private void setupTypeDropdown() {
        // Get the AutoCompleteTextView
        MaterialAutoCompleteTextView typeAutoComplete = binding.spinnerType;

        // Create and set the adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.transaction_types,  // Make sure this array exists in strings.xml
                android.R.layout.simple_dropdown_item_1line
        );
        typeAutoComplete.setAdapter(adapter);

        // Set a default hint
        typeAutoComplete.setHint("Select transaction type");
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> saveTransaction());
    }

    private void saveTransaction() {
        // Get input values
        String type = binding.spinnerType.getText().toString().trim();
        String amountStr = Objects.requireNonNull(binding.etAmount.getText()).toString().trim();
        String description = Objects.requireNonNull(binding.etDescription.getText()).toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(type)) {
            binding.spinnerType.setError("Please select a transaction type");
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            binding.etAmount.setError("Please enter an amount");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                binding.etAmount.setError("Amount must be greater than zero");
                return;
            }
        } catch (NumberFormatException e) {
            binding.etAmount.setError("Please enter a valid amount");
            return;
        }

        // Create and save transaction
        TransactionEntity transaction = new TransactionEntity(
                type,
                amount,
                type.toUpperCase(),
                System.currentTimeMillis(),
                description  // Using the description parameter we added earlier
        );

        viewModel.insert(transaction);

        // Show success message
        Toast.makeText(requireContext(), "Transaction saved", Toast.LENGTH_SHORT).show();

        // Clear form
        clearForm();
    }

    private void clearForm() {
        binding.spinnerType.setText("");
        binding.etAmount.getText().clear();
        binding.etDescription.getText().clear();
        binding.spinnerType.clearFocus();
        binding.etAmount.clearFocus();
        binding.etDescription.clearFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}