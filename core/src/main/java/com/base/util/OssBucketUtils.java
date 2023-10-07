package com.base.util;

import com.base.core.context.CloudManager;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

/**
 * Oss Bucket工具类
 */
public class OssBucketUtils {

    private static final String separatorChar = "/";

    /**
     * 获取bucketName
     *
     * @param bucketPrivate
     * @return
     */
    public static String generateBucketName(String bucketName, boolean bucketPrivate) {
        if (bucketPrivate) {
            return bucketName + "-private";
        }
        return bucketName + "-public";
    }

    /**
     * 生成上传路径
     * @param fileName
     * @param keepOrigName
     * @return
     */
    public static String generatePath(String fileName, boolean keepOrigName) {
        String profile = SpringBeanUtil.getProfile();
        String suffix = fileName.substring(fileName.lastIndexOf(".") +1);
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        if (!keepOrigName) {
            name = UUIDGenerator.generate();
        }
        return new StringBuilder()
                .append(profile)
                .append(separatorChar)
                .append(LocalDate.now())
                .append(separatorChar)
                .append(name + "." + suffix)
                .toString();
    }

    /**
     * 获取url对应实际存储路径
     * @param url
     * @return
     */
    public static String getPath(String url) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        String path = uriComponents.getPath();
        String[] params = path.split(separatorChar);
        return path.replace(separatorChar + params[0] + separatorChar, "");
    }

    /**
     * 获取访问路径、去掉query参数
     * @param url
     * @return
     */
    public static String getURL(String url) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        int port = uriComponents.getPort();
        StringBuilder append = new StringBuilder()
                .append(uriComponents.getScheme())
                .append("://")
                .append(uriComponents.getHost());
        if (port > -1) {
            append.append(":" + port);
        }
        append.append(uriComponents.getPath());

        return append.toString();
    }

    /**
     * 获取bucketName
     * @param url
     * @return
     */
    public static String getBucket(String url) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        String path = uriComponents.getPath();
        String[] params = path.split(separatorChar);
        return params[0];
    }

}
