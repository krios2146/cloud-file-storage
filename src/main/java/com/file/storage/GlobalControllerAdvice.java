package com.file.storage;

import com.file.storage.exception.InvalidUserRegistrationRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidUserRegistrationRequestException.class)
    public RedirectView handleInvalidUserRegistrationRequest(InvalidUserRegistrationRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/registration", true);
    }
}
