package com.theironyard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nigel on 7/21/16.
 */
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByUser(User user);
    List<Car> findByUserNot(User user);
}
