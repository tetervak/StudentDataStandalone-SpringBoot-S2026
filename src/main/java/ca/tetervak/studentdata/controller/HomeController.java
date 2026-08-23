package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.data.entities.AppUser;
import ca.tetervak.studentdata.service.AppUserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class HomeController {

    private final AppUserDataService userDataService;

    public HomeController(AppUserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping(value={"/", "/index"})
    public String index(
            Authentication authentication,
            Model model
    ){
        log.trace("index() is called");
        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            log.debug("index: username = [{}]", username);
            AppUser user = userDataService.requireUser(username);
            model.addAttribute("user", user);
        }
        return "home/index";
    }

    @GetMapping( "/login")
    public String login(
            @RequestParam(defaultValue = "false") boolean error,
            Model model
    ){
        log.trace("login() is called");
        model.addAttribute("error", error);
        if(error){
            log.debug("Login error");
        }
        return "home/login";
    }
}
