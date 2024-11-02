package com.backendtest.Service;

import com.backendtest.model.CompanyAdminUser;
import com.backendtest.model.StandardUser;
import com.backendtest.model.Task;
import com.backendtest.model.TaskType;
import com.backendtest.repository.CompanyAdminUserRepository;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyAdminUserService {
    private final CompanyAdminUserRepository companyAdminUserRepository;
    private final StandardUserRepository standardUserRepository;
    private final TaskRepository taskRepository;

    public Optional<CompanyAdminUser> getCompanyAdminUser(int id) {
        return Optional.ofNullable(companyAdminUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " could not be found")));
    }

    public void deleteCompanyAdminUser(int id) {
        if (companyAdminUserRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " could not be found");
        } else {
            companyAdminUserRepository.deleteById(id);
        }

    }

    public Optional<Task> getCompanyAdminTask(int id, int taskId) {
        CompanyAdminUser user = companyAdminUserRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with id " + id + " could not be found"));
        Task task = taskRepository.findById(taskId).
                orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));
        if (user.getId() == task.getCreatedBy()) {
            if (user.getTaskType() == TaskType.COMPANY_ADMIN_TASK) {
                return Optional.of(task);
            } else {
                throw new IllegalArgumentException("You are not allowed to access this task");
            }

        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " is not created by user with id " + id);
        }
    }

    public List<Task> getAllCompanyAdminTasks(int id) {
        CompanyAdminUser user = companyAdminUserRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with id " + id + " could not be found"));
        if (user.getTaskType() == TaskType.COMPANY_ADMIN_TASK) {
            return user.getTasks();
        } else {
            throw new IllegalArgumentException("You are not allowed to access this task");
        }
    }

    public List<Task> getStandardUserTasks(int companyAdminId, int standardUserId) {
        StandardUser user = standardUserRepository.findById(standardUserId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + standardUserId + " could not be found"));
        CompanyAdminUser companyAdminUser = companyAdminUserRepository.findById(companyAdminId)
                .orElseThrow(() -> new IllegalArgumentException("Company Admin User with id " + companyAdminId + " could not be found"));


        if (companyAdminUser.getTaskType() == TaskType.COMPANY_ADMIN_TASK) {
            return user.getTasks();
        } else {
            throw new IllegalArgumentException("You are not allowed to access this task");
        }
    }


    public void deleteTask(int userId, int taskId) {
        CompanyAdminUser user = companyAdminUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));


        if (user.getId() != task.getCreatedBy()) {
            throw new IllegalArgumentException("Task with id " + taskId + " is not created by user with id " + userId);
        }


        taskRepository.deleteById(taskId);
    }


    public void updateTask(int userId, int taskId, Task updatedTask) {
        CompanyAdminUser user = companyAdminUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " could not be found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " could not be found"));


        if (user.getId() != task.getCreatedBy()) {
            throw new IllegalArgumentException("Task with id " + taskId + " is not created by user with id " + userId);
        }


        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setDeadline(updatedTask.getDeadline());
        taskRepository.save(task);
    }


}