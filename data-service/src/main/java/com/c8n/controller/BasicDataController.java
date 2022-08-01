package com.c8n.controller;

import static com.c8n.constants.RestApiUrls.*;

import com.c8n.constants.CollectionStatus;
import com.c8n.model.dto.ColumnListDto;
import com.c8n.model.dto.UpdateRowDto;
import com.c8n.model.response.SuccessResponse;
import com.c8n.service.BasicDataService;
import com.c8n.util.TimeUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(API+VERSION+ROW)
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class BasicDataController {
    private final BasicDataService basicDataService;
    private final TimeUtil timer;

    public BasicDataController(BasicDataService basicDataService, TimeUtil timer) {
        this.basicDataService = basicDataService;
        this.timer = timer;
    }

    @PostMapping(SAVE)
    public SuccessResponse saveRow(@RequestBody UpdateRowDto rowDto){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.addnewRow(rowDto))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @DeleteMapping(DELETE)
    public SuccessResponse deleteRow(@RequestParam String collectionName, @RequestParam String rowId){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.deleteRow(collectionName, rowId))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping(FINDBYID)
    public SuccessResponse getOneRow(@RequestParam String collectionName, @RequestParam String rowId){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.getRow(collectionName, rowId))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping(FINDALL)
    public SuccessResponse getAllRows(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.getAllRows(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @GetMapping(GETALLCOLUMNNAMES)
    public SuccessResponse getAllColumnNames(@RequestParam String collectionName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.getAllColumns(collectionName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @PostMapping(ADDCOLUMNS)
    public  SuccessResponse addColumsToCollection(@RequestBody ColumnListDto columns){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.addColumns(columns.getCollectionName(), columns.getColumnNames()))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @PutMapping(UPDATE)
    public SuccessResponse updateRowByCollectionName(@RequestBody UpdateRowDto rowDto){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.updateRow(rowDto))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }

    @DeleteMapping(REMOVECOLUMN)
    public SuccessResponse removeColumnFromCollection(@RequestParam String collectionName, @RequestParam String columnName){
        timer.startTimer();
        return SuccessResponse
                .builder()
                .body(basicDataService.deleteColumn(collectionName, columnName))
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .elapsedTime(timer.getTimeAsSecond())
                .build();
    }
}
