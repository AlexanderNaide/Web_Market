package ru.gb.web_market.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.user.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
