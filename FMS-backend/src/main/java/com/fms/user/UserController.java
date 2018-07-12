package com.fms.user;

import com.fms.Trade.Trade;
import com.fms.Wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@EnableAutoConfiguration
@RestController
@RequestMapping({"/api"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = {"/user"})
    public ResponseEntity<?> create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping(path = {"/authenticate"})
    public ResponseEntity<?> authenticate(@RequestBody User user) throws Exception {
        return userService.authenticate(user);
    }

    @GetMapping(path = {"/user={id}"})
    public Optional<User> findById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PutMapping(path = {"/user"})
    public User update(@RequestBody User user) throws Exception {
        return userService.update(user);
    }

    @DeleteMapping(path = {"/user={id}"})
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return userService.delete(id);

    }

    @DeleteMapping(path = {"/users"})
    public void deleteAll() {
        userService.deleteAll();
        return;
    }

    @GetMapping(path = {"/users"})
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(path = {"/user={id}/trades"})
    public List<Trade> trades(@PathVariable("id") int id) {
        return userService.trades(id);
    }

    @GetMapping(path = {"/user={id}/wallet"})
    public Wallet wallet(@PathVariable("id") int id) {
        return userService.wallet(id);
    }


}
