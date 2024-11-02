package com.backendtest.Service;

import com.backendtest.model.StandardUser;
import com.backendtest.model.Task;
import com.backendtest.model.TaskType;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StandardUserService {
    private final StandardUserRepository repository;
    private final TaskRepository taskRepository;

    public Optional<StandardUser> getStandardUser(int id) {
        return repository.findById(id);
    }

    public void deleteStandardUser(int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with id " + id + " could not be found");
        }

    }

    public Optional<Task> getTask(int userId, int taskId) {
        StandardUser user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));

        if (user.getId() == task.getCreatedBy()) {
            if (user.getTaskType() == TaskType.STANDARD_USER_TASK) {
                return Optional.of(task);
            } else {
                throw new IllegalArgumentException("User with id " + userId + " is not allowed to access this task");
            }
        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " is not created by user with id " + userId);
        }
    }


    public List<Task> getAllTasks(int userId) {
        StandardUser user = repository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        if (user.getTaskType() == TaskType.STANDARD_USER_TASK) {
            return user.getTasks();
        } else {
            throw new IllegalArgumentException("You are not allowed to access this task");
        }
    }

    public void deleteTask(int userId, int taskId) {
        StandardUser user = repository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        Task task = taskRepository.findById(taskId).
                orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));
        if (user.getId() == task.getCreatedBy()) {
            taskRepository.deleteById(taskId);
        }
    }


    public void updateTask(int userId, int taskId, Task updatedTask) {
        StandardUser user = repository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        Task task = taskRepository.findById(taskId).
                orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));
        if (user.getId() == task.getCreatedBy()) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setDeadline(updatedTask.getDeadline());
            taskRepository.save(task);

        }


    }
}
