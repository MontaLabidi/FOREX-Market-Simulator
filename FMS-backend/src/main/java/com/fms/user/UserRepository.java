package com.fms.user;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//import org.springframework.data.repository.Repository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    public List<User> searchuser(@Param("username") String username, @Param("password") String password);


}
