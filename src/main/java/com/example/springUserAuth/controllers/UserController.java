package com.example.springUserAuth.controllers;

import com.example.springUserAuth.dtos.BaseResponseDTO;
import com.example.springUserAuth.dtos.SignupDto;
import com.example.springUserAuth.dtos.UserDto;
import com.example.springUserAuth.models.User;
import com.example.springUserAuth.services.UserService;
import com.example.springUserAuth.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userservice;





    @PostMapping("/test")
    public  String func(){
        return "Hello world";
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDTO> userSignup(@RequestBody SignupDto req){
        Boolean res = userservice.signup( req.getEmail(), req.getName(),req.getPassword(),req.getRole());
        BaseResponseDTO signInRes = new BaseResponseDTO();
        if(res){
          signInRes.setMessage("User signed in successfully");
          signInRes.setStatus("success");
          signInRes.setError(false);
          signInRes.setCode(200);
          return ResponseEntity.ok(signInRes);
        }else{
            signInRes.setMessage("User email already exist");
            signInRes.setStatus("Error");
            signInRes.setError(true);
            signInRes.setCode(400);
            return ResponseEntity.badRequest().body(signInRes);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            Map<String, String> tokens = userservice.login(email, password);
            return ResponseEntity.ok(ResponseUtil.getResponse(200, false, "Success", "Login successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseUtil.getResponse(400, true, "Error", e.getMessage()));
        }
    }

    @PostMapping("/verifyEmail")
    public  ResponseEntity<BaseResponseDTO> userEmailVerify(@RequestBody Map<String,Object> reqBody){
        //System.out.println(">>>>_____"+email);
        boolean res = userservice.verifyEmail((String)reqBody.get("email"));
        return res?
                ResponseEntity.ok(ResponseUtil.getResponse(200,false,"Success","Email verified successfully")):
                ResponseEntity.badRequest().body(ResponseUtil.getResponse(400,true,"Error","Email verification failed"));
    }




}
