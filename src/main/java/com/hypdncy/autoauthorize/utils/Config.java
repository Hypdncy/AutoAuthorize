package com.hypdncy.autoauthorize.utils;


import burp.api.montoya.MontoyaApi;
import com.hypdncy.autoauthorize.entity.HeadersEntity;
import com.hypdncy.autoauthorize.entity.WhiteHostEntity;

import java.util.HashSet;

/**
 * ClassName: Config
 * Package: com.hypdncy
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/2/15 17:24
 * @Version 1.0
 */
public class Config {

    public static boolean EnabledStatus = false;
    public static boolean EnabledWhiteStatus = false;
    public static int AuthCompareLen = 20;

    public static HeadersEntity unAuthorizedHeaders = new HeadersEntity();
    public static HeadersEntity reAuthorizedHeaders = new HeadersEntity();
    public static WhiteHostEntity whiteHostEntity = new WhiteHostEntity();
    public static HashSet<String> whiteHostHashSet = new HashSet<>();
}
