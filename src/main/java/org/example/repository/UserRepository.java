package org.example.repository;

import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    User update(User user);

    void deleteById(int id);

    Optional<User> findByEmail(String email);

    List<User> searchByName(String name);
}