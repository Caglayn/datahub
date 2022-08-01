package com.c8n.controller;

import com.c8n.model.response.SuccessResponse;
import com.c8n.service.ImportFromSqlService;
import com.c8n.util.TimeUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class ImportHubFromSqlController {
    private final ImportFromSqlService importService;
    private final TimeUtil timer;

    public ImportHubFromSqlController(ImportFromSqlService importService, TimeUtil timer) {
        this.importService = importService;
        this.timer = timer;
    }

    @GetMapping("/query")
    public SuccessResponse importFromQuery(@RequestParam String query, @RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse.builder()
                .body(importService.importFromQuery(collectionName, query))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }
}
