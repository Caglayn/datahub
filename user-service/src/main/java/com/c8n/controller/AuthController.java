package com.c8n.controller;

import static com.c8n.constants.RestApiUrls.*;

import com.c8n.model.request.SaveUserDto;
import com.c8n.model.response.DhUserDto;
import com.c8n.model.response.SuccessResponse;
import com.c8n.service.AuthService;
import com.c8n.service.DhUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(API+VERSION+AUTH)
public class AuthController {
    private final AuthService authService;
    private final DhUserService userService;

    public AuthController(AuthService authService, DhUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(SAVE)
    public SuccessResponse signUp(SaveUserDto user){
        return SuccessResponse
                .builder()
                .body(authService.singUp(user))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @PostMapping(SIGNIN)
    public SuccessResponse signIn(@RequestParam String userName, @RequestParam String password){
        return SuccessResponse
                .builder()
                .body(authService.signIn(userName, password))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("/validate")
    public SuccessResponse validate(@RequestParam String token){
        return SuccessResponse
                .builder()
                .body(authService.validateToken(token))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("/userdetail")
    public DhUserDto getUserDetail(@RequestParam String username){
        return userService.getUser(username);
    }
}
