package com.theironyard.repositories;

import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EddyJ on 7/21/16.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByName(String userName);
}
