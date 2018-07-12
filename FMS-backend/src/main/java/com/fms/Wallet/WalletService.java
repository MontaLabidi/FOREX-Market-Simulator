package com.fms.Wallet;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface WalletService {

    ResponseEntity<?> create(Wallet user);

    ResponseEntity<String> delete(int id);

    void deleteAll();

    List<Wallet> findAll();

    Optional<Wallet> findById(int id);

    Wallet update(Wallet user) throws Exception;


}
