package todolist;


import com.backendtest.Service.SuperUserService;
import com.backendtest.model.*;
import com.backendtest.repository.CompanyAdminUserRepository;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.SuperUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SuperUserServiceTest {

    private SuperUserRepository superUserRepository;
    private CompanyAdminUserRepository companyAdminUserRepository;
    private StandardUserRepository standardUserRepository;
    private SuperUserService service;

    @BeforeEach
    public void setUp() {
        superUserRepository = Mockito.mock(SuperUserRepository.class);
        companyAdminUserRepository = Mockito.mock(CompanyAdminUserRepository.class);
        standardUserRepository = Mockito.mock(StandardUserRepository.class);
        service = new SuperUserService(superUserRepository, companyAdminUserRepository, standardUserRepository);
    }

    @Test
    public void whenSuperUserIdExists_getSuperUser_shouldReturnUser() {
        SuperUser superUser = new SuperUser("Super User");
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));

        Optional<SuperUser> result = service.getSuperUser(0);

        assertTrue(result.isPresent());
        assertEquals(superUser, result.get());
    }

    @Test
    public void whenSuperUserIdDoesNotExist_getSuperUser_shouldReturnEmpty() {
        when(superUserRepository.findById(1)).thenReturn(Optional.empty());
        Optional<SuperUser> result = service.getSuperUser(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void whenSuperUserIdExists_deleteSuperUser_shouldDeleteUser() {
        service.deleteSuperUser(0);
        verify(superUserRepository).deleteById(0);
    }

    @Test
    public void whenSuperUserIdExists_getStandardUsers_shouldReturnStandardUsers() {
        SuperUser superUser = new SuperUser("Super User");
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));
        when(standardUserRepository.findAll()).thenReturn(Collections.singletonList(new StandardUser("Standard User", TaskType.STANDARD_USER_TASK)));

        List<StandardUser> result = service.getStandardUsers(0);

        assertEquals(1, result.size());
    }

    @Test
    public void whenSuperUserIdDoesNotExist_getStandardUsers_shouldThrowException() {
        when(superUserRepository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getStandardUsers(0));
        assertEquals("User with id 0 could not be found", exception.getMessage());
    }

    @Test
    public void whenSuperUserIdExists_getCompanyAdminUsers_shouldReturnCompanyAdminUsers() {
        SuperUser superUser = new SuperUser("Super User");
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));
        when(companyAdminUserRepository.findAll()).thenReturn(Collections.singletonList(new CompanyAdminUser("Company Admin",  TaskType.COMPANY_ADMIN_TASK)));

        List<CompanyAdminUser> result = service.getCompanyAdminUsers(0);

        assertEquals(1, result.size());
    }

    @Test
    public void whenSuperUserIdDoesNotExist_getCompanyAdminUsers_shouldThrowException() {
        when(superUserRepository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getCompanyAdminUsers(0));
        assertEquals("User with id 0 could not be found", exception.getMessage());
    }

    @Test
    public void whenSuperUserIdAndCompanyAdminIdExist_getCompanyAdminTasks_shouldReturnTasks() {
        SuperUser superUser = new SuperUser("Super User");
        CompanyAdminUser companyAdminUser = new CompanyAdminUser("Company Admin", TaskType.COMPANY_ADMIN_TASK);
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));
        when(companyAdminUserRepository.findById(0)).thenReturn(Optional.of(companyAdminUser));
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        companyAdminUser.setTasks(Collections.singletonList(task));

        List<Task> result = service.getCompanyAdminTasks(0, 0);

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void whenSuperUserIdDoesNotExist_getCompanyAdminTasks_shouldThrowException() {
        when(superUserRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getCompanyAdminTasks(1, 2));
        assertEquals("User with id 1 could not be found", exception.getMessage());
    }

    @Test
    public void whenCompanyAdminUserDoesNotExist_getCompanyAdminTasks_shouldThrowException() {
        SuperUser superUser = new SuperUser("Super User");
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));
        when(companyAdminUserRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getCompanyAdminTasks(0, 2));
        assertEquals("Company Admin User with id 2 could not be found", exception.getMessage());
    }

    @Test
    public void whenSuperUserIdAndStandardUserIdExist_getStandardUserTasks_shouldReturnTasks() {
        SuperUser superUser = new SuperUser("Super User");
        StandardUser standardUser = new StandardUser("Standard User", TaskType.STANDARD_USER_TASK);
        when(superUserRepository.findById(0)).thenReturn(Optional.of(superUser));
        when(standardUserRepository.findById(0)).thenReturn(Optional.of(standardUser));
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        standardUser.setTasks(Collections.singletonList(task));

        List<Task> result = service.getStandardUserTasks(0, 0);

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void whenSuperUserIdDoesNotExist_getStandardUserTasks_shouldThrowException() {
        when(superUserRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getStandardUserTasks(1, 3));
        assertEquals("User with id 1 could not be found", exception.getMessage());
    }

    @Test
    public void whenStandardUserIdDoesNotExist_getStandardUserTasks_shouldThrowException() {
        SuperUser superUser = new SuperUser("Super User");
        when(superUserRepository.findById(1)).thenReturn(Optional.of(superUser));
        when(standardUserRepository.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getStandardUserTasks(1, 3));
        assertEquals("Standard User with id 3 could not be found", exception.getMessage());
    }

    @Test
    public void whenSuperUserIdAndStandardUserIdExist_getStandardUserTasks_shouldThrowExceptionForUnauthorizedAccess() {
        SuperUser superUser = new SuperUser("Super User");
        StandardUser standardUser = new StandardUser("Standard User", TaskType.COMPANY_ADMIN_TASK);
        when(superUserRepository.findById(1)).thenReturn(Optional.of(superUser));
        when(standardUserRepository.findById(3)).thenReturn(Optional.of(standardUser));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getStandardUserTasks(1, 3));
        assertEquals("You are not allowed to access this task", exception.getMessage());
    }
}

