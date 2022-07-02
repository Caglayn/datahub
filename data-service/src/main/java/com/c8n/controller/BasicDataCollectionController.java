package com.c8n.controller;

import static com.c8n.constants.RestApiUrls.*;

import com.c8n.model.BasicDataHub;
import com.c8n.model.response.SuccessResponse;
import com.c8n.service.BasicDataCollectionService;
import com.c8n.util.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(API+VERSION+COLLECTION)
@CrossOrigin(origins = "*")
public class BasicDataCollectionController {
    private final BasicDataCollectionService collectionService;
    private final TimeUtil timer;
    private final BasicDataHub basicDataHub;

    public BasicDataCollectionController(BasicDataCollectionService collectionService, TimeUtil timer, BasicDataHub basicDataHub) {
        this.collectionService = collectionService;
        this.timer = timer;
        this.basicDataHub = basicDataHub;
    }

    @PostMapping(SAVE)
    public SuccessResponse create(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.addCollection(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @DeleteMapping(DELETE)
    public SuccessResponse delete(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.deleteCollection(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping(GETALLNAMES)
    public SuccessResponse getAllCollectionNames(){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.getAllCollectionNames())
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @PostMapping(BACKUP_COLLECTION)
    public SuccessResponse backupCollectionByCollectionName(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.backupCollectionByName(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @PostMapping(BACKUP_ALL)
    public SuccessResponse backupAllCollections(){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.backupAllCollections())
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping(RESTORE_BY_NAME)
    public SuccessResponse restoreCollectionByName(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.restoreCollectionByName(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @PostMapping(RESTORE_ALL)
    public SuccessResponse restoreAllDataFromDb(){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(collectionService.restoreAllCollections())
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping("/gethub")
    public BasicDataHub getHub(){
        return basicDataHub;
    }
}
