package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Integer id){
        User user = userRepository.findById(id).get();
        return Optional.of(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
