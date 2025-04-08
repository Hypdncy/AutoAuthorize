package com.hypdncy.autoauthorize.entity;

import burp.api.montoya.http.message.HttpRequestResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ClassName: AutoAuthorizeTableEntity
 * Package: com.hypdncy.autoauthorize.entity
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/3/30 19:08
 * @Version 1.0
 */

@Getter
@Setter
@ToString
public class AutoAuthorizeTableEntity {
    private String method;
    private String uri;
    private int originLen;
    private int reAuthLen;
    private int unAuthLen;
    private String result;
    private HttpRequestResponse originRequestResponse;
    private HttpRequestResponse reAuthRequestResponse;
    private HttpRequestResponse unAuthRequestResponse;

    public AutoAuthorizeTableEntity(HttpRequestResponse originRequestResponse,
                                    HttpRequestResponse reAuthRequestResponse,
                                    HttpRequestResponse unAuthRequestResponse) {
        method = originRequestResponse.request().method();
        uri = originRequestResponse.request().url();
        originLen = originRequestResponse.response().body().length();
        reAuthLen = reAuthRequestResponse.response().body().length();
        unAuthLen = unAuthRequestResponse.response().body().length();
        this.originRequestResponse = originRequestResponse;
        this.reAuthRequestResponse = reAuthRequestResponse;
        this.unAuthRequestResponse = unAuthRequestResponse;
        result = "Success";
    }
}
