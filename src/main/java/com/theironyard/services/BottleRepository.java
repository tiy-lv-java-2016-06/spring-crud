package com.theironyard.services;

import com.theironyard.entities.Bottle;
import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vasantia on 7/21/16.
 */
public interface BottleRepository extends JpaRepository<Bottle, Integer> {
    List<Bottle> findByUser(User user);
    Bottle findById(int id);
}
