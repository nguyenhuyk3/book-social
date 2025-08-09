package com.bs.identity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bs.identity.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
