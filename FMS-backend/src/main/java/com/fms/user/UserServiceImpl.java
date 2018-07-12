package com.fms.user;

import com.fms.Trade.Trade;
import com.fms.Wallet.Wallet;
import com.fms.Wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> create(User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Wallet wallet = new Wallet();

            wallet.setBalance(1000000.00);
            wallet.setEquity(1000000.00);
            wallet.setFreeMargin(1000000.00);
            wallet.setMargin(0.00);
            wallet.setProfit(0.00);
            wallet.setUser(userRepository.save(user));
            walletRepository.save(wallet);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("{\"message\": \"Username already used\"}", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<?> authenticate(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) == null)
            throw new EntityNotFoundException("Username Not Found");

        User DB_user = userRepository.findByUsername(user.getUsername());
        if (passwordEncoder.matches(user.getPassword(),
                DB_user.getPassword())) {

            return new ResponseEntity<>(DB_user, HttpStatus.OK);
        }

        throw new AccessDeniedException("password is incorrect");

    }


    @Override
    public ResponseEntity<String> delete(int id) {

        userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));
        userRepository.deleteById(id);
        return new ResponseEntity<>("{\"message\": \"User deleted !\"}", HttpStatus.OK);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
        return;
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);

    }

    @Override
    public User update(User user) throws Exception {
        User DB_user = userRepository.findById(user.getId()).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));
        if (passwordEncoder.matches(user.getPassword(),
                DB_user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else
            throw new AccessDeniedException("password is incorrect");
    }

    @Override
    public Wallet wallet(int id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));
        Wallet wallet = walletRepository.findByuser(user).orElseThrow(()
                -> new EntityNotFoundException("Wallet Not Found !"));
        return wallet;
    }

    @Override
    public List<Trade> trades(int id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));

        return user.getTrades();
    }

}