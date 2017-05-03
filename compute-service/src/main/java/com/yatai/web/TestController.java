package com.yatai.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;

@RefreshScope
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }
    
    @RequestMapping("/date")
    public String date() {
    	return DateUtil.toString(new Date(), DatePattern.COMMON_DATE_AND_TIME);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

}