package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.UserE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Repository;

import static com.edu.ulab.app.util.SetterHelper.setIfNotNull;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Storage<UserE> storage;

    public UserRepositoryImpl(Storage<UserE> storage) {
        this.storage = storage;
    }


    @Override
    public void save(UserE user) {
        storage.save(user);
    }

    @Override
    public Optional<UserE> getById(Long id) {
        return storage.get(id);
    }

    @Override
    public UserE update(UserE user) {
        Long id = user.getId();
        UserE oldUser = storage.get(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " was not found"));
        setIfNotNull(user.getFullName(), oldUser::setFullName);
        setIfNotNull(user.getTitle(), oldUser::setTitle);
        setIfNotNull(user.getAge(), oldUser::setAge);
        return oldUser;

    }

    @Override
    public void deleteById(Long id) {
        storage.delete(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.existsById(id);
    }
}
