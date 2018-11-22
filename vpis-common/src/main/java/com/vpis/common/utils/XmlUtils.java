package com.vpis.common.utils;

import com.vpis.common.entity.pay.request.wechat.WxPayUnifiedorderRequest;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: XmlUtils
 *  @package: com.vpis.common.utils
 *  @Date: Created in 2018/11/22 下午9:17
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: XMl工具类
 */
public class XmlUtils {

    public static String xmlToString(WxPayUnifiedorderRequest request){
        Serializer serializer = new Persister();
        StringWriter stringWriter = new StringWriter();
        try {
            serializer.write(request, stringWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static Object stringToXml(String xml, Class<?> cls){
        Serializer serializer = new Persister();

        try {
            return serializer.read(cls,xml);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Map<String, String> buildMap(Object obj) {
        Map<String, String> params = new HashMap<>();
        try {
            Class<?> aClass = obj.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Element annotation = field.getAnnotation(Element.class);
                if (Preconditions.isNotBlank(annotation)) {
                    fieldName = annotation.name();
                }
                String value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
                if(Preconditions.isBlank(value)){
                    continue;
                }
                params.put(fieldName,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return params;
    }
}
