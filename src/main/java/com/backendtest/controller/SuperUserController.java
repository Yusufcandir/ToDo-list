package com.backendtest.controller;

import com.backendtest.Service.SuperUserService;
import com.backendtest.model.CompanyAdminUser;
import com.backendtest.model.StandardUser;
import com.backendtest.model.SuperUser;
import com.backendtest.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/super-users")
@AllArgsConstructor
public class SuperUserController {
    private final SuperUserService superUserService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SuperUser>> getSuperUser(@PathVariable int id) {
        return ResponseEntity.ok(superUserService.getSuperUser(id));
    }

    @GetMapping("/{superUserId}/standard-users")
    public ResponseEntity<List<StandardUser>> getStandardUsers(@PathVariable int superUserId) {
        return ResponseEntity.ok(superUserService.getStandardUsers(superUserId));
    }

    @GetMapping("/{superUserId}/company-admin-users")
    public ResponseEntity<List<CompanyAdminUser>> getCompanyAdminUsers(@PathVariable int superUserId) {
        return ResponseEntity.ok(superUserService.getCompanyAdminUsers(superUserId));
    }

    @DeleteMapping("/{id}")
    public void deleteSuperUser(@PathVariable int id) {
        superUserService.deleteSuperUser(id);
    }

    @GetMapping("/{superUserId}/company-admin-users/{companyAdminId}/tasks")
    public ResponseEntity<List<Task>> getCompanyAdminTasks(@PathVariable int superUserId, @PathVariable int companyAdminId) {
        return ResponseEntity.ok(superUserService.getCompanyAdminTasks(superUserId, companyAdminId));
    }

    @GetMapping("/{superUserId}/standard-users/{standardUserId}/tasks")
    public ResponseEntity<List<Task>> getStandardUserTasks(@PathVariable int superUserId, @PathVariable int standardUserId) {
        return ResponseEntity.ok(superUserService.getStandardUserTasks(superUserId, standardUserId));
    }
}
