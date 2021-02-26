package com.hillel.artemjev.homework21.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PhonesSelector {
    public Set<String> getPhoneNumbers(String text) {
        String regexToSplit = "[\\s,.!?;()\"-]+";
        String regexPhoneNumber = "(?:\\+380|380|80|0)?(\\d{9})";

        return Arrays.stream(text.split(regexToSplit))
                .filter(s -> s.matches(regexPhoneNumber))
                .map(s -> s.replaceFirst(regexPhoneNumber, "+380$1"))
                .collect(Collectors.toSet());
    }
}
