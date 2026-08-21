package ca.tetervak.studentdata.service;

import ca.tetervak.studentdata.data.entities.AppRole;
import ca.tetervak.studentdata.data.entities.AppUser;
import ca.tetervak.studentdata.data.repositories.AppRoleDataRepository;
import ca.tetervak.studentdata.data.repositories.AppUserDataRepository;
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

    public Optional<AppUser> getUserByUsername(String username) {
        return userDataRepository.findByUsername(username);
    }

    public AppUser addUser(@NonNull AddUserForm form) {
        AppUser user = new AppUser();
        user.setUsername(form.getUsername());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        if(form.getUserAdmin()){
            AppRole userAdminRole = roleDataRepository.findByRoleName("USER_ADMIN").orElseThrow();
            user.getRoles().add(userAdminRole);
        }
        if(form.getDataAdmin()){
            AppRole dataAdminRole = roleDataRepository.findByRoleName("DATA_ADMIN").orElseThrow();
            user.getRoles().add(dataAdminRole);
        }
        if(form.getDataUser()){
            AppRole dataUserRole = roleDataRepository.findByRoleName("DATA_USER").orElseThrow();
            user.getRoles().add(dataUserRole);
        }
        return userDataRepository.save(user);
    }

    public void updateUser(EditUserForm form){
        AppUser user = userDataRepository.findById(form.getId()).orElseThrow();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        AppRole userAdminRole = roleDataRepository.findByRoleName("USER_ADMIN").orElseThrow();
        if(form.getUserAdmin()){
            user.getRoles().add(userAdminRole);
        } else {
            user.getRoles().remove(userAdminRole);
        }
        AppRole dataAdminRole = roleDataRepository.findByRoleName("DATA_ADMIN").orElseThrow();
        if(form.getDataAdmin()){
            user.getRoles().add(dataAdminRole);
        } else {
            user.getRoles().remove(dataAdminRole);
        }
        AppRole dataUserRole = roleDataRepository.findByRoleName("DATA_USER").orElseThrow();
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
        AppRole userAdminRole = roleDataRepository.findByRoleName("USER_ADMIN").orElseThrow();
        AppRole dataAdminRole = roleDataRepository.findByRoleName("DATA_ADMIN").orElseThrow();
        AppRole dataUserRole = roleDataRepository.findByRoleName("DATA_USER").orElseThrow();
        form.setUserAdmin(user.getRoles().contains(userAdminRole));
        form.setDataAdmin(user.getRoles().contains(dataAdminRole));
        form.setDataUser(user.getRoles().contains(dataUserRole));
        return form;
    }

    public void removeUser(String userName) {
        userDataRepository.deleteByUsername(userName);
    }

    public void updatePassword(String userName, String password) {
        AppUser user = userDataRepository.findByUsername(userName).orElseThrow();
        user.setPasswordHash(passwordEncoder.encode(password));
        userDataRepository.save(user);
    }

    public boolean checkPassword(String userName, String password) {
        String storedPassword = getPassword(userName);
        if(storedPassword != null) {
            return passwordEncoder.matches(password, storedPassword);
        }else{
            return false;
        }
    }

    public String getPassword(String userName) {
        AppUser user = userDataRepository.findByUsername(userName).orElseThrow();
        return user.getPasswordHash();
    }
}
