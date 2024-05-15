package com.example.chatApp.util;

import java.util.Base64;

public class ImageUtil {
    // Metod, faylni Base64 formatiga kodlash
    public static String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Metod, Base64 kodlangan matnni rasmlarga o'zgartirish
    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
}
