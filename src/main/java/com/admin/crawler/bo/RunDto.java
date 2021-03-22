package com.admin.crawler.bo;

import lombok.Data;

@Data
public class RunDto {
    private String color;
    private String content;


    public RunDto() {
    }

    public RunDto(String color, String content) {
        this.color = color;
        this.content = content;
    }
}
