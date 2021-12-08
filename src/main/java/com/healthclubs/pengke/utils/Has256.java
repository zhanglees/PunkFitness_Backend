package com.healthclubs.pengke.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.StringUtils;

public class Has256 {

    public static String sign(String stringToSign, String accessKeySecret)
    {
        byte[]hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256,
                accessKeySecret).hmac(stringToSign);
        return Base64.encodeBase64String(hmac);
    }
}
