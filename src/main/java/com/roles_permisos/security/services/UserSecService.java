package com.roles_permisos.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.roles_permisos.security.models.UserSec;
import com.roles_permisos.security.repository.UserSecRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecService implements IUserSecService {

    private UserSecRepository userSecRepository;

    @Override
    public List findAll() {
        return userSecRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userSecRepository.findById(id);
    }


    @Override
    public void deleteById(Long id) {
        userSecRepository.deleteById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userSecRepository.save(userSec);
    }

    @Override
    public void update(UserSec userSec) {
        save(userSec);
    }

}




