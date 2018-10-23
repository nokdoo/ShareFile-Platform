package com.common.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Encryptor {
	
	
    private static final List<Integer> VALID_PWD_CHARS = new ArrayList<>();

    static {
        IntStream.rangeClosed('0', '9').forEach(VALID_PWD_CHARS::add); // 0-9
        IntStream.rangeClosed('A', 'Z').forEach(VALID_PWD_CHARS::add); // A-Z
        IntStream.rangeClosed('a', 'z').forEach(VALID_PWD_CHARS::add); // a-z
    }

    public static String GenerateCryptString() {
        int passwordLength = 31;
        String a = new SecureRandom().ints(passwordLength, 0, VALID_PWD_CHARS.size()).map(VALID_PWD_CHARS::get)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return a;
    }

    
    

}
