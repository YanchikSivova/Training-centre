package org.example.service;

import org.example.model.ApplicationStatus;
import org.example.model.EducationType;
import org.example.model.TrainingApplication;
import org.example.repository.TrainingApplicationRepository;

import java.util.List;

public class StatisticsService {

    private final TrainingApplicationRepository repository;

    public StatisticsService(
            TrainingApplicationRepository repository
    ) {
        this.repository = repository;
    }

    public void printStatistics() {

        List<TrainingApplication> applications =
                repository.findAll();

        long total = applications.size();

        long newApplications = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.NEW)
                .count();

        long underReview = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.UNDER_REVIEW)
                .count();

        long approved = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.APPROVED)
                .count();

        long rejected = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.REJECTED)
                .count();

        long cancelled = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.CANCELLED)
                .count();

        long online = applications.stream()
                .filter(a -> a.getEducationType() == EducationType.ONLINE)
                .count();

        long fullTime = applications.stream()
                .filter(a -> a.getEducationType() == EducationType.FULL_TIME)
                .count();

        long partTime = applications.stream()
                .filter(a -> a.getEducationType() == EducationType.PART_TIME)
                .count();

        System.out.println();
        System.out.println("========== СТАТИСТИКА ==========");
        System.out.println("Всего заявок: " + total);
        System.out.println("Новые: " + newApplications);
        System.out.println("На рассмотрении: " + underReview);
        System.out.println("Одобрено: " + approved);
        System.out.println("Отклонено: " + rejected);
        System.out.println("Отменено: " + cancelled);
        System.out.println("Онлайн: " + online);
        System.out.println("Очное обучение: " + fullTime);
        System.out.println("Заочное обучение: " + partTime);
        System.out.println("================================");
        System.out.println();
    }
}