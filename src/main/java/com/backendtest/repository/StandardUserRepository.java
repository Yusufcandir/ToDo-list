package com.backendtest.repository;

import com.backendtest.model.StandardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardUserRepository extends JpaRepository<StandardUser, Integer> {
}
