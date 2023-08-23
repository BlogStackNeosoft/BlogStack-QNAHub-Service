package com.blogstack.feign.clients;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BlogStackIAMService", url = "http://localhost:9095/v1.0/user")
public interface IBlogStackUserFeignClient {

    @GetMapping("/user/{user_Id}")
    ResponseEntity<?> fetchUserById(@PathVariable(value = "user_Id") @NotBlank(message = "user id can not be blank") String userId);

}