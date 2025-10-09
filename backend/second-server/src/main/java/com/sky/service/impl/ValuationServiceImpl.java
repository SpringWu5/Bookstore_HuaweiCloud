package com.sky.service.impl;

import com.sky.service.ValuationService;
import com.sky.vo.ValuationResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ValuationServiceImpl implements ValuationService {
    @Override
    public ValuationResultVO estimate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空");
        }
        String base64Img;
        try {
            base64Img = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("图片读取失败");
        }
        String DEEPSEEK_URL = "https://api.deepseek.com/v1/chat/completions";
        String API_KEY = "sk-db50b5bb2ec047fbb64eebfab97f5fb7"; // 请替换为你的DeepSeek密钥
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + API_KEY);
        headers.put("Content-Type", "application/json");
        // 构造多模态prompt
        String prompt = "请帮我识别这张图片的商品类别、成色，并给出合理的二手价格，返回json格式，字段为price, condition, category";
        JSONObject imageUrlObj = new JSONObject();
        imageUrlObj.put("type", "image_url");
        JSONObject urlObj = new JSONObject();
        urlObj.put("url", "data:image/jpeg;base64," + base64Img);
        imageUrlObj.put("image_url", urlObj);
        JSONObject textObj = new JSONObject();
        textObj.put("type", "text");
        textObj.put("text", prompt);
        JSONArray contentArr = new JSONArray();
        contentArr.add(textObj);
        contentArr.add(imageUrlObj);
        JSONObject messageObj = new JSONObject();
        messageObj.put("role", "user");
        messageObj.put("content", contentArr);
        JSONArray messagesArr = new JSONArray();
        messagesArr.add(messageObj);
        JSONObject body = new JSONObject();
        body.put("model", "deepseek-vl-7b-chat"); // 或官方推荐的多模态模型名
        body.put("messages", messagesArr);
        String responseJson;
        try {
            responseJson = com.sky.utils.HttpClientUtil.doPostRawJsonWithHeader(DEEPSEEK_URL, body.toJSONString(), headers);
            System.out.println("DeepSeek返回: " + responseJson);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用AI估价服务失败: " + e.getMessage());
        }
        // 解析AI回复内容
        JSONObject resp = JSONObject.parseObject(responseJson);
        JSONArray choices = resp.getJSONArray("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("AI未返回有效结果");
        }
        JSONObject message = choices.getJSONObject(0).getJSONObject("message");
        String content = message.getString("content");
        // content应为AI返回的json字符串，需再次parse
        JSONObject result;
        try {
            result = JSONObject.parseObject(content);
        } catch (Exception e) {
            throw new RuntimeException("AI返回内容解析失败: " + content);
        }
        ValuationResultVO vo = new ValuationResultVO();
        vo.setPrice(result.getString("price"));
        vo.setCondition(result.getString("condition"));
        vo.setCategory(result.getString("category"));
        return vo;
    }
} 