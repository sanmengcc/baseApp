package com.base.core.context;

import com.base.core.entity.UserInfo;
import com.base.util.ValidateHelper;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局上下文
 */
public class CloudManager {

    private static CloudManager instance = new CloudManager();
    private static final String USERINFO = "USERINFO";

    private static final String OEM_CODE = "OEM_CODE";

    private static final String TRACE_ID = "TRACE_ID";

    private static final String TOKEN = "TOKEN";
    private ThreadLocal<Map<String, Object>> map = new ThreadLocal();
    private CloudManager() {
        this.map.set(new ConcurrentHashMap());
    }

    public static CloudManager getInstance() {
        return instance;
    }

    public void setToken(String token) {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }
        if (ValidateHelper.isEmptyString(token)) {
            return;
        }
        ((Map)this.map.get()).put(TOKEN, token);
    }

    public String getToken() {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }

        return (String) ((Map) this.map.get()).get(TOKEN);
    }

    public void setOemCode(String oemCode) {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }
        if (ValidateHelper.isEmptyString(oemCode)) {
            return;
        }
        ((Map)this.map.get()).put(OEM_CODE, oemCode);
    }

    public String getOemCode() {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }

        return (String) ((Map) this.map.get()).get(OEM_CODE);
    }

    public void setTraceId(String traceId) {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }
        if (ValidateHelper.isEmptyString(traceId)) {
            return;
        }
        ((Map)this.map.get()).put(TRACE_ID, traceId);
    }

    public String getTraceId() {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }

        return (String) ((Map) this.map.get()).get(TRACE_ID);
    }

    public void setUserInfo(UserInfo userInfo) {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }
        if (Objects.isNull(userInfo)) {
            return;
        }
        ((Map)this.map.get()).put(USERINFO, userInfo);
    }

    public UserInfo getUserInfo() {
        if (this.map.get() == null) {
            this.map.set(new ConcurrentHashMap());
        }

        return (UserInfo)((Map) this.map.get()).get(USERINFO);
    }

    public void clear() {
        this.map.remove();
    }
}
