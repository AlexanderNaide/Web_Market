package ru.gb.web_market.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.web_market.entities.Role;
import ru.gb.web_market.entities.User;
import ru.gb.web_market.repositories.RoleRepository;
import ru.gb.web_market.repositories.UserRepository;

import java.security.AuthProvider;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

//    private final BCryptPasswordEncoder passwordEncoder;

/*    @PostConstruct
    public void init(){
        User user = new User();
        user.setUsername("user");
        user.setPassword("pass");
        user.getRoles().add(new Role("ROLE_USER"));
        save(user);
    }*/



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

/*    public void save(User user){
        System.out.println("Работа энкодера" + encoder.encode(user.getPassword()));
//        user.setPassword(encoder.encode(user.getPassword()));
//        userRepository.save(user);
    }*/

//    @EventListener(ApplicationReadyEvent.class)
//    public void generateDataOnStartup(){
//
//        Role userRole = new Role();
//        userRole.setRole("ROLE_USER");
//
//        Role adminRole = new Role();
//        adminRole.setRole("ROLE_ADMIN");
//
//        roleRepository.save(userRole);
//        roleRepository.save(adminRole);
//
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword("$2a$12$RUXIUbQUAjZBlV3XUFDKM.O7l1RzNebLpEHh3BOBkPNMbjQKr5Xxa");
////        user.setPassword("pass");
//        user.setEmail("us@us.ru");
//
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword("$2a$12$RUXIUbQUAjZBlV3XUFDKM.O7l1RzNebLpEHh3BOBkPNMbjQKr5Xxa");
//        admin.setEmail("ad@ad.ru");
//
//        user.setRoles(List.of(userRole));
//        admin.setRoles(List.of(userRole, adminRole));
//
////        System.out.println("Работа энкодера: " + passwordEncoder.encode(user.getPassword()));
//
//        userRepository.save(user);
//        userRepository.save(admin);
//    }
}
