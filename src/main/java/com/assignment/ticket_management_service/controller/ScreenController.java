package com.assignment.ticket_management_service.controller;

import com.assignment.ticket_management_service.model.ScreenRegisterRequest;
import com.assignment.ticket_management_service.service.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screens")
@CrossOrigin
public class ScreenController {
    ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<Object> registerScreen(@RequestBody ScreenRegisterRequest screenRegisterRequest){
        return ResponseEntity.ok().body(screenService.registerScreen(screenRegisterRequest));
    }

    @DeleteMapping("{screenId}")
    public ResponseEntity<String> deleteScreen(@PathVariable Integer screenId){
        return ResponseEntity.ok().body(screenService.deleteScreen(screenId));
    }
}
