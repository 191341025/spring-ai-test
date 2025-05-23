package com.saninco.pdtstructuredoutput.controller;

import com.saninco.pdtstructuredoutput.model.BillParseRequest;
import com.saninco.pdtstructuredoutput.model.BillParseResult;
import com.saninco.pdtstructuredoutput.service.BillParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class BillParseController {

    @Autowired
    private BillParseService billParseService;

    @PostMapping("/parse-bill")
    public BillParseResult parseBill(@RequestBody BillParseRequest request) {
        return billParseService.parseBill(request.getRawText());
    }
}
