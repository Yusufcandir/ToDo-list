package com.backendtest.Service;

import com.backendtest.model.*;
import com.backendtest.repository.CompanyAdminUserRepository;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.SuperUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SuperUserService {
    private final SuperUserRepository superUserRepository;
    private final CompanyAdminUserRepository companyAdminUserRepository;
    private final StandardUserRepository standardUserRepository;

    public Optional<SuperUser> getSuperUser(int id) {
        return superUserRepository.findById(id);
    }

    public void deleteSuperUser(int id) {
        superUserRepository.deleteById(id);
    }

    public List<StandardUser> getStandardUsers(int superUserId) {
        Optional<SuperUser> user = superUserRepository.findById(superUserId);
        if (user.isPresent()) {
            return standardUserRepository.findAll();
        } else {
            throw new IllegalArgumentException("User with id " + superUserId + " could not be found");
        }
    }

    public List<CompanyAdminUser> getCompanyAdminUsers(int superUserId) {
        Optional<SuperUser> user = superUserRepository.findById(superUserId);
        if (user.isPresent()) {
            return companyAdminUserRepository.findAll();
        } else {
            throw new IllegalArgumentException("User with id " + superUserId + " could not be found");
        }
    }


    public List<Task> getCompanyAdminTasks(int superUserId, int companyAdminId) {
        SuperUser user = superUserRepository.findById(superUserId).
                orElseThrow(() -> new IllegalArgumentException("User with id " + superUserId + " could not be found"));
        CompanyAdminUser companyAdminUser = companyAdminUserRepository.findById(companyAdminId).
                orElseThrow(() -> new IllegalArgumentException("Company Admin User with id " + companyAdminId + " could not be found"));
        if (companyAdminUser.getTaskType() == TaskType.COMPANY_ADMIN_TASK && user != null) {
            return companyAdminUser.getTasks();
        } else {
            throw new IllegalArgumentException("You are not allowed to access this task");
        }

    }


    public List<Task> getStandardUserTasks(int superUserId, int standardUserId) {
        SuperUser user = superUserRepository.findById(superUserId).
                orElseThrow(() -> new IllegalArgumentException("User with id " + superUserId + " could not be found"));
        StandardUser standardUser = standardUserRepository.findById(standardUserId).
                orElseThrow(() -> new IllegalArgumentException("Standard User with id " + standardUserId + " could not be found"));
        if (standardUser.getTaskType() == TaskType.STANDARD_USER_TASK && user != null) {
            return standardUser.getTasks();
        } else {
            throw new IllegalArgumentException("You are not allowed to access this task");
        }
    }
}
