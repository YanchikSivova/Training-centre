package org.example.service;

import org.example.exception.BusinessException;
import org.example.exception.EntityNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {

        validateUser(user);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException(
                    "Пользователь с таким email уже существует"
            );
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Пользователь с ID " + id + " не найден"
                        )
                );
    }

    public User update(User user) {

        findById(user.getId());

        validateUser(user);

        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {

                    if (existingUser.getId() != user.getId()) {
                        throw new BusinessException(
                                "Этот email уже используется другим пользователем"
                        );
                    }
                });

        return userRepository.update(user);
    }

    public void delete(int id) {

        findById(id);

        userRepository.deleteById(id);
    }

    public List<User> searchByName(String name) {

        if (name == null || name.isBlank()) {
            throw new BusinessException(
                    "Имя для поиска не может быть пустым"
            );
        }

        return userRepository.searchByName(name);
    }

    private void validateUser(User user) {

        if (user == null) {
            throw new BusinessException(
                    "Пользователь не может быть null"
            );
        }

        if (user.getFullName() == null ||
                user.getFullName().isBlank()) {

            throw new BusinessException(
                    "ФИО пользователя не может быть пустым"
            );
        }

        if (user.getEmail() == null ||
                user.getEmail().isBlank()) {

            throw new BusinessException(
                    "Email не может быть пустым"
            );
        }

        if (!user.getEmail().contains("@")) {

            throw new BusinessException(
                    "Некорректный email"
            );
        }
    }

    public User findByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new BusinessException(
                    "Email не может быть пустым"
            );
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Пользователь с email " +
                                        email +
                                        " не найден"
                        )
                );
    }
}