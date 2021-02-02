package com.example.aop.web;

import com.example.aop.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CommonDto<?> save(@RequestBody @Valid JoinReqDto dto, BindingResult bindingResult) {
        System.out.println("save()");
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });
            return new CommonDto<>(errorMap, HttpStatus.BAD_REQUEST.value());
        }
        userRepository.save(dto);
        return new CommonDto<>("ok", HttpStatus.CREATED.value());
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
    public CommonDto<?> update(@PathVariable int id, @Valid @RequestBody UpdateReqDto dto, BindingResult bindingResult){
        System.out.println("update()");
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });
            return new CommonDto<>(errorMap, HttpStatus.BAD_REQUEST.value());
        }
        userRepository.update(id, dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }
}
