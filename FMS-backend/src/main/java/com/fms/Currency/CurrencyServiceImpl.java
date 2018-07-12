package com.fms.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency create(Currency Currency) {
        return currencyRepository.save(Currency);
    }

    @Override
    public ResponseEntity<String> botCurrencyInfo(int id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Currency Not Found !"));

        return new ResponseEntity<>("{\"open\": " + currency.getDayOpen() + ","
                + "\"low\": " + currency.getDayLow() + ","
                + "\"high\": " + currency.getDayHigh() + ","
                + "\"close\": " + currency.getDayClose() + " }", HttpStatus.OK);
    }

    @Override
    public Currency delete(int id) {

        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency Not Found !"));
        currencyRepository.deleteById(id);
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        currencyRepository.findAll().forEach(currencies::add);
        return currencies;
    }

    @Override
    public void deleteAll() {
        currencyRepository.deleteAll();
        return;
    }

    @Override
    public Optional<Currency> findById(int id) {
        return currencyRepository.findById(id);

    }


}