package com.fetch.demofetch.service;

import com.fetch.demofetch.model.User;
import com.fetch.demofetch.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public void save(User user) {
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional
    public void deleteById(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void updateUser(User user) {

        userRepo.findById(user.getId()).ifPresent(user1 -> {
            user1.setEmail(user.getEmail());
            user1.setName(user.getName());
            user1.setPhone(user.getPhone());
            user1.setPassword(user.getPassword());
            userRepo.save(user1);
        });
    }

    public List<User> findAllByIdAndName(Integer id, String name) {
        Specification<User> spec = Specification.where((root, query, builder) ->  builder.isTrue(builder.literal(true)));

        if(null != id){
            spec = spec.and((root, query, builder) ->
                    builder.equal(root.get("id"),id));
        }

        if(StringUtils.hasLength(name)){
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("name")),
                            name.toLowerCase().concat("%")));
        }
        return userRepo.findAll(spec);
    }
}
