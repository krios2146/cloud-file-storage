package com.file.storage.controller;

import com.file.storage.dto.request.UserRegistrationRequest;
import com.file.storage.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest());
        return "registration";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest) {
        userService.register(userRegistrationRequest);
        return "redirect:/login";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleRequestValidation(MethodArgumentNotValidException e) {
        var modelAndView = new ModelAndView("registration");

        String message = e.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse(e.getBody().getDetail());

        modelAndView.addObject("error", message);
        modelAndView.setStatus(e.getStatusCode());
        modelAndView.addObject("userRegistrationRequest", new UserRegistrationRequest());

        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException e) {
        var modelAndView = new ModelAndView("registration");

        modelAndView.addObject("error", "User with such credentials already exists. Try to change username or/and email");
        modelAndView.setStatus(HttpStatus.CONFLICT);
        modelAndView.addObject("userRegistrationRequest", new UserRegistrationRequest());

        return modelAndView;
    }
}
