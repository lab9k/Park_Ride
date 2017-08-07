package controller;

import domain.Location;
import domain.ParkAndRide;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import repository.Repository;

@Controller
@RequestMapping(value= {"/overview"})
public class OverviewController {

    private final Repository repository;
    private final Environment environment;

    @Autowired
    public OverviewController(Repository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        Location city = repository.getCity();
        List<ParkAndRide> parkAndRides = repository.getParkAndRides();
        ModelAndView mav = new ModelAndView("overview");
        mav.addObject("environment", environment);
        mav.addObject("city", city);
        mav.addObject("parkAndRides", parkAndRides);
        return mav;
    }

}
