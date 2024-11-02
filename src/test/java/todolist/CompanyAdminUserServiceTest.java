package todolist;

import com.backendtest.Service.CompanyAdminUserService;
import com.backendtest.model.CompanyAdminUser;
import com.backendtest.model.StandardUser;
import com.backendtest.model.Task;
import com.backendtest.model.TaskType;
import com.backendtest.repository.CompanyAdminUserRepository;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyAdminUserServiceTest {

    private CompanyAdminUserRepository companyAdminUserRepository;
    private StandardUserRepository standardUserRepository;
    private TaskRepository taskRepository;
    private CompanyAdminUserService service;

    @BeforeEach
    public void setUp() {
        companyAdminUserRepository = Mockito.mock(CompanyAdminUserRepository.class);
        standardUserRepository = Mockito.mock(StandardUserRepository.class);
        taskRepository = Mockito.mock(TaskRepository.class);
        service = new CompanyAdminUserService(companyAdminUserRepository, standardUserRepository, taskRepository);
    }

    @Test
    public void whenUserIdExists_getCompanyAdminUser_shouldReturnUser() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));

        Optional<CompanyAdminUser> result = service.getCompanyAdminUser(0);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void whenUserIdDoesNotExist_getCompanyAdminUser_shouldThrowException() {
        when(companyAdminUserRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getCompanyAdminUser(1));
        assertEquals("User with id 1 could not be found", exception.getMessage());
    }

    @Test
    public void whenUserIdExists_deleteCompanyAdminUser_shouldDeleteUser() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);

        Mockito.when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));

        service.deleteCompanyAdminUser(0);
        Mockito.verify(companyAdminUserRepository).deleteById(0);
    }

    @Test
    public void whenUserIdDoesNotExist_deleteCompanyAdminUser_shouldThrowException() {
        when(companyAdminUserRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteCompanyAdminUser(1));
        assertEquals("User with id " + 1 + " could not be found", exception.getMessage());
    }

    @Test
    public void whenUserIdExists_getCompanyAdminTask_shouldReturnTask() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        Optional<Task> result = service.getCompanyAdminTask(0, 0);

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    public void whenUserDoesNotOwnTask_getCompanyAdminTask_shouldThrowException() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 2);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        when(taskRepository.findById(2)).thenReturn(Optional.of(task));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getCompanyAdminTask(0, 2));
        assertEquals("Task with id 2 is not created by user with id 0", exception.getMessage());
    }

    @Test
    public void whenUserIdExists_getAllCompanyAdminTasks_shouldReturnEmptyList() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        when(companyAdminUserRepository.findById(1)).thenReturn(Optional.of(user));

        assertEquals(Collections.emptyList(), service.getAllCompanyAdminTasks(1));
    }

    @Test
    public void whenUserIdHasAccess_getStandardUserTasks_shouldReturnTasks() {
        StandardUser standardUser = new StandardUser("Standard User", TaskType.STANDARD_USER_TASK);
        CompanyAdminUser adminUser = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        when(standardUserRepository.findById(1)).thenReturn(Optional.of(standardUser));
        when(companyAdminUserRepository.findById(1)).thenReturn(Optional.of(adminUser));

        assertEquals(standardUser.getTasks(), service.getStandardUserTasks(1, 1));
    }

    @Test
    public void whenUserDoesNotHaveAccess_getStandardUserTasks_shouldThrowException() {
        StandardUser standardUser = new StandardUser("Standard User", TaskType.STANDARD_USER_TASK);
        CompanyAdminUser adminUser = new CompanyAdminUser("Admin", TaskType.STANDARD_USER_TASK);

        when(standardUserRepository.findById(0)).thenReturn(Optional.of(standardUser));
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(adminUser));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getStandardUserTasks(0, 0);
        });

        assertEquals("You are not allowed to access this task", exception.getMessage());
    }

    @Test
    public void whenUserIdHasAccess_deleteTask_shouldDeleteTask() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        Mockito.when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        service.deleteTask(0, 0);
        Mockito.verify(taskRepository).deleteById(0);
    }

    @Test
    public void whenUserDoesNotOwnTask_deleteTask_shouldThrowException() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 2);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteTask(0, 0));
        assertEquals("Task with id 0 is not created by user with id 0", exception.getMessage());
    }

    @Test
    public void whenUserIdHasAccess_updateTask_shouldUpdateTask() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        Task updatedTask = new Task("Updated Task", "Updated Description", null, null, 0);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        service.updateTask(0, 0, updatedTask);
        Mockito.verify(taskRepository).save(task);
        assertEquals("Updated Task", task.getName());
        assertEquals("Updated Description", task.getDescription());
    }

    @Test
    public void whenUserDoesNotOwnTask_updateTask_shouldThrowException() {
        CompanyAdminUser user = new CompanyAdminUser("Admin", TaskType.COMPANY_ADMIN_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 2);
        Task updatedTask = new Task("Updated Task", "Updated Description", null, null, 1);
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(user));
        when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.updateTask(0, 0, updatedTask));
        assertEquals("Task with id 0 is not created by user with id 0", exception.getMessage());
    }
}
