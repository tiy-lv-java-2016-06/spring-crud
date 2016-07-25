package com.theironyard.controllers;

import com.theironyard.entities.Bottle;
import com.theironyard.entities.User;
import com.theironyard.services.BottleRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.annotation.PostConstruct;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by vasantia on 7/21/16.
 */

@Controller
public class BottleTrackerController {

    @Autowired
    BottleRepository bottles;

    @Autowired
    UserRepository users;

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        User user = userSessionGrabber(session);
        if(user != null){
            model.addAttribute("user", user);
        }

        List<Bottle> userBottleList = bottles.findByUser(user);
        model.addAttribute("userBottleList", userBottleList);

        List<Bottle> allBottles = bottles.findAll();
        model.addAttribute("allBottles", allBottles);

        return "home";
    }

    /**
     * Add new bottle
     * @param session
     * @param bottleName
     * @param bottleProducer
     * @param bottleRegion
     * @param bottleVintage
     * @param bottleVariety
     * @param bottleABV
     * @return
     * @throws Exception
     */
    @RequestMapping(path="/create-bottle", method = RequestMethod.POST)
    public String createBottle(HttpSession session, String bottleName, String bottleProducer, String bottleRegion,
                               int bottleVintage, String bottleVariety, float bottleABV, Integer id) throws Exception {

        User user = userSessionGrabber(session);
        if(user == null){
            throw new Exception("Need to login!");
        }
        Bottle bottle = new Bottle(bottleName, bottleProducer, bottleRegion, bottleVintage, bottleVariety,
                bottleABV, user);

        bottles.save(bottle);

        return "redirect:/";
    }

    /**
     * Delete bottle
     * @param session
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/delete-bottle", method = RequestMethod.POST)
    public String deleteBottle(HttpSession session, Integer id) throws Exception {

        User user = userSessionGrabber(session);
        if(user == null){
            throw new Exception("Need to login!");
        }
        Bottle foundBottle = bottles.findById(id);
        bottles.delete(foundBottle);

        return "redirect:/";
    }

    /**
     * Pull in bottle id and go to edit bottle page
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(path = "/edit-bottle", method = RequestMethod.GET)
    public String editBottle(Model model, Integer id){

        if(id != null){
            model.addAttribute("bottle", bottles.getOne(id));
        }
        return "bottles";
    }

    /**
     * Update bottle info
     * @param session
     * @param id
     * @param bottleName
     * @param bottleProducer
     * @param bottleRegion
     * @param bottleVintage
     * @param bottleVariety
     * @param bottleABV
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/edit-bottle", method = RequestMethod.POST)
    public String editBottle(HttpSession session, Integer id, String bottleName, String bottleProducer,
                             String bottleRegion, int bottleVintage, String bottleVariety, float bottleABV) throws Exception {
        User user = userSessionGrabber(session);
        if(user == null){
            throw new Exception("Need to login!");
        }
        Bottle foundBottle = bottles.findById(id);
        foundBottle.setName(bottleName);
        foundBottle.setProducer(bottleProducer);
        foundBottle.setRegion(bottleRegion);
        foundBottle.setVintage(bottleVintage);
        foundBottle.setVariety(bottleVariety);
        foundBottle.setAbv(bottleABV);

        bottles.save(foundBottle);

        return "redirect:/";
    }

    /**
     * Log user in
     * @param session
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws
            Exception {
        User user = users.findFirstByName(userName);
        if(user == null){
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if(!PasswordStorage.verifyPassword(password, user.getPassword())){
            throw new Exception("Please enter correct password.");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    /**
     * Log user out of session
     * @param session
     * @return
     */
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    /**
     * Find user and put into the session
     * @param session
     * @return
     */
    public User userSessionGrabber(HttpSession session){
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        return user;
    }


}
