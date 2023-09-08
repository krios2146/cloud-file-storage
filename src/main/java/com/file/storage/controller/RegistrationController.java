package com.file.storage.controller;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.exception.InvalidRegistrationRequestException;
import com.file.storage.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.file.storage.util.ValidationUtils.getErrorMessage;

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
    public String register(@Valid @ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRegistrationRequestException(getErrorMessage(bindingResult));
        }

        userService.register(userRegistrationRequest);

        return "redirect:/login";
    }
}
