package com.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BatchItemProcessor implements ItemProcessor<User,User> {
    private static final Map<String,String> dep = new HashMap<>();

    public BatchItemProcessor() {
        dep.put("01","Computer");
        dep.put("02","Science");
    }

    @Override
    public User process(User user) throws Exception {
        System.out.println("processor: called : "+ user);
       user.setDep(dep.get(user.getDep()));
        return user;
    }
}
