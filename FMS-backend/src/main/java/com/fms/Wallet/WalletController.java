package com.fms.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@EnableAutoConfiguration
@RestController
@RequestMapping({"/api"})
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping(path = {"/wallet"})
    public ResponseEntity<?> create(@RequestBody Wallet wallet) {
        return walletService.create(wallet);
    }

    @GetMapping(path = {"/wallet={id}"})
    public Optional<Wallet> findById(@PathVariable("id") int id) {
        return walletService.findById(id);
    }

    @PutMapping(path = {"/wallet"})
    public Wallet update(@RequestBody Wallet wallet) throws Exception {
        return walletService.update(wallet);
    }

    @DeleteMapping(path = {"/wallet={id}"})
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return walletService.delete(id);

    }

    @DeleteMapping(path = {"/wallets"})
    public void deleteAll() {
        walletService.deleteAll();
        return;
    }

    @GetMapping(path = {"/wallets"})
    public List<Wallet> findAll() {
        return walletService.findAll();
    }

}
