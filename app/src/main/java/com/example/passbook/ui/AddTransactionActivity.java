package com.example.passbook.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.passbook.R;
import com.example.passbook.database.TransactionEntity;
import com.example.passbook.viewmodel.TransactionViewModel;

public class AddTransactionActivity extends AppCompatActivity {

    private Spinner spinnerType;
    private EditText etAmount;
    private Button btnSave;
    private EditText etNote;

    private TransactionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        spinnerType = findViewById(R.id.spinnerType);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.transaction_type,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            if (validate()) {
                String type = spinnerType.getSelectedItem().toString().toUpperCase();
                double amount = Double.parseDouble(etAmount.getText().toString());
                String title = etNote.getText().toString();

                TransactionEntity entity = new TransactionEntity(
                        title,
                        amount,
                        type,
                        System.currentTimeMillis()
                );

                viewModel.insert(entity);
                finish();
            }
        });
    }

    private boolean validate() {
        if (spinnerType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Credit or Debit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etAmount.getText().toString().trim().isEmpty()) {
            etAmount.setError("Amount required");
            return false;
        }
        return true;
    }
}
