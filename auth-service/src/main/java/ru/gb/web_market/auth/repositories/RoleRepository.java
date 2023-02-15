package ru.gb.web_market.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.auth.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
