package com.fms.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

@EnableAutoConfiguration
@RestController
@RequestMapping({"/api"})
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @GetMapping(path = {"/currencies"})
    public List<Currency> findAll() {
        return currencyService.findAll();
    }

    @PostMapping(path = {"/currency"})
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);

    }

    @GetMapping(path = {"/currency={id}"})
    public Currency findById(@PathVariable("id") int id) throws Exception {
        return currencyService.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency Not Found !"));

    }

    @GetMapping(path = {"/getcurrency={id}"})
    public ResponseEntity<String> botCurrencyInfo(@PathVariable("id") int id) throws Exception {
        return currencyService.botCurrencyInfo(id);
    }

    @DeleteMapping(path = {"/currency={id}"})
    public Currency delete(@PathVariable("id") int id) {
        return currencyService.delete(id);
    }

    @DeleteMapping(path = {"/currencies"})
    public void deleteAll() {
        currencyService.deleteAll();
        return;
    }


}
