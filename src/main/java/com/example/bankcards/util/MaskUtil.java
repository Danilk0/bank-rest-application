package com.example.bankcards.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MaskUtil {

    public String mask(String input) {
        StringUtils.deleteWhitespace(input);
        String endString = input.substring(12);
        String mask= "**** **** **** ";
        return mask+endString;
    }
}
