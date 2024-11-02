package com.backendtest.repository;

import com.backendtest.model.CompanyAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdminUserRepository extends JpaRepository<CompanyAdminUser, Integer> {
}
