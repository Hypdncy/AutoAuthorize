package com.hypdncy.autoauthorize.entity;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.params.HttpParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: HeaderEntity
 * Package: com.hypdncy.autoauthorize.entity
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/3/30 17:43
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class HeadersEntity {
    private List<HttpHeader> UpdateHeaders = new ArrayList<>();
    private List<HttpHeader> DeleteHeaders = new ArrayList<>();

    // 只有存在才会修改
    private List<HttpParameter> AddedParameters = new ArrayList<>();
    // 只有存在才会修改
    private List<HttpParameter> DeleteParameters = new ArrayList<>();


    public void setData(String addText, String delText) {
        UpdateHeaders.clear();
        DeleteHeaders.clear();
        // 按行分割字符串
        // 使用正则表达式 \\r?\\n 来匹配换行符 (\n) 或回车换行符 (\r\n)
        String[] addLines = addText.split("\\r?\\n");

        // 遍历并打印分割后的每一行
        for (String str : addLines) {
            if (str.contains(": ")) {
                String[] s = str.strip().split(": ", 2);
                UpdateHeaders.add(HttpHeader.httpHeader(s[0], s[1]));
            }
        }

        // 按行分割字符串
        // 使用正则表达式 \\r?\\n 来匹配换行符 (\n) 或回车换行符 (\r\n)
        String[] delLines = delText.split("\\r?\\n");

        // 遍历并打印分割后的每一行
        for (String str : delLines) {
            if (!str.isEmpty()) {
                DeleteHeaders.add(HttpHeader.httpHeader(str.strip()));
            }
        }
    }

    public HeadersEntity() {
    }
}
