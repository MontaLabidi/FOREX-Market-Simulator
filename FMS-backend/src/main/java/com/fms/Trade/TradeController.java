package com.fms.Trade;

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
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @GetMapping(path = {"/trades"})
    public List<Trade> findAll() {
        return tradeService.findAll();
    }


    @PostMapping(path = {"/user={user_id}/currency={currency_id}/trade"})
    public ResponseEntity<?> create(@RequestBody Trade trade, @PathVariable("user_id") int user_id, @PathVariable("currency_id") int currency_id) {

        return tradeService.create(trade, user_id, currency_id);
    }

    @PostMapping(path = {"/closeTrade={id}"})
    public List<Trade> closeTrade(@PathVariable("id") int id) {
        return tradeService.closeTrade(id);

    }

    @GetMapping(path = {"/trade={id}"})
    public Trade findById(@PathVariable("id") int id) throws Exception {
        return tradeService.findById(id).orElseThrow(() -> new EntityNotFoundException("Trade Not Found !"));

    }

    @DeleteMapping(path = {"/trade={id}"})
    public Trade delete(@PathVariable("id") int id) {
        return tradeService.delete(id);
    }

    @DeleteMapping(path = {"/trades"})
    public void deleteAll() {
        tradeService.deleteAll();
        return;
    }


}
