package com.web.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.net.ssl.HttpsURLConnection;
import com.web.util.weixinAbout.course.util.MyX509TrustManager;

/**
 * 常用公共方法
 * */
public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	

	/**
	 * 将异常堆栈的信息转成String
	 * @param t
	 * @return
	 */
	public static String printStackTraceToString(Throwable t) {
	    StringWriter sw = new StringWriter();
	    t.printStackTrace(new PrintWriter(sw, true));
	    return sw.getBuffer().toString();
	}
	
    /** 
     * 将json格式的字符串解析成Map对象 <li> 
     * json格式：{"name":"admin","retries":"3fff","testname" 
     * :"ddd","testretries":"fffffffff"} 
     */  
	public static HashMap<String, Object> jsonObjToHashMap(Object object) { 
        HashMap<String, Object> data = new HashMap<String, Object>();  
        // 将json字符串转换成jsonObject  
        JSONObject jsonObject = JSONObject.fromObject(object);  
        Iterator it = jsonObject.keys();  
        // 遍历jsonObject数据，添加到Map对象  
        while (it.hasNext())  
        {  
            String key = String.valueOf(it.next());  
            Object value = jsonObject.get(key);  
            data.put(key, value);  
        }  
        return data;  
    }  

	/**
	 * 本方法是将页面传递过来的参数quest.getParameter("")封装在MAP中，最好为LinkedHashMap的进行封装
	 * hashmap是没有排序的，而LinkedHashMap是按着放入参数的顺序组合出来的字符串
	 * @param map
	 * @return 返回组合好的参数拼串
	 */
	public static String getParam(Map<String,Object> map){
		
		LogUtil.getStart("CommonUtil.getParam", "开始拼装参数方法", "CommonUtil", "");
		StringBuffer sb = new StringBuffer();
		String param="";
	
			try {
				for (String key : map.keySet()) {
				   System.out.println("key= "+ key + " and value= " + map.get(key));
				   
				   sb.append(key);
				   sb.append("=");
				   sb.append( map.get(key));
				   sb.append("&");
				   
				 }
				if(!"".equals(sb.toString().trim()) && sb.toString().trim()!=null){
					 param = sb.toString().substring(0, sb.length()-1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			LogUtil.getEnd("CommonUtil.getParam", "结束拼装参数方法", "CommonUtil");
		return param;
		
	}
	
	/**
	 * @return (00000000+str)返回包括报文长度的字符串
	 * @param str action中返回的字符串
	 * @param len 表示JSON前面的8位的"报文长度"
	 * */
	public static String countRtnByte(String str, int len) {
		logger.debug(">>>>>>>>不包含00000000:" + str);
		byte[] byteStr = null;
		try {
			byteStr = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug("字符串不是UTF-8编码格式");
		}
		
		int length = byteStr.length;//报文体长度
		int lengthTotal = length + len;//报文总长度
		
		String add0Str = addStr(lengthTotal + "", len);
		return add0Str + str;
	}
	
	/**
	 * 读取Properties属性文件:
	 * fileName是以src为根目录的文件名称,例:src/config/deal/dealConfig.properties
	 * */
	public static Properties parseProperties(Class<?> inputClass, String fileName) {
		logger.debug("文件名称是:" + fileName);
		Properties props = new Properties();
		
		//method one
//		String path = inputClass.getClass().getResource("/").getPath().substring(1);
//		String filePath = path.substring(0, (path.length() - 4)) + fileName;
		
		//method two
		String filePath = System.getProperty("user.dir").toString() + fileName;
		
		logger.debug("文件路径是:" + filePath);
		
		try {
			props.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			logger.error("/CommonUtil/parseProperties 异常:" + e.getMessage());
		} catch (IOException e) {
			logger.error("/CommonUtil/parseProperties 异常:" + e.getMessage());
		}
		return props;
	}
	
	/**
	 * 补齐len位,不足左补0
	 * */
	public static String addStr(String str, int len) {
		String strRtn = "";
		for(int i = 0; i < (len - str.length()); i++) {
			strRtn += "0";
		}
		strRtn = strRtn + str;
		return strRtn;
	}
	
	/**
	 * 构建json字符串
	 * @param transCode 交易码
	 * @param sysuserid 操作人ID
	 * @param pageModel 报文体Model
	 * @return
	 */
	public static String constructJsonStr(String transCode, String sysuserid, 
			Map<String, Object> pageModel) throws Exception{
		// 系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String sysdate = sdf.format(new Date());
		
		pageModel.put("iface", transCode);
		pageModel.put("sysuserid", sysuserid);
		pageModel.put("sysdate", sysdate);
		
		return JsonUtil.getJsonStringFromMap(pageModel);
	}
	
	/**
	 * 调用核心接口
	 * @param jsonStr
	 * @return
	 */
/*	public static String getCoreValue(String jsonStr) throws Exception{
		TcpMinaClient tcpMinaClient = new TcpMinaClient();
		String rtnStr = tcpMinaClient.sendMessage(jsonStr);
		logger.debug(">>>>>>>responsemessage:" + rtnStr);
		
		rtnStr = rtnStr.substring(8, rtnStr.length());
		
		return rtnStr;
	}*/
	

//	/**
//	 * 把传入的对象转成json
//	 * */
//	public static String createJson(Object obj) throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.writeValueAsString(obj);
//	}
	
	/**
	 * 解析请求参数
	 **/
	public static Map<String, String> decryptParaToMap(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		try {
			//获取get参数
			String reqString = request.getQueryString();
			
			if (StringUtils.isNotBlank(reqString)) {
				String[] tmpArr = reqString.split("&");
				for(int i = 0; i < tmpArr.length; i++){
					String[] paramArr = tmpArr[i].split("=");
					if (paramArr.length == 1) {        // 参数值为空
						rtnMap.put(paramArr[0], "");
					} else if (paramArr.length == 2) {
						rtnMap.put(paramArr[0], paramArr[1]);
					} 
				}
			}
		} catch (Exception e) {
			logger.debug("请求参数解析异常：" + e.getMessage());
		}
		
		return rtnMap;
	}
	
	/**
	 * 解析请求参数
	 **/
	public static Map<String, String> decryptParamters(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		
		try {
			//获取get参数
			String reqString = request.getQueryString();
//			logger.debug(">>>>>>>>>>>>>>>>获取的参数字符串：" + reqString);
			
//			if (reqString == null) {
//				//获取post参数
//				InputStream is = request.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);   
//				BufferedReader br = new BufferedReader(isr);
//				// 解码POST提交的参数
//				reqString = java.net.URLDecoder.decode(br.readLine(),"UTF-8");
//			}
			
			if (StringUtils.isNotBlank(reqString)) {
				// 解密  Base64 + 3DES
				String  paramters = DES3Util.decode(reqString);

				// 特殊字符、中文汉字转码
				paramters = java.net.URLDecoder.decode(paramters, "UTF-8");
				
//				String paramters = reqString;
				
//				logger.debug("请求参数列表：");
				String[] tmpArr = paramters.split("&");
				for(int i = 0; i < tmpArr.length; i++){
//					logger.debug("*************" + tmpArr[i]);
					
					String[] paramArr = tmpArr[i].split("=");
					if (paramArr.length == 1) {        // 参数值为空
						rtnMap.put(paramArr[0], "");
					} else if (paramArr.length == 2) {
						rtnMap.put(paramArr[0], paramArr[1]);
					} else {
						
					}
				}
			}
		} catch (Exception e) {
			logger.debug("请求参数解析异常：" + e.getMessage());
		}
		
		return rtnMap;
	}
	
	
	/**
	 * 解析请求参数
	 **/
	public static Map<String, String> decryptParamtersTwo(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		
		try {
			//获取get参数
			String reqString = request.getQueryString();
//			logger.debug(">>>>>>>>>>>>>>>>获取的参数字符串：" + reqString);
			
//			if (reqString == null) {
//				//获取post参数
//				InputStream is = request.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);   
//				BufferedReader br = new BufferedReader(isr);
//				// 解码POST提交的参数
//				reqString = java.net.URLDecoder.decode(br.readLine(),"UTF-8");
//			}
			
			if (StringUtils.isNotBlank(reqString)) {
				// 解密  Base64 + 3DES
				System.out.println("加息券前解密string:"+reqString);
				logger.info("加息券前解密string:"+reqString);
				String  paramters = DES3Util.decode(reqString);

				// 特殊字符、中文汉字转码
//				paramters = java.net.URLDecoder.decode(paramters, "UTF-8");
				
//				String paramters = reqString;
				
//				logger.debug("请求参数列表：");
				String[] tmpArr = paramters.split("&");
				System.out.println("加息券后解密string:"+paramters);
				logger.info("加息券后解密string:"+paramters);
				for(int i = 0; i < tmpArr.length; i++){
//					logger.debug("*************" + tmpArr[i]);
					
					String[] paramArr = tmpArr[i].split("=");
					if (paramArr.length == 1) {        // 参数值为空
						rtnMap.put(paramArr[0], "");
					} else if (paramArr.length == 2) {
						rtnMap.put(paramArr[0], paramArr[1]);
					} else {
						
					}
				}
			}
		} catch (Exception e) {
			logger.debug("请求参数解析异常：" + e.getMessage());
		}
		
		return rtnMap;
	}
	
	/***
	 * 
	 * 解析参数
	 * 样式abc=12231&dfw=123131 转换成Map取值
	 * @param paramters
	 * @return
	 */
	public static  Map<String, String> decryptParam(String paramters) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String[] tmpArr = paramters.split("&");
		for(int i = 0; i < tmpArr.length; i++){
	//		logger.debug("*************" + tmpArr[i]);
			
			String[] paramArr = tmpArr[i].split("=");
			if (paramArr.length == 1) {        // 参数值为空
				rtnMap.put(paramArr[0], "");
			} else if (paramArr.length == 2) {
				rtnMap.put(paramArr[0], paramArr[1]);
			} else {
				
			}
		}
		return rtnMap;
	}
	/**
	 * json返回客户端(加密)
	 * @throws UnsupportedEncodingException 
	 **/
	public static void responseJson(Object result, HttpServletResponse response){
		String encodeStr = "";
		try {
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>返回结果加密前：" + result);
			// 特殊字符、中文汉字转码
			encodeStr = java.net.URLEncoder.encode(String.valueOf(result), "utf-8");
			// 加密返回json串
			encodeStr = DES3Util.encode(encodeStr);
			
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>返回结果加密后：" + encodeStr);
		} catch (Exception e) {
			logger.debug("返回结果加密异常：" + e.getMessage());
		}

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
//			out.print(result);
			out.print(encodeStr);
			out.flush();
			out.close();
		}catch(IOException e){
			logger.error("/CommonUtil/responseJson 异常:" + e.getMessage());
		}
	}
	
	/**
	 * json返回客户端(不加密)
	 * @throws UnsupportedEncodingException 
	 **/
	public static void responseJsonNotEncrypt(Object result, HttpServletResponse response){
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		}catch(IOException e){
			logger.error("/CommonUtil/responseJsonNotEncrypt 异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取随机验证码:随机产生的6位数字
	 */
	public static String getRandomCode() {
		// 获取验证码
		String randomCode = "";
		// 验证码字符个数
		int codeCount = 6;
		// 创建一个随机数生成器类
		Random random = new Random();
		// 随机产生codeCount数字的验证码
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字
			String strRand = String.valueOf(random.nextInt(10));
			randomCode += strRand;
		}
		return randomCode;
	}

	/**
	 * 生成订单号
	 * 
	 * 订单号格式:20140510(日期) + 1399540001193(当时系统的毫秒数)
	 * 
	 * */
	public static String createTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String formatStr = sdf.format(date);//20140508
		String dateStr = date.getTime() + "";//1399540001193
		String now = formatStr + dateStr;
		
		return now;
	}
	
	/**
	 * 设置返回rescode、resmsg
	 * 
	 * */
	public static String setResultInfo(Map<String, Object> map, 
			String rescode, String resmsg) {
		map.put("rescode", rescode);
		map.put("resmsg", resmsg);
		// 处理返回结果
		return JsonUtil.getJsonStringFromMap(map);
	}
	
	/**
	 * 设置返回rescode、resmsg、resmsg_cn
	 * 
	 * */
	public static String setResultStringCn(Map<String, Object> map, 
			String rescode, String resmsg,String resmsgCn) {
		map.put("rescode", rescode);
		map.put("resmsg", resmsg);
		map.put("resmsg_cn", resmsgCn);
		// 处理返回结果
		return JsonUtil.getJsonStringFromMap(map);
	}
	
	/**
	 * 金额判断大小
	 * 
	 * */
	public static int checkAmount(BigDecimal amount1, BigDecimal amount2){
		//-1表示小于，0是等于，1是大于
		return amount1.compareTo(amount2);
	}
	
	/**
	 * 年龄判断
	 * 
	 * */
	public static int checkAge(int compareObj1,int compareObj2){
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.YEAR, -compareObj2);
	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String str = sf.format(calendar.getTime());
        if(Integer.parseInt(str) == compareObj1){
        	return 0;//等于
        }else if(Integer.parseInt(str) > compareObj1){
        	return 1;//大于
        }else{
        	return -1;//小于
        }
	}
	/**
	 * 获取系统时间
	 * 
	 * */
	public static String getNow(String formatStr){
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        String str = sf.format(calendar.getTime());
        return str;
	}

	private static String getOracleSQLIn(List<String> ids, int count, String field) {
	    count = Math.min(count, 1000);
	    int len = ids.size();
	    int size = len % count;
	    if (size == 0) {
	        size = len / count;
	    } else {
	        size = (len / count) + 1;
	    }
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < size; i++) {
	        int fromIndex = i * count;
	        int toIndex = Math.min(fromIndex + count, len);
	        String productId = StringUtils.defaultIfEmpty(StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");
	        if (i != 0) {
	            builder.append(" or ");
	        }
	        builder.append(field).append(" in ('").append(productId).append("')");
	    }
	    
	    return StringUtils.defaultIfEmpty(builder.toString(), field + " in ('')");
	}
	
	/**
	 * 获取uuid
	 * 
	 * */
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString(); 
        // 去掉"-"符号   
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24); 
        return temp; 
	}
	
	/**
	 * 获取客户端真实IP
	 * @param args
	 * @throws Exception
	 */
	public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
           }
       }
       ip = request.getHeader("X-Real-IP");
       if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
          return ip;
      }
       return request.getRemoteAddr();
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostByHttp(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送Http的POST 请求出现异常！" + e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Map的数据转换成POST请求参数的格式。 name1=value1&name2=value2 的形式。
	 */
	public static String getHttpParameterFromMap(Map<String, String> map) {
		StringBuffer stringBuffer = new StringBuffer();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				stringBuffer.append(entry.getKey()).append("=")
						.append(entry.getValue())
						.append("&");

			} catch (Exception e) {
				logger.error("转换POST请求参数格式出现异常！" + e);
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		
		logger.debug("转换后的POST请求参数串：" + stringBuffer.toString());
		return stringBuffer.toString();
	}
	
	/**
	 * 
	 * 当参数是Map类型
	 * 发送http访问
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequestMap(String requestUrl, String requestMethod, String mapKeyValue) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			// 发送POST请求必须设置如下两行
			if("POST".equals(requestMethod)){
				URL realUrl = new URL(requestUrl);
				// 打开和URL之间的连接
				URLConnection conn = realUrl.openConnection();
				// 设置通用的请求属性
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.setRequestProperty("contentType", "UTF-8");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(mapKeyValue);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream(),"UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}else{
				String urlNameString = requestUrl + "?" + mapKeyValue;
				URL realUrl = new URL(urlNameString);
				// 打开和URL之间的连接
				URLConnection conn = realUrl.openConnection();
				// 设置通用的请求属性
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// 建立实际的连接
				conn.connect();
				// 获取所有响应头字段
				Map<String, List<String>> map = conn.getHeaderFields();
				// 遍历所有的响应头字段
				for (String key : map.keySet()) {
					System.out.println(key + "--->" + map.get(key));
				}
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 发送https请求
	 *  获取订单的meta响应信息
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 */
	public static String weixinOrderMetaData(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(null,requestUrl,new com.sun.net.ssl.internal.www.protocol.https.Handler());
//			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

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
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
		} catch (ConnectException ce) {
			logger.error("连接超时：{}", ce);
		} catch (Exception e) {
			logger.error("https请求异常：{}", e);
		}
		return buffer.toString();
	}
	
	

	/**
	 * 发送https请求
	 *  获取订单的meta响应信息
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 */
	public static String get_weixinOrderMetaData(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(null,requestUrl,new com.sun.net.ssl.internal.www.protocol.https.Handler());
//			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
//			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

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
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
		} catch (ConnectException ce) {
			logger.error("连接超时：{}", ce);
		} catch (Exception e) {
			logger.error("https请求异常：{}", e);
		}
		return buffer.toString();
	}
	
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 */
	public static String json_weixinOrderMetaData(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		// 使用POST方式向目的服务器发送请求  
    	StringBuffer data = new StringBuffer();  
		try {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				URL url = new URL(null,requestUrl,new com.sun.net.ssl.internal.www.protocol.https.Handler());
	//			URL url = new URL(requestUrl);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				
		    	conn.setRequestMethod("POST");  
		        conn.setDoOutput(true); 
		        conn.setRequestProperty("Content-Type", "application/json");
		         
		        OutputStreamWriter paramout = new OutputStreamWriter(  
		        		conn.getOutputStream(),"UTF-8");  
		        paramout.write(outputStr);  
		        paramout.flush();  
		  
		        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
		        String line;
		        while ((line = reader.readLine()) != null) {          
		            data.append(line);            
		        }  
		        paramout.close();  
		        reader.close();  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		    return data.toString();  
	}
	
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = Base64Util.decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * ouyy
	 * 设置金额格式
	 * @param context 金额
	 * @return
	 */
	public static String setFormatMoney(String context) throws Exception{
		StringBuffer sb=new StringBuffer();
		if(context.lastIndexOf(".")!=-1){
			String pdly=context.substring(context.lastIndexOf(".")+1);
			if(pdly!=null&&!"".equals(pdly.trim())){
				sb.append(context.substring(0,context.lastIndexOf(".")+1));
				if(pdly!=null&&pdly.length()==1){
					sb.append(pdly+"0");
				}else if(pdly.length()==2){
					sb.append(pdly);
				}else if(pdly.length()>=3){
					sb.append(pdly.substring(0,2));
				}
			}else{
				sb.append(context);
			}
			
		}else{
			sb.append(context+".00");
		}
		return sb.toString();
	}
    
	public static void main(String[] args) throws Exception{
//		List<String> ids = new ArrayList<String>();
//		ids.add("1");
//		ids.add("2");
//		ids.add("3");
//		ids.add("4");
//		ids.add("5");
//		
//		String sql = getOracleSQLIn(ids,ids.size(),"CREC.CREDIT_ACCT_CID");
//		System.out.print(sql);
		//getUuid();
		System.out.println(GetImageStr("D:\\9252150_190139450381_2.jpg"));
	}

	
	
}
