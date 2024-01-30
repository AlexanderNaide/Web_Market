package ru.gb.web_market.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.user.entities.Role;
import ru.gb.web_market.user.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByRole(String role){
        return roleRepository.findByRole(role);
    }
}
