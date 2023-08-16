package com.blogstack;

import com.blogstack.beans.requests.QuestionIdRequestBean;
import com.blogstack.service.IBlogStackQuestionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Set;

@SpringBootApplication
@EnableFeignClients
public class BlogStackQNAHubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogStackQNAHubServiceApplication.class, args);
    }
}