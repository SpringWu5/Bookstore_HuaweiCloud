package com.sky.service;

import com.sky.vo.ValuationResultVO;
import org.springframework.web.multipart.MultipartFile;

public interface ValuationService {
    ValuationResultVO estimate(MultipartFile file);
} 