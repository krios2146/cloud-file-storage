package com.file.storage;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.exception.InvalidUserRegistrationRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidUserRegistrationRequestException.class)
    public ModelAndView handleInvalidRegistrationRequest(InvalidUserRegistrationRequestException e) {
        var model = new ModelAndView("registration");

        model.addObject("error", e.getMessage());
        model.addObject("userRegistrationRequest", new UserRegistrationRequest());
        model.setStatus(HttpStatus.BAD_REQUEST);

        return model;
    }
}
