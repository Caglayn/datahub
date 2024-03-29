package com.c8n.controller;

import com.c8n.model.response.SuccessResponse;
import com.c8n.service.BasicDataQueryParsingService;
import com.c8n.util.TimeUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.c8n.constants.RestApiUrls.*;

@RestController
@RequestMapping(API+VERSION+QUERY)
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class BasicDataQueryController {

    private final TimeUtil timer;
    private final BasicDataQueryParsingService queryParsingService;

    public BasicDataQueryController(TimeUtil timer, BasicDataQueryParsingService queryParsingService) {
        this.timer = timer;
        this.queryParsingService = queryParsingService;
    }

    @GetMapping
    public SuccessResponse executeQuery(@RequestParam String query){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(queryParsingService.parseQuery(query.toLowerCase()))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }
}
