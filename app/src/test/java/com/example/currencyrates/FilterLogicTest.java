package com.example.currencyrates;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterLogicTest {

    @Test
    public void testFilter_returnsMatchingCodes() {
        List<CurrencyRate> list = new ArrayList<>();
        list.add(new CurrencyRate("USD", 1.0));
        list.add(new CurrencyRate("EUR", 0.9));
        list.add(new CurrencyRate("AUD", 1.5));

        FilterLogic logic = new FilterLogic();
        List<String> result = logic.filter(list, "US");

        assertEquals(1, result.size());
        assertEquals("USD - 1.0", result.get(0));
    }

    @Test
    public void testFilter_emptyQuery_returnsAll() {
        List<CurrencyRate> list = new ArrayList<>();
        list.add(new CurrencyRate("USD", 1.0));
        list.add(new CurrencyRate("EUR", 0.9));

        FilterLogic logic = new FilterLogic();
        List<String> result = logic.filter(list, "");

        assertEquals(2, result.size());
    }

    @Test
    public void testFilter_noMatch_returnsEmptyList() {
        List<CurrencyRate> list = new ArrayList<>();
        list.add(new CurrencyRate("USD", 1.0));

        FilterLogic logic = new FilterLogic();
        List<String> result = logic.filter(list, "XYZ");

        assertEquals(0, result.size());
    }
}
