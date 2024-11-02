package com.backendtest.repository;

import com.backendtest.model.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUserRepository extends JpaRepository<SuperUser, Integer> {
}
