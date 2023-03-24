package br.com.ada.challenge.controllers;

import br.com.ada.challenge.security.CurrentUser;
import br.com.ada.challenge.security.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/start");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@CurrentUser final CustomUser customUser) {
        customUser.resetMatch();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
}
