package com.bs.identity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bs.identity.entities.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
