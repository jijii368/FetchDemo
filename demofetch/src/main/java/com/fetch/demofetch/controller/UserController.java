package com.fetch.demofetch.controller;

import com.fetch.demofetch.model.User;
import com.fetch.demofetch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

   /* @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createdUser(@RequestBody User user){
        Map<String,String> response = new HashMap<>();

        response.put("message","Created successfully");
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }*/

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@ModelAttribute User user) {
        Map<String, String> response = new HashMap<>();

        service.save(user);

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            String fileName = StringUtils.cleanPath(user.getFile().getOriginalFilename());
            String uploadDir = "C:/Users/Acer/Downloads/demofetch/src/main/resources/static/upload";

            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try {
                    Files.copy(user.getFile().getInputStream(), uploadPath.resolve(fileName));
                    user.setPhoto(fileName);
                    service.save(user);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save photo");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to create upload directory");
            }
        }

        response.put("message", "Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



  /*  @GetMapping("/users")
    public ResponseEntity<List<User>> userList(){
        List<User> users = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }*/

    @GetMapping("/users")
    public ResponseEntity<List<User>> userList(){
        List<User> users = service.findAll();
        users.forEach(user ->{
            if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                user.setPhoto("/upload/" + user.getPhoto());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletedUser(@PathVariable("id") int id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatedUser(@RequestBody User user){
        service.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> userList(@RequestParam(required = false)Integer id,@RequestParam(required = false) String name) {
        List<User> users = service.findAllByIdAndName(id,name);
        users.forEach(user -> {
            if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                user.setPhoto("/upload/" + user.getPhoto());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
