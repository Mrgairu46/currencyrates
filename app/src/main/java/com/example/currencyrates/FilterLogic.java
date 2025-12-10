package com.example.currencyrates;

import java.util.ArrayList;
import java.util.List;

public class FilterLogic {

    public List<String> filter(List<CurrencyRate> allRates, String query) {
        List<String> result = new ArrayList<>();

        if (allRates == null) return result;
        if (query == null) query = "";

        query = query.trim().toUpperCase();

        for (CurrencyRate rate : allRates) {
            if (rate.getCode().contains(query)) {
                result.add(rate.getCode() + " - " + rate.getRate());
            }
        }

        return result;
    }
}
