package com.gjm.webquizengine.user;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FakeUserRepository implements UserRepository {
    private static int counter = 0;
    private final List<User> users;

    public FakeUserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return users;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return new PageImpl<>(users);
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        List<Integer> ids = StreamSupport.stream(integers.spliterator(), false)
                .collect(Collectors.toList());

        return users.stream()
                .filter(user -> ids.contains(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void deleteById(Integer integer) {
        users.removeIf(user -> user.getId() == integer);
    }

    @Override
    public void delete(User entity) {
        users.remove(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        entities.forEach(users::remove);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public <S extends User> S save(S entity) {
        counter++;
        entity.setId(counter);

        users.add(entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(user -> {
                    counter++;
                    user.setId(counter);

                    users.add(user);
                    return user;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return users.stream()
                .filter(user -> user.getId() == integer)
                .findFirst();
    }

    @Override
    public boolean existsById(Integer integer) {
        return users.stream()
                .anyMatch(user -> user.getId() == integer);
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public void deleteInBatch(Iterable<User> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public User getOne(Integer integer) {
        return findById(integer).orElse(null);
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }
}
