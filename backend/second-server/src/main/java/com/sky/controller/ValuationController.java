package com.sky.controller;

import com.sky.result.Result;
import com.sky.vo.ValuationResultVO;
import com.sky.service.ValuationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/valuation")
public class ValuationController {
    @Autowired
    private ValuationService valuationService;

    @PostMapping("/estimate")
    public Result<ValuationResultVO> estimate(@RequestParam("file") MultipartFile file) {
        ValuationResultVO result = valuationService.estimate(file);
        return Result.success(result);
    }
} 