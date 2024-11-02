package com.backendtest.controller;

import com.backendtest.Service.StandardUserService;
import com.backendtest.model.StandardUser;
import com.backendtest.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/standard-users")
@AllArgsConstructor
public class StandardUserController {
    private final StandardUserService standardUserService;

    @GetMapping("/{id}")
    public ResponseEntity<StandardUser> getStandardUser(@PathVariable int id) {
        return ResponseEntity.ok(standardUserService.getStandardUser(id).orElse(null));

    }


    @DeleteMapping("/{id}")
    public void deleteStandardUser(@PathVariable int id) {
        standardUserService.deleteStandardUser(id);
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable int userId, @PathVariable int taskId) {
        return ResponseEntity.ok(standardUserService.getTask(userId, taskId).orElse(null));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable int userId) {
        return ResponseEntity.ok(standardUserService.getAllTasks(userId));
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public void deleteTask(@PathVariable int userId, @PathVariable int taskId) {
        standardUserService.deleteTask(userId, taskId);
    }

    @PutMapping("/{userId}/tasks/{taskId}")
    public void updateTask(@PathVariable int userId, @PathVariable int taskId, @RequestBody Task updatedTask) {
        standardUserService.updateTask(userId, taskId, updatedTask);
    }
}
