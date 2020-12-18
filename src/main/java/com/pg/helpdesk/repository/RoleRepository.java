package com.pg.helpdesk.repository;

import com.pg.helpdesk.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findAllByNameLike(String name, Pageable pageable);

    Role findOne(Long id);

    Role findOneByName(String name);
}

