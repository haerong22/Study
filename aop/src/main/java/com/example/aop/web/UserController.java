package com.example.aop.web;

import com.example.aop.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // http://localhost:8080/user
    @GetMapping("/user")
    public ResponseEntity<List<User>> findAll() {
        System.out.println("findAll()");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK); // MessageConverter ( Java Object -> Json string )
    }

    // http://localhost:8080/user/{id}
    @GetMapping("/user/{id}")
    public CommonDto<User> findById(@PathVariable int id) {
        System.out.println("findById() : " + id);
        return new CommonDto<>(userRepository.findById(id), HttpStatus.OK.value());
    }

    // http://localhost:8080/user
    // x-www-form-urlencoded => request.getParameter()
    // text/plain => @RequestBody
    // CORS 정책 무시 @CrossOrigin
    @CrossOrigin
    @PostMapping("/user")
    public ResponseEntity<CommonDto<String>> save(@RequestBody JoinReqDto dto) {
        System.out.println("save()");
        userRepository.save(dto);
        return new ResponseEntity<>(
                new CommonDto<>("ok", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    // http://localhost:8080/user/{id}
    @DeleteMapping("/user/{id}")
    public CommonDto<String> delete(@PathVariable int id) {
        System.out.println("delete()");
        int status = userRepository.delete(id) == 1 ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return new CommonDto<>(status);
    }

    // http://localhost:8080/user/{id}
    @PutMapping("/user/{id}")
    public CommonDto<String> update(@PathVariable int id, @RequestBody UpdateReqDto dto){
        System.out.println("update()");
        userRepository.update(id, dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }
}
