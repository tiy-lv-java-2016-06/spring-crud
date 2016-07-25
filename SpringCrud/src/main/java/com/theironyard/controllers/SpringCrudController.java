package com.theironyard.controllers;

import com.theironyard.entities.Restaurant;
import com.theironyard.entities.User;
import com.theironyard.repositories.RestaurantRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EddyJ on 7/21/16.
 */
@Controller
public class SpringCrudController {

    @Autowired
    UserRepository users;

    @Autowired
    RestaurantRepository restaurants;

    public static final String SESSION_USERNAME = "userName";

    public User getUserFromSession(HttpSession session) {
        String userName = (String) session.getAttribute(SESSION_USERNAME);
        User user = users.findFirstByName(userName);
        return user;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        User user = getUserFromSession(session);
        if(user != null){
            model.addAttribute("user", user);
        }
        List<Restaurant> userRestaurantList = new ArrayList<>();
        List<Restaurant> otherRestaurantList = new ArrayList<>();
        for(Restaurant restaurant : restaurants.findAll()){
            if(restaurant.getUser().equals(user)){
                userRestaurantList.add(restaurant);
            }
            else{
                otherRestaurantList.add(restaurant);
            }
        }
        model.addAttribute("userRestaurantList", userRestaurantList);
        model.addAttribute("otherRestaurantList", otherRestaurantList);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
        User user = users.findFirstByName(userName);
        if(user == null){
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())){
            return "home";
        }
        session.setAttribute(SESSION_USERNAME, userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create-restaurant", method = RequestMethod.POST)
    public String createRestaurant(HttpSession session, String restaurantName, String menuType, String restaurantAddress, int restaurantRating){
        User user = getUserFromSession(session);
        if(user == null){
            return "/";
        }
        Restaurant restaurant = new Restaurant(restaurantName, menuType, restaurantAddress, restaurantRating, user);
        restaurants.save(restaurant);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String edit(Model model, int id, HttpServletRequest request){
        String idString = request.getParameter("id");
        id = Integer.valueOf(idString);
        Restaurant restaurant = restaurants.findOne(id);
        model.addAttribute("editRestaurant", restaurant);
        return "edit";
    }

    @RequestMapping(path = "/edit-restaurant", method = RequestMethod.POST)
    public String editRestaurant(String restaurantName, String menuType, String restaurantAddress, int restaurantRating, int id){
        Restaurant restaurant = restaurants.findOne(id);
        restaurant.setName(restaurantName);
        restaurant.setMenuType(menuType);
        restaurant.setAddress(restaurantAddress);
        restaurant.setRating(restaurantRating);
        restaurants.save(restaurant);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(int id){
        Restaurant restaurant = restaurants.findOne(id);
        restaurants.delete(restaurant);
        return "redirect:/";
    }

}
