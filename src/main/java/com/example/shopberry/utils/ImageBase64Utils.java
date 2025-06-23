package com.example.shopberry.utils;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class ImageBase64Utils {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static byte[] readImageAsBytes(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            return Files.readAllBytes(imageFile.toPath());
        } catch (Exception e) {
            return null;
        }
    }

    public static String encode(byte[] data) {
        return ENCODER.encodeToString(data);
    }

    public static byte[] decode(String base64) {
        try {
            return DECODER.decode(base64);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
