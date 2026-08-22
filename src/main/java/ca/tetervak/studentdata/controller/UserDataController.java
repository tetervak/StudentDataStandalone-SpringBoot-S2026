package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.data.entities.AppUser;
import ca.tetervak.studentdata.model.AddUserForm;
import ca.tetervak.studentdata.model.EditUserForm;
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
                log.trace("insertUser: Entered username already exists.");
            }
        }
        if (bindingResult.hasErrors()) {
            log.trace("insertUser: Input validation errors.");
            model.addAttribute("user", user);
            return "users/add-user";
        }
        AppUser savedUser = userDataService.addUser(user);
        log.trace("insertUser: User added");
        return "redirect:/users/user-added/" + savedUser.getUsername();
    }

    @GetMapping("/user-added/{username}")
    public String userAdded(@PathVariable String username, Model model) {
        log.trace("userAdded() is called.");
        log.trace("userAdded: username = {}", username);
        AppUser user = userDataService.requireUser(username);
        model.addAttribute("user", user);
        return "users/user-added";
    }

    @GetMapping("/edit-user")
    public String editUser(@RequestParam String username, Model model) {
        log.trace("editUser() is called.");
        log.trace("editUser: username = {}", username);
        EditUserForm form = userDataService.getEditUserFormByUsername(username);
        log.debug("editUser: form = {}", form);
        model.addAttribute("user", form);
        return "users/edit-user";
    }

    @PostMapping("/update-user")
    public String updateUser(
            @ModelAttribute("user") EditUserForm user,
            BindingResult result,
            Model model
    ) {
        log.trace("updateUser() is called.");
        log.debug("updateUser: user = {}", user);
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "users/edit-user";
        }
        userDataService.updateUser(user);
        return "redirect:/users/list-users";
    }

    // an admin clicks "Delete" link in "list-users.html",
    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam String username, Model model) {
        log.trace("deleteUser() is called.");
        log.debug("deleteUser: username = {}", username);
        AppUser user = userDataService.requireUser(username);
        model.addAttribute("user", user);
        return "users/delete-user";
    }

    // an admin clicks on "Delete User" button
    // the form submits the data to "RemoveUser"
    @PostMapping("/remove-user")
    public String removeUser(@RequestParam String username) {
        log.trace("removeUser() is called.");
        log.debug("removeUser: username = {}", username);
        userDataService.removeUser(username);
        return "redirect:/users/list-users";
    }
}
