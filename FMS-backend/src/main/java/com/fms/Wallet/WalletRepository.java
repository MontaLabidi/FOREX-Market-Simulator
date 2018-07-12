package com.fms.Wallet;


import com.fms.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {

    public Optional<Wallet> findByuser(User user);


}
