package org.example.ui;

import org.example.exception.BusinessException;
import org.example.exception.DatabaseException;
import org.example.exception.EntityNotFoundException;
import org.example.model.ApplicationStatus;
import org.example.model.EducationType;
import org.example.model.TrainingApplication;
import org.example.model.User;
import org.example.service.StatisticsService;
import org.example.service.TrainingApplicationService;
import org.example.service.UserService;
import org.example.util.ExcelExporter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;

    private final UserService userService;
    private final TrainingApplicationService applicationService;
    private final StatisticsService statisticsService;

    public ConsoleUI(
            UserService userService,
            TrainingApplicationService applicationService,
            StatisticsService statisticsService
    ) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
        this.applicationService = applicationService;
        this.statisticsService = statisticsService;
    }

    public void start() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("   СИСТЕМА УЧЕБНОГО ЦЕНТРА");
        System.out.println("   Управление заявками на обучение");
        System.out.println("==========================================");

        boolean running = true;

        while (running) {

            printMenu();

            int choice = readInt("Выберите пункт: ");

            try {

                switch (choice) {

                    case 1 -> showAllApplications();

                    case 2 -> findApplication();

                    case 3 -> createApplication();

                    case 4 -> updateApplication();

                    case 5 -> deleteApplication();

                    case 6 -> searchByCourse();

                    case 7 -> searchByUser();

                    case 8 -> filterByStatus();

                    case 9 -> filterByEducationType();

                    case 10 -> sortByDate();

                    case 11 -> sortByCourse();

                    case 12 -> statistics();

                    case 13 -> exportToExcel();

                    case 14 -> showAllUsers();

                    case 0 -> {
                        running = false;
                        System.out.println("Программа завершена.");
                    }

                    default ->
                            System.out.println(
                                    "Такого пункта меню нет."
                            );
                }

            } catch (BusinessException |
                     EntityNotFoundException e) {

                System.out.println();
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println();

            } catch (DatabaseException e) {

                System.out.println();
                System.out.println(
                        "Ошибка базы данных: " +
                                e.getMessage()
                );
                System.out.println();

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Непредвиденная ошибка: " +
                                e.getMessage()
                );
                System.out.println();
            }
        }

        scanner.close();
    }

    private void printMenu() {

        System.out.println();
        System.out.println("--------------- МЕНЮ ----------------");
        System.out.println("1. Показать все заявки");
        System.out.println("2. Найти заявку по ID");
        System.out.println("3. Создать заявку");
        System.out.println("4. Изменить заявку");
        System.out.println("5. Удалить заявку");
        System.out.println("6. Поиск по названию курса");
        System.out.println("7. Поиск по имени пользователя");
        System.out.println("8. Фильтр по статусу");
        System.out.println("9. Фильтр по типу обучения");
        System.out.println("10. Сортировка по дате");
        System.out.println("11. Сортировка по курсу");
        System.out.println("12. Статистика");
        System.out.println("13. Экспорт в Excel");
        System.out.println("14. Показать пользователей");
        System.out.println("0. Выход");
        System.out.println("--------------------------------------");
    }

    private void showAllApplications() {

        List<TrainingApplication> applications =
                applicationService.findAll();

        printApplications(applications);
    }

    private void findApplication() {

        int id = readInt("Введите ID заявки: ");

        TrainingApplication application =
                applicationService.findById(id);

        System.out.println();
        System.out.println(application);
    }

    private void createApplication() {

        System.out.println();
        System.out.println("=== СОЗДАНИЕ ЗАЯВКИ ===");

        String email =
                readString("Email пользователя: ");

        User user = userService.findByEmail(email);

        String courseName =
                readString("Название курса: ");

        LocalDate date =
                readDate("Дата заявки (ГГГГ-ММ-ДД): ");

        ApplicationStatus status =
                ApplicationStatus.NEW;

        EducationType educationType =
                readEducationType();

        String comment =
                readString("Комментарий: ");

        TrainingApplication application =
                new TrainingApplication(
                        user,
                        courseName,
                        date,
                        status,
                        educationType,
                        comment
                );

        TrainingApplication saved =
                applicationService.create(application);

        System.out.println();
        System.out.println(
                "Заявка создана. ID = " +
                        saved.getId()
        );
    }

    private void updateApplication() {

        System.out.println();
        System.out.println("=== ИЗМЕНЕНИЕ ЗАЯВКИ ===");

        int id = readInt("ID заявки: ");

        TrainingApplication existing =
                applicationService.findById(id);

        System.out.println("Текущая заявка:");
        System.out.println(existing);

        showAllUsers();

        int userId =
                readInt("Новый ID пользователя: ");

        String courseName =
                readString("Новое название курса: ");

        LocalDate date =
                readDate("Новая дата (ГГГГ-ММ-ДД): ");

        ApplicationStatus status =
                readStatus();

        EducationType educationType =
                readEducationType();

        String comment =
                readString("Новый комментарий: ");

        User user =
                userService.findById(userId);

        TrainingApplication updated =
                new TrainingApplication(
                        id,
                        user,
                        courseName,
                        date,
                        status,
                        educationType,
                        comment
                );

        applicationService.update(updated);

        System.out.println("Заявка успешно изменена.");
    }

    private void deleteApplication() {

        int id = readInt("ID заявки для удаления: ");

        applicationService.delete(id);

        System.out.println("Заявка удалена.");
    }

    private void searchByCourse() {

        String course =
                readString("Название курса: ");

        List<TrainingApplication> applications =
                applicationService.searchByCourseName(course);

        printApplications(applications);
    }

    private void searchByUser() {

        String name =
                readString("ФИО пользователя: ");

        List<TrainingApplication> applications =
                applicationService.searchByUserName(name);

        printApplications(applications);
    }

    private void filterByStatus() {

        ApplicationStatus status =
                readStatus();

        List<TrainingApplication> applications =
                applicationService.findByStatus(status);

        printApplications(applications);
    }

    private void filterByEducationType() {

        EducationType type =
                readEducationType();

        List<TrainingApplication> applications =
                applicationService.findByEducationType(type);

        printApplications(applications);
    }

    private void sortByDate() {

        System.out.println("1. По возрастанию");
        System.out.println("2. По убыванию");

        int choice =
                readInt("Выберите вариант: ");

        boolean ascending = choice == 1;

        List<TrainingApplication> applications =
                applicationService.sortByDate(ascending);

        printApplications(applications);
    }

    private void sortByCourse() {

        System.out.println("1. По возрастанию");
        System.out.println("2. По убыванию");

        int choice =
                readInt("Выберите вариант: ");

        boolean ascending = choice == 1;

        List<TrainingApplication> applications =
                applicationService.sortByCourseName(
                        ascending
                );

        printApplications(applications);
    }

    private void statistics() {
        statisticsService.printStatistics();
    }

    private void exportToExcel()
            throws IOException {

        List<TrainingApplication> applications =
                applicationService.findAll();

        String fileName =
                "training_applications.xlsx";

        ExcelExporter.export(
                applications,
                fileName
        );

        System.out.println();
        System.out.println(
                "Экспорт завершён."
        );
        System.out.println(
                "Файл: " + fileName
        );
    }

    private void showAllUsers() {

        System.out.println();
        System.out.println("=== ПОЛЬЗОВАТЕЛИ ===");

        List<User> users =
                userService.findAll();

        for (User user : users) {
            System.out.println(user);
        }

        System.out.println();
    }

    private ApplicationStatus readStatus() {

        System.out.println();
        System.out.println("Выберите статус:");

        ApplicationStatus[] statuses =
                ApplicationStatus.values();

        for (int i = 0; i < statuses.length; i++) {

            System.out.println(
                    (i + 1) + ". " + statuses[i]
            );
        }

        int choice =
                readInt("Номер статуса: ");

        if (choice < 1 ||
                choice > statuses.length) {

            throw new BusinessException(
                    "Некорректный номер статуса"
            );
        }

        return statuses[choice - 1];
    }

    private EducationType readEducationType() {

        System.out.println();
        System.out.println("Выберите тип обучения:");

        EducationType[] types =
                EducationType.values();

        for (int i = 0; i < types.length; i++) {

            System.out.println(
                    (i + 1) + ". " + types[i]
            );
        }

        int choice =
                readInt("Номер типа: ");

        if (choice < 1 ||
                choice > types.length) {

            throw new BusinessException(
                    "Некорректный номер типа обучения"
            );
        }

        return types[choice - 1];
    }

    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ошибка: необходимо ввести целое число."
                );
            }
        }
    }

    private String readString(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isBlank()) {
                return input;
            }

            System.out.println(
                    "Поле не может быть пустым."
            );
        }
    }

    private LocalDate readDate(String message) {

        while (true) {

            String input =
                    readString(message);

            try {

                return LocalDate.parse(input);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Неверный формат даты."
                );

                System.out.println(
                        "Используйте ГГГГ-ММ-ДД."
                );
            }
        }
    }

    private void printApplications(
            List<TrainingApplication> applications
    ) {

        System.out.println();

        if (applications.isEmpty()) {

            System.out.println(
                    "Заявок не найдено."
            );

            return;
        }

        System.out.println(
                "Найдено заявок: " +
                        applications.size()
        );

        System.out.println();

        for (TrainingApplication application :
                applications) {

            System.out.println(application);
        }

        System.out.println();
    }
}