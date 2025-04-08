package com.hypdncy.autoauthorize.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;

/**
 * ClassName: HostEntity
 * Package: com.hypdncy.vulnscan.entity
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/2/25 22:31
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class WhiteHostEntity {

    public HashSet<String> HostNames;
    public HashSet<String> HostEnds;

    public void setData(String Text) {
        HostEnds.clear();
        HostNames.clear();
        // 按行分割字符串
        // 使用正则表达式 \\r?\\n 来匹配换行符 (\n) 或回车换行符 (\r\n)
        String[] addLines = Text.split("\\r?\\n");

        // 遍历并打印分割后的每一行
        for (String str : addLines) {
            if (str.startsWith("*")) {
                HostEnds.add(str.substring(1));
            } else {
                HostNames.add(str);
            }
        }
    }

    public static boolean checkIn(WhiteHostEntity whiteHostEntity, String hostname) {
        for (String hostEnd : whiteHostEntity.HostEnds) {
            if (hostname.endsWith(hostEnd)) {
                return true;
            }
        }
        return whiteHostEntity.HostNames.contains(hostname);

    }
}
