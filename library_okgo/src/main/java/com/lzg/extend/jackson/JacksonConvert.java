package com.lzg.extend.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lzg.extend.BaseResponse;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.utils.OkLogger;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JacksonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JacksonConvert() {
    }

    public JacksonConvert(Type type) {
        this.type = type;
    }

    public JacksonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        ResponseBody body = response.body();
        if (body == null) return null;

        String json = body.string();
        OkLogger.i(json);

        if (clazz == null) {
            Type genType = getClass().getGenericSuperclass();
            final Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];

            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType(); //泛型的实际类型
                Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0]; //泛型的参数

                if (rawType == BaseResponse.class) {

                    if (typeArgument instanceof ParameterizedType) {
                        Type rawTypeInner = ((ParameterizedType) typeArgument).getRawType(); //泛型的实际类型
                        Type typeArgumentInner = ((ParameterizedType) typeArgument).getActualTypeArguments()[0]; //泛型的参数

                        if (rawTypeInner == ArrayList.class) {
                            Class<?> clazzArgument = getClass(typeArgumentInner);

                            BaseResponse baseResponse = new BaseResponse();
                            JSONArray jsonArray = new JSONObject(json).optJSONArray("data");
                            baseResponse.data = JsonUtil.toList(jsonArray == null ? "[]" : jsonArray.toString(), clazzArgument);

                            response.close();
                            return (T) baseResponse;
                        } else {

                            T t = JsonUtil.jsonToBean(json, new TypeReference<T>() {
                            }); //解析成为LinkedHashMap
                            response.close();
                            return t;
                        }
                    } else {
                        Class<?> clazzArgument = getClass(typeArgument);

                        BaseResponse baseResponse = new BaseResponse();
                        JSONObject jsonObject = new JSONObject(json).optJSONObject("data");
                        baseResponse.data = JsonUtil.jsonToBean(jsonObject == null ? "{}" : jsonObject.toString(), clazzArgument);

                        response.close();
                        return (T) baseResponse;
                    }
                } else {

                    T t = JsonUtil.jsonToBean(json, new TypeReference<T>() {
                    }); //解析成为LinkedHashMap
                    response.close();
                    return t;
                }
            }
        } else {
            T t = JsonUtil.jsonToBean(json, clazz);
            response.close();
            return t;
        }
        return null;
    }

    private String getClassName(Type type) {
        if (type == null) return "";
        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring("class ".length());
        } else if (className.startsWith("interface ")) {
            className = className.substring("interface ".length());
        }
        return className;
    }


    private Class<?> getClass(Type type) throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) return null;
        return Class.forName(className);
    }

}
