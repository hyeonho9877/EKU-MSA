package com.hyunho9877.infoboard.utils.common;

import org.springframework.stereotype.Component;

@Component
public class WriterGenerator {

    public String generate(String studNo, String name) {
        String year = studNo.substring(2, 4);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append(" ").append(name);

        return sb.toString();
    }
}
