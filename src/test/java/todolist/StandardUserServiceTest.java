package todolist;


import com.backendtest.Service.StandardUserService;
import com.backendtest.model.StandardUser;
import com.backendtest.model.Task;
import com.backendtest.model.TaskType;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StandardUserServiceTest {
    private StandardUserService service;
    private StandardUserRepository repository;
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(StandardUserRepository.class);
        taskRepository = Mockito.mock(TaskRepository.class);
        service = new StandardUserService(repository, taskRepository);
    }

    @Test
    public void whenUserIdExists_getStandardUser_shouldReturnUser() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));

        Optional<StandardUser> result = service.getStandardUser(0);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void whenUserIdDoesNotExist_getStandardUser_shouldReturnEmpty() {
        Mockito.when(repository.findById(0)).thenReturn(Optional.empty());

        Optional<StandardUser> result = service.getStandardUser(0);
        assertFalse(result.isPresent());
    }

    @Test
    public void whenUserExists_deleteStandardUser_shouldDeleteUser() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));

        service.deleteStandardUser(0);
        Mockito.verify(repository).deleteById(0);
    }

    @Test
    public void whenUserDoesNotExist_deleteStandardUser_shouldThrowException() {
        Mockito.when(repository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteStandardUser(0));
        assertEquals("User with id 1 could not be found", exception.getMessage());
    }

    @Test
    public void whenUserAndTaskExists_getTask_shouldReturnTask() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        Optional<Task> result = service.getTask(0, 0);
        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    public void whenUserDoesNotExist_getTask_shouldThrowException() {
        Mockito.when(repository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getTask(0, 0));
        assertEquals("User with id 0 could not be found", exception.getMessage());
    }

    @Test
    public void whenTaskDoesNotExist_getTask_shouldThrowException() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getTask(0, 0));
        assertEquals("Task with id 0 could not be found", exception.getMessage());
    }

    @Test
    public void whenUserHasNoTasks_getAllTasks_shouldReturnEmptyList() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        user.setTasks(Collections.emptyList());
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));

        List<Task> result = service.getAllTasks(0);
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenUserExists_getAllTasks_shouldReturnListOfTasks() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        user.setTasks(List.of(task));
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));

        List<Task> result = service.getAllTasks(0);
        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void whenUserDoesNotExist_getAllTasks_shouldThrowException() {
        Mockito.when(repository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.getAllTasks(0));
        assertEquals("User with id 0 could not be found", exception.getMessage());
    }

    @Test
    public void whenUserAndTaskExists_deleteTask_shouldDeleteTask() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Task task = new Task("Task 1", "Description 1", null, null, 0);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(0)).thenReturn(Optional.of(task));

        service.deleteTask(0, 0);
        Mockito.verify(taskRepository).deleteById(0);
    }


    @Test
    public void whenUserAndTaskExists_updateTask_shouldUpdateTask() {
        StandardUser user = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        Task existingTask = new Task("Task 1", "Description 1", null, null, 0);
        Task updatedTask = new Task("Updated Task", "Updated Description", null, null, 1);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(0)).thenReturn(Optional.of(existingTask));

        service.updateTask(0, 0, updatedTask);
        Mockito.verify(taskRepository).save(existingTask);
        assertEquals("Updated Task", existingTask.getName());
        assertEquals("Updated Description", existingTask.getDescription());
    }

}
