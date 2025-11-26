package com.example.currencyrates;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    public static List<CurrencyRate> parseRatesFromJson(String json) throws JSONException {
        List<CurrencyRate> list = new ArrayList<>();

        JSONObject root = new JSONObject(json);
        JSONObject ratesObject = root.getJSONObject("rates");

        Iterator<String> keys = ratesObject.keys();
        while (keys.hasNext()) {
            String code = keys.next();
            double rate = ratesObject.getDouble(code);
            list.add(new CurrencyRate(code, rate));
        }

        return list;
    }
}
