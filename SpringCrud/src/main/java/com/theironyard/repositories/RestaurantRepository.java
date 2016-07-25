package com.theironyard.repositories;

import com.theironyard.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EddyJ on 7/21/16.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findFirstById(int id);
}
