package com.ttm.pet.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    private final static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     */
    public static Map doXMLParse(String strxml) {
        try {
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

            if (null == strxml || "".equals(strxml)) {
                return null;
            }
            Map m = new HashMap();

            InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XmlUtil.getChildrenText(children);
                }

                m.put(k, v);
            }

            //关闭流
            in.close();

            return m;
        } catch (Exception e){
            e.printStackTrace();
            logger.error("xml解析失败，xml:"+strxml);
            return null;
        }

    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(XmlUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String a = "<xml>\n" +
                "<total_fee>1</total_fee>\n" +
                "<trade_type>JSAPI</trade_type>\n" +
                "<mch_id>1591338951</mch_id>\n" +
                "<body>数商企云服务-会员充值</body>\n" +
                "<notify_url>http://192.168.1.12:8081/ssBusiness/mini/wxPay/notify</notify_url>\n" +
                "<spbill_create_ip>192.168.1.12</spbill_create_ip>\n" +
                "</xml>";
        Map<String,Object> m = XmlUtil.doXMLParse(a);
        System.out.println(m.get("appid"));
    }
}

