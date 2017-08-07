package controller;

import api.IRouteApi;
import domain.Location;
import domain.ParkAndRide;
import domain.Route;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import repository.Repository;

@Controller
@RequestMapping(value= {"/", "/route"})
public class HomeController {


    private final Repository repository;
    private final IRouteApi routeApi;
    private final Environment environment;

    @Autowired
    public HomeController(Repository repository, Environment environment, IRouteApi routeApi) {
        this.repository = repository;
        this.environment = environment;
        this.routeApi = routeApi;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        Location city = repository.getCity();
        List<ParkAndRide> parkAndRides = repository.getParkAndRides();

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("environment", environment);
        mav.addObject("city", city);
        mav.addObject("parkAndRides", parkAndRides);
        return mav;
    }

    @RequestMapping(value = {"/route"}, method = RequestMethod.GET)
    public DeferredResult<ModelAndView> calculateRoute(HttpServletRequest request,
               @RequestParam(value = "start_id", required = true) String start_id,
               @RequestParam(value = "end_id", required = true) String end_id,
               @RequestParam(value = "start_name", required = true) String start_name,
               @RequestParam(value = "end_name", required = true) String end_name,
               @RequestParam(value = "hour-input", required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime hour_input,
               @RequestParam(value = "date-input", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") DateTime date_input,
               @RequestParam(value = "selected-pr", required = false) Integer selectedPR) {


        DeferredResult<ModelAndView> dr = new DeferredResult<>();

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("environment", environment);
        mav.addObject("city", repository.getCity());
        mav.addObject("parkAndRides", repository.getParkAndRides());

        DateTime departureTime;
        if(date_input != null && hour_input != null){
            departureTime = date_input
                                .hourOfDay().setCopy(hour_input.getHourOfDay())
                                .minuteOfHour().setCopy(hour_input.getMinuteOfHour());
            // Allow a departuretime of 15 minutes in the past, but no more
            if(departureTime.plusMinutes(5).isBeforeNow()) {
                System.out.println(departureTime.toString("dd/MM/yyyy HH:mm"));
                mav.addObject("errorMessage", "Vetrekdatum en uur mag niet in het verleden liggen");
                dr.setResult(mav);
                return dr;
            }
        }

        else {
            departureTime = DateTime.now();
        }

        mav.addObject("startLocationName", HtmlUtils.htmlEscape(start_name));
        mav.addObject("endLocationName", HtmlUtils.htmlEscape(end_name));

        if(selectedPR == null || selectedPR == 0) {
            routeApi.getFastestThreeRoutes(start_id, end_id, departureTime, (routes) -> {
                mav.addObject("routes", routes);
                dr.setResult(mav);
            });
        }

        else {
            routeApi.getRouteVia(start_id, end_id, departureTime, repository.getParkAndRide(selectedPR), 1, (route) -> {
                List<Route> routes = new ArrayList<>();
                routes.add(route);
                mav.addObject("routes", routes);
                dr.setResult(mav);
            });
        }

        return dr;
    }
}