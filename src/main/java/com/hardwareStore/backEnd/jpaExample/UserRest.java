package com.hardwareStore.backEnd.jpaExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRest {


    @Autowired
    private UserRepository userRepository;



    @GetMapping("/user")
    public String index(){

        List<UserEntity> listUser = (List<UserEntity>) userRepository.findAll();
        return "Hola mundo";
    }
}
