package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Nigel on 7/21/16.
 */

@Controller
public class AwwCrudController {
    public static final String SESSION_USERNAME = "userName";
    public static final String SESSION_PASSWORD = "password";

    @Autowired
    CarRepository cars;

    @Autowired
    UserRepository users;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        String userName = (String) session.getAttribute(SESSION_USERNAME);
        String password = (String) session.getAttribute(SESSION_PASSWORD);
        User user = users.findFirstByName(userName);
        List<Car> othersCars = cars.findAll();


        if (user != null && PasswordStorage.verifyPassword(password, user.getPassword())) {
            List<Car> userCars = cars.findByUser(user);
            othersCars = cars.findByUserNot(user);

            model.addAttribute("userCars", userCars);
        }

        model.addAttribute("othersCars", othersCars);
        model.addAttribute("userName", userName);

        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws PasswordStorage.CannotPerformOperationException {

        User user = users.findFirstByName(userName);

        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        }

        session.setAttribute(SESSION_USERNAME, userName);
        session.setAttribute(SESSION_PASSWORD, password);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(path = "/create-car", method = RequestMethod.POST)
    public String logout(HttpSession session, String year, String make, String model, String drivetrain){

        String name = (String) session.getAttribute(SESSION_USERNAME);
        User user = users.findFirstByName(name);
        int carYear = Integer.valueOf(year);
        Car newCar = new Car(drivetrain, make, model, carYear, user);

        cars.save(newCar);

        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String edit(Model model, String carId ){

        int id = Integer.valueOf(carId);
        Car editCar = cars.findOne(id);

        model.addAttribute("editCar", editCar);

        return "edit";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String edit2(String drivetrain, String make, String model, String year, String carId ){

        int id = Integer.valueOf(carId);
        Car editedCar = cars.findOne(id);

        editedCar.setDrivetrain(drivetrain);
        editedCar.setMake(make);
        editedCar.setModel(model);
        editedCar.setYear(Integer.valueOf(year));

        cars.save(editedCar);

        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(String carId){

        int id = Integer.valueOf(carId);

        cars.delete(cars.findOne(id));

        return "redirect:/";
    }
}
