package com.backendtest;

import com.backendtest.model.*;
import com.backendtest.repository.CompanyAdminUserRepository;
import com.backendtest.repository.StandardUserRepository;
import com.backendtest.repository.SuperUserRepository;
import com.backendtest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class BackendTestApplication implements CommandLineRunner {
    StandardUserRepository standardUserRepository;
    CompanyAdminUserRepository companyAdminUserRepository;
    SuperUserRepository superUserRepository;
    TaskRepository taskRepository;

    List<Task> standardUserTask;
    List<Task> companyAdminTask;


    public static void main(String[] args) {
        SpringApplication.run(BackendTestApplication.class, args);

    }

    @Override
    public void run(String... args) {
        // Initialize the task lists
        standardUserTask = new ArrayList<>();
        companyAdminTask = new ArrayList<>();

        // Create and save a StandardUser
        StandardUser standardUser = new StandardUser("Yusuf", TaskType.STANDARD_USER_TASK);
        standardUser = standardUserRepository.save(standardUser); // Save the user first to get the ID

        // Create a task for the StandardUser using the saved user's ID
        Task task = new Task("Task 1", "Description 1", null, new Date(), standardUser.getId());
        standardUserTask.add(task);
        standardUser.setTasks(standardUserTask);
        taskRepository.save(task);

        System.out.println("Standard User ID: " + standardUser.getId());
        System.out.println("Task ID: " + task.getId());

        //Create and save a StandardUser
        StandardUser standardUser2 = new StandardUser("Yasin", TaskType.STANDARD_USER_TASK);
        standardUser2 = standardUserRepository.save(standardUser2); // Save the user first to get the ID

        // Create a task for the StandardUser using the saved user's ID
        Task task2 = new Task("Task 2", "Description 2", null, new Date(), standardUser2.getId());
        standardUserTask.add(task2);
        standardUser2.setTasks(standardUserTask);
        taskRepository.save(task2);

        System.out.println("Standard User2 ID: " + standardUser2.getId());
        System.out.println("Task3 ID: " + task2.getId());


        // Create and save a CompanyAdminUser
        CompanyAdminUser companyAdminUser = new CompanyAdminUser("Javier", TaskType.COMPANY_ADMIN_TASK);
        companyAdminUser = companyAdminUserRepository.save(companyAdminUser); // Save the user first to get the ID

        // Create a task for the CompanyAdminUser using the saved user's ID
        Task task3 = new Task("Task 3", "Description 3", null, new Date(), companyAdminUser.getId());
        companyAdminTask.add(task2);
        companyAdminUser.setTasks(companyAdminTask);
        taskRepository.save(task2);

        System.out.println("Company Admin User ID: " + companyAdminUser.getId());
        System.out.println("Task3 ID: " + task3.getId());


        //Create and save a CompanyAdminUser
        CompanyAdminUser companyAdminUser2 = new CompanyAdminUser("Tuna", TaskType.COMPANY_ADMIN_TASK);
        companyAdminUser2 = companyAdminUserRepository.save(companyAdminUser2); // Save the user first to get the ID

        // Create a task for the CompanyAdminUser using the saved user's ID
        Task task4 = new Task("Task 4", "Description 4", null, new Date(), companyAdminUser2.getId());
        companyAdminTask.add(task4);
        companyAdminUser2.setTasks(companyAdminTask);
        taskRepository.save(task4);

        System.out.println("Company Admin User2 ID: " + companyAdminUser2.getId());
        System.out.println("Task4 ID: " + task4.getId());


        //Create and save a SuperUser
        SuperUser superUser = new SuperUser("Ada");
        superUser = superUserRepository.save(superUser);

        System.out.println("Super User ID: " + superUser.getId());


    }

}
