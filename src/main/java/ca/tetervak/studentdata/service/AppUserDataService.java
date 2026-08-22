package ca.tetervak.studentdata.service;

import ca.tetervak.studentdata.data.entities.AppRole;
import ca.tetervak.studentdata.data.entities.AppUser;
import ca.tetervak.studentdata.data.repositories.AppRoleDataRepository;
import ca.tetervak.studentdata.data.repositories.AppUserDataRepository;
import ca.tetervak.studentdata.errors.UserNotFoundException;
import ca.tetervak.studentdata.model.AddUserForm;
import ca.tetervak.studentdata.model.EditUserForm;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppUserDataService implements UserDetailsService {

    private final AppUserDataRepository userDataRepository;
    private final AppRoleDataRepository roleDataRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserDataService(
            AppUserDataRepository userDataRepository,
            AppRoleDataRepository roleDataRepository,
            PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.roleDataRepository = roleDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        AppUser user = userDataRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                //.disabled(!user.isEnabled())
                .build();
    }

    public List<AppUser> getAllUsers() {
        return userDataRepository.findAllByOrderByUsername();
    }

    public boolean userExists(String userName) {
        return userDataRepository.findByUsername(userName).isPresent();
    }

    public AppUser requireUser(String username) {
        return userDataRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private AppUser requireUser(Integer id) {
        return userDataRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private AppRole requireRole(String roleName) {
        return roleDataRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalStateException(
                        "Required role is missing from the database: " + roleName));
    }

    public AppUser addUser(@NonNull AddUserForm form) {
        AppUser user = new AppUser();
        user.setUsername(form.getUsername());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        if(form.getUserAdmin()){
            AppRole userAdminRole = requireRole("USER_ADMIN");
            user.getRoles().add(userAdminRole);
        }
        if(form.getDataAdmin()){
            AppRole dataAdminRole = requireRole("DATA_ADMIN");
            user.getRoles().add(dataAdminRole);
        }
        if(form.getDataUser()){
            AppRole dataUserRole = requireRole("DATA_USER");
            user.getRoles().add(dataUserRole);
        }
        return userDataRepository.save(user);
    }

    public void updateUser(EditUserForm form){
        AppUser user = userDataRepository.findById(form.getId()).orElseThrow();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        AppRole userAdminRole = requireRole("USER_ADMIN");
        if(form.getUserAdmin()){
            user.getRoles().add(userAdminRole);
        } else {
            user.getRoles().remove(userAdminRole);
        }
        AppRole dataAdminRole = requireRole("DATA_ADMIN");
        if(form.getDataAdmin()){
            user.getRoles().add(dataAdminRole);
        } else {
            user.getRoles().remove(dataAdminRole);
        }
        AppRole dataUserRole = requireRole("DATA_USER");
        if(form.getDataUser()){
            user.getRoles().add(dataUserRole);
        } else {
            user.getRoles().remove(dataUserRole);
        }
        userDataRepository.save(user);
    }

    public EditUserForm getEditUserFormByUsername(String username) {
        AppUser user = userDataRepository.findByUsername(username).orElseThrow();
        EditUserForm form = new EditUserForm();
        form.setId(user.getId());
        form.setFirstName(user.getFirstName());
        form.setLastName(user.getLastName());
        AppRole userAdminRole = requireRole("USER_ADMIN");
        AppRole dataAdminRole = requireRole("DATA_ADMIN");
        AppRole dataUserRole = requireRole("DATA_USER");
        form.setUserAdmin(user.getRoles().contains(userAdminRole));
        form.setDataAdmin(user.getRoles().contains(dataAdminRole));
        form.setDataUser(user.getRoles().contains(dataUserRole));
        return form;
    }

    public void removeUser(String userName) {
        userDataRepository.deleteByUsername(userName);
    }

    public void updatePassword(String username, String password) {
        AppUser user = requireUser(username);
        user.setPasswordHash(passwordEncoder.encode(password));
    }

    public boolean checkPassword(String username, String password) {
        return userDataRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .orElse(false);
    }

    public String getPassword(String username) {
        return requireUser(username).getPasswordHash();
    }
}
