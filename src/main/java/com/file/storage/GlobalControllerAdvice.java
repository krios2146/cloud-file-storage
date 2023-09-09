package com.file.storage;

import com.file.storage.exception.InvalidUserRegistrationRequestException;
import com.file.storage.exception.file.FileRequestException;
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

    @ExceptionHandler(FileRequestException.class)
    public RedirectView handleInvalidFileRequests(FileRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }
}
