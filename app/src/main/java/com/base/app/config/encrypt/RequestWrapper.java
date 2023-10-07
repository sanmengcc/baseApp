package com.base.app.config.encrypt;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author: chenJY
 * @Description:
 * @Date: 2022-10-24 10:37
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    // 存储流的容器
    private byte[] requestBody =null;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 将流复制到字节数组 requestBody 中
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * @Description 获取请求体
     * @author chenJY
     * @date 2022/10/24 14:21
     * @param request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description 获取请求体
     * @author chenJY
     * @date 2022/10/24 14:23
     * @return String
     */
    public String getBodyString() {
        final InputStream inputStream = new ByteArrayInputStream(requestBody);

        return inputStream2String(inputStream);
    }

    /**
     * @Description 读取inputStream数据，并转换为String
     * @author chenJY
     * @date 2022/10/24 14:24
     * @param inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("异常：", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }

        return sb.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                log.info("setReadListener......");
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
