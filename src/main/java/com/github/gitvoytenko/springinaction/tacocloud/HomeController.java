package com.github.gitvoytenko.springinaction.tacocloud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Handling requests with root path '/'
     *
     * @return of the submissions name
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
