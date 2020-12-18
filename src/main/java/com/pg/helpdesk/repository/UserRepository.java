package com.pg.helpdesk.repository;

import com.pg.helpdesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("from User u where u.firstName = :query or u.lastName = :query or u.username = :query")
    Page<User> search(@Param("query") String query, Pageable pageable);
}

