package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.model.AddUserForm;
import ca.tetervak.studentdata.service.AppUserDataService;
import ca.tetervak.studentdata.passwords.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserDataController {

    private final AppUserDataService userDataService;
    private final PasswordGenerator passwordGenerator;

    public UserDataController(
            AppUserDataService userDataService,
            PasswordGenerator passwordGenerator
    ) {
        this.userDataService = userDataService;
        this.passwordGenerator = passwordGenerator;
    }

    @GetMapping(value={"/","/index"})
    public String index(){
        log.trace("index() is called");
        return "users/users-index";
    }

    // an admin clicks "List Users" link in "index.html",
    @GetMapping("/list-users")
    public String listUsers(Model model) {
        log.trace("listUsers() is called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        model.addAttribute("you", authentication.getName());
        model.addAttribute("users", userDataService.getAllUsers());
        return "users/list-users";
    }

    // an admin clicks "Add User" link in "list-users.html",
    @GetMapping("/add-user")
    public String addUser(Model model) {
        log.trace("addUser() is called");
        AddUserForm user = new AddUserForm();
        user.setPassword(passwordGenerator.randomPassword());
        model.addAttribute("user", user);
        model.addAttribute("random", passwordGenerator.randomPassword());
        return "users/add-user";
    }

    // an admin clicks on "Add User" button in "add-user.html",
    // the form submits the data to "InsertUser"
    @PostMapping("/insert-user")
    public String insertUser(
            @Validated @ModelAttribute("user") AddUserForm user,
            BindingResult bindingResult,
            Model model
    ) {
        log.trace("insertUser() is called");
        log.debug("insertUser: user = {}", user);

        if (!bindingResult.hasFieldErrors("currentPassword")) {
            if(userDataService.userExists(user.getUsername())) {
                bindingResult.rejectValue("username", "username.exists");
                log.trace("Entered username already exists");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "users/add-user";
        }

        userDataService.addUser(user);
        log.trace("User added");

        return "users/user-added";
    }

    // an admin clicks "Delete" link in "list-users.html",
    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam String login, Model model) {
        log.trace("deleteUser() is called");
        log.debug("deleteUser: login = {}", login);
        model.addAttribute("user", login);
        return "users/delete-user";
    }

    // an admin clicks on "Delete User" button in "DeleteUser.jsp",
    // the form submits the data to "RemoveUser"
    @PostMapping("/remove-user")
    public String removeUser(@RequestParam String login) {
//        userDataService.removeRoles(login);
//        userDataService.removeUser(login);
        return "redirect:/users/list-users";
    }
}
