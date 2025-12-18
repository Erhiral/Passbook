package com.example.passbook.fragment;

import android.os.Bundle;
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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TransactionViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity())
                .get(TransactionViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.transaction_type,
                android.R.layout.simple_spinner_item
        );
        binding.spinnerType.setAdapter(adapter);

        binding.btnSave.setOnClickListener(v -> saveTransaction());

        return binding.getRoot();
    }

    private void saveTransaction() {
        if (binding.spinnerType.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "Select type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.etAmount.getText().toString().trim().isEmpty()) {
            binding.etAmount.setError("Enter amount");
            return;
        }

        TransactionEntity entity = new TransactionEntity(
                "title",
                Double.parseDouble(binding.etAmount.getText().toString()),
                binding.spinnerType.getSelectedItem().toString().toUpperCase(),
                System.currentTimeMillis()
        );

        viewModel.insert(entity);

        binding.etAmount.setText("");
        binding.spinnerType.setSelection(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

