package com.base.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.beanutils.BeanUtils.copyProperty;

/**
 * Json相关util
 */
public class JsonUtils {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 转成 Json 字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 对象转换
     */
    public static <T> T createBean(Object origObj, Class<T> destClazz) {
        String jsonStr = toJson(origObj);
        return str2Bean(jsonStr, destClazz);
    }

    /**
     * Json字符串转对象
     */
    public static <T> T str2Bean(String json, Class<T> beanClass) {
        return gson.fromJson(json, beanClass);
    }

    /**
     * Json转List集合
     */
    public static <T> List<T> str2List(String json, Class<T> clz) {
        List<T> mList = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        Gson mGson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, clz));
        }
        return mList;
    }


    /**
     * Json转Map对象
     */
    public static <T> Map<String, T> str2Map(String json) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Json转Map对象
     */
    public static Map<String, String> str2StringMap(String json) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * List 对象转换
     */
    public static <T> List<T> createList(List<?> origList, Class<T> destClazz) {
        String jsonStr = toJson(origList);
        return str2List(jsonStr, destClazz);
    }

    /**
     * map转对象
     */
    public static <T> T map2Bean(Map map, Class<T> destClazz) {
        String jsonStr = toJson(map);
        return str2Bean(jsonStr, destClazz);
    }

    /**
     * 对象转map
     */
    public static Map bean2Map(Object object) {
        String jsonStr = toJson(object);
        return str2Bean(jsonStr, Map.class);
    }

    public static void copyBeanNotNull2Bean(Object databean, Object tobean) {
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(databean);

        for(int i = 0; i < origDescriptors.length; ++i) {
            String name = origDescriptors[i].getName();
            if (!"class".equals(name) && PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(databean, name);
                    if (value != null) {
                        copyProperty(tobean, name, value);
                    }
                } catch (IllegalArgumentException var6) {
                } catch (Exception var7) {
                }
            }
        }

    }

    /**
     * 反射调用对象的get方法
     * @param getName
     * @param obj
     * @return
     */
    public static Object getFieldValue(String getName, Object obj) {
        String firstLetter = getName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + getName.substring(1);

        try {
            Method method = obj.getClass().getMethod(getter);
            Object value = method.invoke(obj);
            return value;
        } catch (NoSuchMethodException var7) {
            var7.printStackTrace();
        } catch (SecurityException var8) {
            var8.printStackTrace();
        } catch (IllegalArgumentException var9) {
        } catch (InvocationTargetException var10) {
            var10.printStackTrace();
        } catch (IllegalAccessException var11) {
            var11.printStackTrace();
        }

        return null;
    }
}
