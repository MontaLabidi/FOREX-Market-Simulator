package com.fms.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public ResponseEntity<?> create(Wallet wallet) {
        walletRepository.save(wallet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(int id) {

        walletRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Wallet Not Found !"));
        walletRepository.deleteById(id);
        return new ResponseEntity<>("{\"message\": \"Wallet deleted !\"}", HttpStatus.OK);
    }

    @Override
    public List<Wallet> findAll() {
        List<Wallet> wallets = new ArrayList<>();
        walletRepository.findAll().forEach(wallets::add);
        return wallets;
    }

    @Override
    public void deleteAll() {
        walletRepository.deleteAll();
        return;
    }

    @Override
    public Optional<Wallet> findById(int id) {
        return walletRepository.findById(id);

    }

    @Override
    public Wallet update(Wallet wallet) {


        return walletRepository.save(wallet);
    }


}