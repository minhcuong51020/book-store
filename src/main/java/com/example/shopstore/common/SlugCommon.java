package com.example.shopstore.common;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugCommon {
    public static String convertToSlug(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD).toLowerCase(Locale.ROOT).trim();
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        str = pattern.matcher(str).replaceAll("").replace('đ','d');
        str = str.replaceAll(" ", "-");
        return str;
    }

}
