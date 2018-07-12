package com.fms.user;

import com.fms.Trade.Trade;
import com.fms.Wallet.Wallet;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<?> create(User user);

    ResponseEntity<String> delete(int id);

    void deleteAll();

    List<User> findAll();

    Optional<User> findById(int id);

    User update(User user) throws Exception;

    ResponseEntity<?> authenticate(User user) throws Exception;

    Wallet wallet(int id);

    List<Trade> trades(int id);


}
