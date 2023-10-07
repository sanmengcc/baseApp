package com.base.core.entity;

import com.base.util.JsonUtils;

import java.io.Serial;
import java.util.HashMap;
import java.util.Objects;

/**
 * 接口返回基类
 */
public class R<T> extends HashMap<String, Object> {
    @Serial
    private static final long serialVersionUID = 1L;

    public R() {
    }

    public R(String code, String desc) {
        put("code", code);
        put("desc", desc);
    }

    /**
     * 请求成功
     * @return
     */
    public static R ok() {
        return new R("200","操作成功");
    }

    public static R error() {
        return error("500", "服务端异常,请联系管理员");
    }

    public static R error(String msg) {
        return error("500", msg);
    }

    /**
     * 设置错误信息
     * @param msg
     * @return
     */
    public R setError(String code,String msg) {
        put("code", code);
        put("msg", msg);
        return this;
    }

    /**
     * 设置错误信息
     * @param msg
     * @return
     */
    public R setError(String msg) {
        put("code", "500");
        put("msg", msg);
        return this;
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 操作成功
     * @param object
     * @return
     */
    public static R ok(Object object) {
        R r = new R("200", "操作成功");
        r.put("data", object);
        return r;
    }

    /**
     * 获取数据
     * @return
     */
    public Object getData() {
        return get("data");
    }

    /**
     * 自定义数据
     * @param object
     * @return
     */
    public R setData(Object object) {
        if (Objects.nonNull(object)) {
            put("data", object);
        }
        return this;
    }

    /**
     * 自定义数据
     * @param object
     * @return
     */
    public static R data(Object object) {
        R r = new R("200", "操作成功");
        if (Objects.nonNull(object)) {
            r.put("data", object);
        }
        return r;
    }

    /**
     * 转换Json
     * @return
     */
    public String toJson() {
        return JsonUtils.toJson(this);
    }

}
