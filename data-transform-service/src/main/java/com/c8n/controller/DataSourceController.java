package com.c8n.controller;

import com.c8n.model.dto.DataSourceDto;
import com.c8n.model.response.SuccessResponse;
import com.c8n.service.DataSourceService;
import com.c8n.util.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ds")
@CrossOrigin(origins = "*")
public class DataSourceController {
    private final DataSourceService dataSourceService;
    private final TimeUtil timer;

    public DataSourceController(DataSourceService dataSourceService, TimeUtil timer) {
        this.dataSourceService = dataSourceService;
        this.timer = timer;
    }

    @PostMapping
    private SuccessResponse changeDataSource(@RequestBody DataSourceDto dto){
        timer.startTimer();
        return SuccessResponse.builder()
                .body(dataSourceService.changeDatasource(dto))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }
}
