package com.backendtest.controller;

import com.backendtest.Service.CompanyAdminUserService;
import com.backendtest.model.CompanyAdminUser;
import com.backendtest.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company-admin-users")
@AllArgsConstructor
public class CompanyAdminUserController {
    private final CompanyAdminUserService companyAdminUserService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyAdminUser> getCompanyAdminUser(@PathVariable int id) {
        return ResponseEntity.ok(companyAdminUserService.getCompanyAdminUser(id).orElse(null));

    }

    @DeleteMapping("/{id}")
    public void deleteCompanyAdminUser(@PathVariable int id) {
        companyAdminUserService.deleteCompanyAdminUser(id);
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Optional<Task>> getCompanyAdminTask(@PathVariable int userId, @PathVariable int taskId) {
        return ResponseEntity.ok(companyAdminUserService.getCompanyAdminTask(userId, taskId));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getAllCompanyAdminTasks(@PathVariable int userId) {
        return ResponseEntity.ok(companyAdminUserService.getAllCompanyAdminTasks(userId));
    }

    @GetMapping("/{companyAdminId}/standard-users/{standardUserId}/tasks")
    public ResponseEntity<List<Task>> getStandardUserTasks(@PathVariable int companyAdminId, @PathVariable int standardUserId) {
        return ResponseEntity.ok(companyAdminUserService.getStandardUserTasks(companyAdminId, standardUserId));
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public void deleteTask(@PathVariable int userId, @PathVariable int taskId) {
        companyAdminUserService.deleteTask(userId, taskId);
    }

    @PutMapping("/{userId}/tasks/{taskId}")
    public void updateTask(@PathVariable int userId, @PathVariable int taskId, @RequestBody Task task) {
        companyAdminUserService.updateTask(userId, taskId, task);
    }
}
