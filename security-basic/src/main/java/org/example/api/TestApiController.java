package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.domain.MyServiceRole;
import org.example.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApiController {
    private final UserRepository userRepository;

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        return "admin-page";
    }

    @GetMapping("/protected")
    public String showProtectedPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "protected-page";
    }

    @PreAuthorize("@authorizationFilter.hasPermission('" + MyServiceRole.ADMIN + "')")
    public String testMethodSecurity(Model model) {
        return "protected-page";
    }
}
