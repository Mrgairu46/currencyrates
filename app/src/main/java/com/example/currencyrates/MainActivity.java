package com.example.currencyrates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataLoader.DataLoaderListener {

    private static final String RATES_URL = "https://open.er-api.com/v6/latest/USD";

    private EditText edtFilter;
    private ListView listRates;

    private ArrayAdapter<String> adapter;
    private List<CurrencyRate> allRates = new ArrayList<>();
    private List<String> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFilter = findViewById(R.id.edtFilter);
        listRates = findViewById(R.id.listRates);

        adapter = new ArrayAdapter<>(this,
                R.layout.item_rate,
                R.id.txtRate,
                displayList);

        listRates.setAdapter(adapter);

        new DataLoader(this).execute(RATES_URL);

        edtFilter.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRates(s.toString());
            }
        });
    }

    private void filterRates(String query) {
        query = query.trim().toUpperCase();

        displayList.clear();

        for (CurrencyRate rate : allRates) {
            if (rate.getCode().contains(query)) {
                displayList.add(rate.getCode() + " - " + rate.getRate());
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataLoaded(List<CurrencyRate> rates) {
        if (rates == null) {
            Toast.makeText(this, "Failed to load rates", Toast.LENGTH_LONG).show();
            return;
        }

        allRates.clear();
        allRates.addAll(rates);

        displayList.clear();
        for (CurrencyRate rate : rates) {
            displayList.add(rate.getCode() + " - " + rate.getRate());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
