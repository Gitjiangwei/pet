package com.ttm.pet.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;
import java.util.*;

public class PayUtil {
	 /**  
     * 签名字符串  
     *  text需要签名的字符串
     * @param key 密钥  
     * input_charset编码格式
     * @return 签名结果  
     */   
    public static String sign(String text, String key, String input_charset) {   
        text = text + "&key=" + key;   
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }


    /**  
     * 签名字符串  
     * text需要签名的字符串
     * @param sign 签名结果  
     * key密钥
     * @param input_charset 编码格式  
     * @return 签名结果  
     */   
    public static boolean verify(String text, String sign, String key, String input_charset) {   
        text = text + key;   
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {   
            return true;   
        } else {   
            return false;   
        }   
    }   
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    private static boolean isValidChar(char ch) {   
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))   
            return true;   
        if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))   
            return true;// 简体中文汉字编码   
        return false;   
    }
    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map paraFilter(SortedMap<String,Object> sArray) {
        Map result = new HashMap();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = (String) sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    /**
     *requestUrl请求地址
     *requestMethod请求方法
     *outputStr参数
     */   
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){   
        // 创建SSLContext   
        StringBuffer buffer = null;   
        try{   
	        URL url = new URL(requestUrl);   
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
	        conn.setRequestMethod(requestMethod);   
	        conn.setDoOutput(true);   
	        conn.setDoInput(true);   
	        conn.connect();   
	        //往服务器端写内容   
	        if(null !=outputStr){   
	            OutputStream os =conn.getOutputStream();   
	            os.write(outputStr.getBytes("UTF-8"));   
	            os.close();   
	        }   
	        // 读取服务器端返回的内容   
	        InputStream is = conn.getInputStream();   
	        InputStreamReader isr = new InputStreamReader(is, "UTF-8");   
	        BufferedReader br = new BufferedReader(isr);   
	        buffer = new StringBuffer();   
	        String line = null;   
	        while ((line = br.readLine()) != null) {   
	        	buffer.append(line);   
	        }   
	        // 释放资源  
	        br.close();  
            isr.close();  
            is.close();  
            is = null;  
            conn.disconnect();  
        }catch(Exception e){   
            e.printStackTrace();   
        }
        return buffer.toString();
    }     
    public static String urlEncodeUTF8(String source){   
        String result=source;   
        try {   
            result=java.net.URLEncoder.encode(source, "UTF-8");   
        } catch (UnsupportedEncodingException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }   
        return result;   
    } 
    
  //请求方法  
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {  
          try {  
              URL url = new URL(requestUrl);  
              HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
              conn.setDoOutput(true);  
              conn.setDoInput(true);  
              conn.setUseCaches(false);  
              // 设置请求方式（GET/POST）  
              conn.setRequestMethod(requestMethod);  
              conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
              // 当outputStr不为null时向输出流写数据  
              if (null != outputStr) {  
                  OutputStream outputStream = conn.getOutputStream();  
                  // 注意编码格式  
                  outputStream.write(outputStr.getBytes("UTF-8"));  
                  outputStream.close();  
              }  
              // 从输入流读取返回内容  
              InputStream inputStream = conn.getInputStream();  
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
              String str = null;  
              StringBuffer buffer = new StringBuffer();  
              while ((str = bufferedReader.readLine()) != null) {  
                  buffer.append(str);  
              }  
              // 释放资源  
              bufferedReader.close();  
              inputStreamReader.close();  
              inputStream.close();  
              inputStream = null;  
              conn.disconnect();  
              return buffer.toString();  
          } catch (ConnectException ce) {  
              System.out.println("连接超时：{}"+ ce);  
          } catch (Exception e) {  
              System.out.println("https请求异常：{}"+ e);  
          }  
          return null;  
    }  
    
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
//	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,String> doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String,String> m = new HashMap<String,String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		Iterator it = root.getChildren().iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			if(e.getChildren().isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(e.getChildren());
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	
	/**
	 * 工具类方法 获取一定长度的随机字符串，范围0-9，a-z
	 * @param length：指定字符串长度
	 * @return 一定长度的随机字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 工具类方法 获取真实的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
    /**
     * 生成6位或10位随机数 param codeLength(多少位)
     * @return
     */
    public static String createCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 9);
        }
        return code;
    }

    //map转xml
    public static String getRequestXml(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
//            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
            if ("attach".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 获取有序map
     * @param map
     * @return
     */
    public static SortedMap<String,String> getSortedMap(Map<String,String> map){

        SortedMap<String, String> sortedMap = new TreeMap<>();
        Iterator<String> it =  map.keySet().iterator();
        while (it.hasNext()){
            String key  = (String)it.next();
            String value = map.get(key);
            String temp = "";
            if( null != value){
                temp = value.trim();
            }
            sortedMap.put(key,temp);
        }
        return sortedMap;
    }


    /**
     * 生成微信支付sign
     * @return
     */
    public static String createSign(SortedMap<String, String> params, String key){
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es =  params.entrySet();
        Iterator<Map.Entry<String,String>> it =  es.iterator();

        //生成 stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
        while (it.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k+"="+v+"&");
            }
        }

        sb.append("key=").append(key);
        String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(),"utf-8")).toUpperCase();
        return sign;
    }

    /**
     * 校验签名
     * @param params
     * @param key
     * @return
     */
    public static boolean isCorrectSign(SortedMap<String, String> params, String key){
        String sign = createSign(params,key);

        String weixinPaySign = params.get("sign").toUpperCase();

        return weixinPaySign.equals(sign);
    }

}

