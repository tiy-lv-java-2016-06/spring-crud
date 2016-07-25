package com.theironyard;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nigel on 7/21/16.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findFirstByName(String name);
}

