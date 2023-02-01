package ru.gb.web_market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
