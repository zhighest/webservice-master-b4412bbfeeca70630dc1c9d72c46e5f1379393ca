package com.web.util;

import java.io.IOException;
import java.security.Key;  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
  
import javax.crypto.Cipher;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESedeKeySpec;  
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.support.PropertiesLoaderUtils;
 
/** 
 * 3DES加密工具类 
 */  
public class DES3Util { 
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";
	// 密钥
    private static String secretKey = "";
    // 电商密钥
    private static String secretKeyShop = "";
    // 向量
    private static String iv = "";  
    // 电商向量
    private static String ivShop = ""; 
    
    // 网站微信Cookie密钥
    private static String secretKeyCookie = "";
    // 电商密钥
    private static String ivCookie = "";
    
    static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("DES3Key.properties");
			secretKey = properties.getProperty("SECRET_KEY");
			iv = properties.getProperty("IV");
			secretKeyShop = properties.getProperty("SECRET_KEY_SHOP");
			ivShop = properties.getProperty("IV_SHOP");
			secretKeyCookie = properties.getProperty("SECRET_KEY_COOKIE");
			ivCookie = properties.getProperty("IV_COOKIE");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
  
    /** 
     * 3DES加密 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encode(String plainText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decode(String encryptText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
  
        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));  
  
        return new String(decryptData, encoding);  
    }
    
    /** 
     * 3DES加密（电商） 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encodeShop(String plainText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKeyShop.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(ivShop.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 （电商） 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decodeShop(String encryptText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKeyShop.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(ivShop.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
  
        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));  
  
        return new String(decryptData, encoding);  
    }
    
    /** 
     * 3DES加密 Cookie 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encodeCookie(String plainText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKeyCookie.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(ivCookie.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 Cookie
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decodeCookie(String encryptText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKeyCookie.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(ivCookie.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));  
        return new String(decryptData, encoding);  
    }
    
    public static void main(String[] args) {
    	try {
    		String enStr = "/wxabout/goIndex";
        	System.out.println(">>>>>>>>加密前：" + enStr);
        	
			String result = encode(enStr);
			System.out.println(">>>>>>>>加密后：" + result);
			
			String src = "G5qkPJQaQZ8kUnepfYqFU+FtA8IO8nUllSrX6I9ysIi8oOXu0l8Ok7l8PwxDdf6A/KpZ0MGPrB3neNv1FmjObg1IJb6oGpmfUOO3mDU7yLyl6Y7efk06qRrJ36EzBzXC";
			System.out.println(">>>>>>>>解密前：" + src);
			
			
			System.out.println(">>>>>>>>解密后：" + java.net.URLDecoder.decode(decode(src),"UTF-8"));
			
			///wxTrigger/getWxCode?actionScope=iPVttW0rMhj8uo7dNyfODguFy45k3Cup
			// wxTrigger/getWxCode?actionScope=iPVttW0rMhj8uo7dNyfODguFy45k3Cup
			//------------------------------------------------------以上加密解密
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(new Date());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("shareMobile", "18552184832");
			map.put("shareDate", date);
			String shareMobileDateJM = CommonUtil.getParam(map);
			try {
				shareMobileDateJM = DES3Util.encode(shareMobileDateJM);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(shareMobileDateJM);
			
			String goUrl="";
			try {
				goUrl = Base64.encodeBase64URLSafeString(("/wxuser/lootShareIncreaseInterest?shareMobileDateJM="+shareMobileDateJM+"&lootMobile=13601051150").getBytes("UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("***********************************http://wangenlai2010.vicp.cc/webservice/wxTrigger/getWxCode?actionScope="+goUrl);
			
			//以上加息-------------------------------------------------
			
			
			
			Map<String, Object> couponsMapAdd = new LinkedHashMap<String, Object>();
			couponsMapAdd.put("type", "4");//1.新用户注册，2.用户每日分享，3.被他人查看分享，4.查看他人分享链接，5、活期收益满五元，自动生成加息
			couponsMapAdd.put("mobile","13344444444");
			couponsMapAdd.put("userId", "");
			couponsMapAdd.put("shareMobile", "18552184832");
			couponsMapAdd.put("mediaUid", "oTHApxLxSAZDTkmsNgrKlhWXv4UY");
			
			
			//封装加密请求数据
			String couponsAddParam = CommonUtil.getParam(couponsMapAdd);
			try {
				couponsAddParam = DES3Util.encode(couponsAddParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("********************"+couponsAddParam);
			
			
			
			Map mapz = new HashMap();
			mapz.put("mediaUid", "on65vuFolgpVwG-xKQImd2aoOkjQ");
			mapz.put("functionScope", "increateInterest");
			mapz.put("increateInterestRateInfo", "你好");
			
			String increateInterestRateInfo = CommonUtil.getParam(mapz);
			try {
				increateInterestRateInfo = DES3Util.encode(increateInterestRateInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("(*************adfdjsk***********"+increateInterestRateInfo);
			
			//ct4ty1x+d/i5SCDnE0fhMNy6+zrMrkRcLPKShs/mb6rVn6vYWdWprLSrF8g17v6eqRAendX+pmt5Qoc9Q2KHEjDbvqCctzkcGyTE2p5f04ZOlqsFsUOzUIm8w0aEi7EKiBWqLAS82vw=
			//String src1 = "MtTlHDHVkyLDnelUnF6MPiz5Uq89Kl74wboE9ulusCaDm12cHhgEWokGHLR5qefs7wu0O6aAOMdaC2Q0oziW7FpePmhXHSrZTxzr9+MONG3ig4pz+zyaP38SMg+qXKWjrpX34Yazc2vVQsVKrTZBMzPSNAciuztzFfzaRB1xlBGp9axY/3+MNh5thMNI1gv8Z5OT7antz/1VbL2ou5x5LZN2YWLv+CgoIl0Wbs4NC4EVLsqlMukUF02Tpe2Pa3sNWzyVayHH+a2WPpctVmSmcVpq6RKIJGsiZdfViMW0docoo+p/j6pmRJQM/covlruGVsGmi9HxCtG5lNkhM3jhy9QfMj48Lb0QTQ3lsREqPRRh/KrottXmYk/vkuiQW/jyd/EQ2+kmW4WJipdJY/f4sKrj29Rjga7yH0JILMGL05oQlRpV1aY42BDjbTvPOb/FKrkxHx4MV55aTZYJaFIBFPoLI3RnC8gfJvDz+DFeNdv8lOhWpCQuEceXyMCkj5SL2E5r5nlobfc2wEsuk0dNzPueMEWS5r//UMRU4y/pbZc0qgMU8wkInsGwWFEv/S0lMgzhxBE4AEQg+o9VW2KL3p1fcT+UeAgEev2RQlDYqA1y+IPc/hd3L4TlTCuk2fuCt2JGPkc9/11g5T6ILyGrc1HB1x7q3kQoxoOHeXkYXJiDIHUiMIyecWvL69zQ1GNeNCCk7FweFMb3TKBQAoVgfjXu4LRN8GNypr10Q/MRJYjQmOVqXDzPlOTEdGx9IMWxmNPe65Q+oPiQYYZYxpz9nhX1LL59F+L60Biuo9HHzwK6V+kmfrujSSQdSZslSn6uX7LZiya6rwo=";
			//System.out.println(">>>>>>>>解密前：" + src1);
			
			//System.out.println(">>>>>>>>解密后：" + java.net.URLDecoder.decode(decode(src1),"UTF-8"));
			
			//System.out.println(">>>>>>>>解密后：" + decode("NWROvkiGvh4tHUH6XVXL5N=="));
		} catch (Exception e) {
		}
    }

}