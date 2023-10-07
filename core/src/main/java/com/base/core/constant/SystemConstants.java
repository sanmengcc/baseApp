package com.base.core.constant;

/**
 * 系统常量
 */
public class SystemConstants {

    /**
     * utf8字符集编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 基础包名
     */
    public static final String BASE_PACKAGE = "oem.cloud";

    /**
     * 应用名称
     */
    public static final String APP_NAME = "oemCloud";

    /**
     * 默认oemcode
     */
    public static final String DEFAULT_OEM_CODE = "default_oem_cloud";

    /**
     * 默认token
     */
    public static final String DEFAULT_TOKEN = "default_token";


    /**
     * 接口请求头
     */
    public interface HttpHeader{

        /**
         * token请求头
         */
        String AUTHORIZATION = "Authorization";

        /**
         * oemCode租户代码
         */
        String OEM_CODE = "Oem-Code";

        /**
         * 轨迹ID
         */
        String TRACE_ID = "TraceId";

        /**
         * 请求来源
         */
        String X_FOR = "X-FOR";

    }

    /**
     * 前端路由模式
     */
    public interface RouterMode {

        /**
         * vue
         */
        String VUE = "Vue";

        /**
         * React
         */
        String REACT = "React";
    }

    /**
     * oss通道
     */
    public interface OssChannel {

        /**
         * minio
         */
        String MINIO = "MINIO";
    }

    /**
     * 环境变量
     */
    public interface Profile {

        String DEV = "dev";

        String TEST = "test";

        String PROD = "prod";
    }

    /**
     * Dubbo上下文
     */
    public interface DubboAttachment{

        /**
         * oemCode
         */
        String OEMC_CODE = "OEM_CODE";

        /**
         * 用户token
         */
        String TOKEN = "TOKEN";

        /**
         * 事件追踪ID
         */
        String TRACE_ID = "TRACE_ID";
    }
}
