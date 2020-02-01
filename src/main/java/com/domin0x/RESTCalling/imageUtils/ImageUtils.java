package com.domin0x.RESTCalling.imageUtils;

import java.util.Base64;

public class ImageUtils {

    public static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0)
            return Base64.getEncoder().encodeToString(binImage);
        return "";
    }

}
