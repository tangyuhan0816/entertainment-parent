package com.vpis.common.utils;

import com.vpis.common.entity.pay.request.wechat.WxPayUnifiedorderRequest;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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


    public static Object toObject(String xml, Class objClass) {
        Persister serializer = new Persister();

        try {
            return serializer.read(objClass, xml);
        } catch (Exception var4) {
            var4.printStackTrace();
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

    public static Map<String, String> toMap(String strXML) {
        try {
            Map<String, String> data = new HashMap();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for(int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == 1) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element)node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }

            try {
                stream.close();
            } catch (Exception var10) {
                ;
            }

            return data;
        } catch (Exception var11) {
            var11.printStackTrace();
            return null;
        }
    }
}
