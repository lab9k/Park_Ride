package controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import repository.Repository;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalDefaultExceptionHandlerController {

    @Autowired
    private Environment environment;
    @Autowired
    private Repository repository;
    
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        if(e instanceof MissingServletRequestParameterException) {
            ModelAndView mav = new ModelAndView("home");
            mav.addObject("environment", environment);
            mav.addObject("city", repository.getCity());
            mav.addObject("parkAndRides", repository.getParkAndRides());
            mav.addObject("errorMessage", "Gelieve vertrek- en aankomstlocatie correct in te vullen");
            return mav;
        }

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e);
        return mav;
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView defaultNotFoundHandler(NoHandlerFoundException e) {
        ModelAndView mav = new ModelAndView("notFound");
        mav.addObject("exception", e);
        return mav;
    }
    
}
