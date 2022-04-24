package com.example.springmongo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 02/10/21
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	//    @ResponseStatus(HttpStatus.BAD_REQUEST)
	//    @ExceptionHandler(NumberFormatException.class)
	//    public ModelAndView handleNumberFormat(Exception exception){
	//
	//        log.error("Handling Number Format Exception");
	//        log.error(exception.getMessage());
	//
	//        ModelAndView modelAndView = new ModelAndView();
	//
	//        modelAndView.setViewName("400error");
	//        modelAndView.addObject("exception", exception);
	//
	//        return modelAndView;
	//    }
}
