package org.example;

import org.example.repository.TrainingApplicationRepository;
import org.example.repository.TrainingApplicationRepositoryImpl;
import org.example.repository.UserRepository;
import org.example.repository.UserRepositoryImpl;
import org.example.service.StatisticsService;
import org.example.service.TrainingApplicationService;
import org.example.service.UserService;
import org.example.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {

        UserRepository userRepository =
                new UserRepositoryImpl();

        TrainingApplicationRepository applicationRepository =
                new TrainingApplicationRepositoryImpl();

        UserService userService =
                new UserService(userRepository);

        TrainingApplicationService applicationService =
                new TrainingApplicationService(
                        applicationRepository,
                        userRepository
                );

        StatisticsService statisticsService =
                new StatisticsService(
                        applicationRepository
                );

        ConsoleUI consoleUI =
                new ConsoleUI(
                        userService,
                        applicationService,
                        statisticsService
                );

        consoleUI.start();
    }
}