package com.web.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;



/**
 *
 * <p>RSA签名,加解密处理核心文件，注意：密钥长度1024</p>
 */
public class RSA {

    /**
     * 签名算法
     */
    public static final String  SIGNATURE_ALGORITHM = "SHA1withRSA";
    /**
     * 加密算法RSA
     */
    public static final String  KEY_ALGORITHM       = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int    MAX_ENCRYPT_BLOCK   = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int    MAX_DECRYPT_BLOCK   = 128;

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY          = "RSAThirdPartyPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY         = "RSAThirdPartyPrivateKey";

    /**
     * 获取公钥的key RSA加密串
     */
    private static final String PUBLIC_KEY_RSA          = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhEsAWxChMWYMr+82J7ajClGHixeKkT6Fxsg5Q1WONS0WS87IAEgXGI9sBbXJgeCBoaDKXnd+s0+c3xE1CJOwIYmNhgDlIziLZTzZBdK66pwnqIKgJjRkDX+gt42yNrzxCvEIHFeC5cH9x63cqwxcgmE4G1u0u+PcE31PKa6egnwIDAQAB";

    /**
     * 获取私钥的key RSA加密串
     */
    private static final String PRIVATE_KEY_RSA         = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOESwBbEKExZgyv7zYntqMKUYeLF4qRPoXGyDlDVY41LRZLzsgASBcYj2wFtcmB4IGhoMped36zT5zfETUIk7AhiY2GAOUjOItlPNkF0rrqnCeogqAmNGQNf6C3jbI2vPEK8QgcV4Llwf3HrdyrDFyCYTgbW7S749wTfU8prp6CfAgMBAAECgYAhd5lKiVZ4q+K+Wo3gkY9Dh67aepRN3G1kK5bHXEOWUfgBdu0j54omQVSibLTSNWqUO9IyC06kM0oxV6YpTLYv7Ybs0yLTDzElUrLDVFrSqtOAk1472wQXRQ0AbDinUtjoqy0GHAy5QZ8rro4QJJkDYj0QBqCHyjn0FQVDb1q48QJBAPoorLnJZnx6dj51jv7hTwWiz9Q/iSP8L0keAtW6kabIW6/r7wZYCOn6tb9kxfUfTOEWztcXAW4dksFEfPbVeQcCQQDmVCBL0YYp7qvxM0kdZnfa7ejBwsJQrtrxFdbaSHhoYl3xhdhasksCctZYG1wXhQcyst04A2dXrrJSWqHzR62pAkEA2MpFHeCu43NRY8GHQdVKIS0+hMbm93uGEwxiEozTysDZzHRfuZSzbOordYlEkLAmdO7KI9a5E4+nbbwHu/W2IwJALjDa1TmstUYXF2UYmZvOF4q0rf735yRZ5hViJB56fzyS6HfWHsI4xsHu+HZsLo9QFTvHn48XL39qZZeleNt8GQJAMUgDJtVu3MhJKGs+2697CkWRbu+HJX4ZKXW98hxtOJJehs7PeYSEDsjBR/OcY4WL0u8sYm6KrUJ99dCS/EvC0A==";

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;

    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> genKeyPair = genKeyPair();

//        String base64publicKey = getPublicKey(genKeyPair);
//        System.out.println("公钥 \n" + base64publicKey);
//        String base64privateKey = getPrivateKey(genKeyPair);
//        System.out.println("私钥\n" + base64privateKey);

//	      String base64publicKey = PUBLIC_KEY_RSA;
//	      System.out.println("公钥 \n" + base64publicKey);
//	      String base64privateKey = PRIVATE_KEY_RSA;
//	      System.out.println("私钥\n" + base64privateKey);
          
          Map map = new HashMap();
          map.put("username", "");
          
          String encrypt_key = getPublick_Key_Encrypt(map);
          System.out.println(encrypt_key);
          
          Map mapTwo = getPrivate_Key_Deciphering(encrypt_key);
          System.out.println(mapTwo.get("username"));
    }
    
    /***
     * @author wangenlai
     * 加密参数，获取公钥RSA加密后的串
     * @return
     */
    public static String getPublick_Key_Encrypt(Map<String,String> map) throws Exception {
    	JSONObject jsonObject = JSONObject.fromObject(map);
    	String data = new String(jsonObject.toString());
    	String base64publicKey = PUBLIC_KEY_RSA;
    	String charsetName = "utf-8";
    	String encryptByPublicKey = Base64.encodeBase64String((encryptByPublicKey(
    			data.getBytes(charsetName), base64publicKey)));
        System.out.println("加密\n" + encryptByPublicKey);
        return encryptByPublicKey;
    }
    
    /***
     * @author wangenlai
     * 解密参数，获取私钥解密后的key,value值
     * @return
     */
    public static Map<String,String> getPrivate_Key_Deciphering(String encryptByPublicKey) throws Exception {
    	String base64privateKey = PRIVATE_KEY_RSA;
    	byte[] decryptByPrivateKey = decryptByPrivateKey(Base64.decodeBase64(encryptByPublicKey),
	            base64privateKey);
        String params = new String(decryptByPrivateKey, "utf-8");
        System.out.println("解密后\n" + params);
        Map<String,String> resultMapForBuyPlan = (HashMap<String,String>)JsonUtil.getObjectFromJsonString(params, HashMap.class);
        return resultMapForBuyPlan; 
    }

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param privateKey 私钥(BASE64编码)
     *
     * @param charset
     *            编码格式
     * @return 签名结果(BASE64编码)
     */
    public static String sign(String text, String privateKey, String charset) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(getContentBytes(text, charset));
        byte[] result = signature.sign();
        return Base64.encodeBase64String(result);

    }

    public static String sign(String text, PrivateKey privateKey, String charset)
                                                                                 throws SignatureException,
                                                                                 InvalidKeyException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(getContentBytes(text, charset));
            byte[] result = signature.sign();
            return Base64.encodeBase64String(result);
        } catch (NoSuchAlgorithmException e) {
            //不可能发生，
            return null;
        }
    }

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param sign
     *            客户签名结果
     * @param publicKey
     *            公钥(BASE64编码)
     * @param charset
     *            编码格式
     * @return 验签结果
     */
    public static boolean verify(String text, String sign, String publicKey, String charset)
                                                                                            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(getContentBytes(text, charset));
        return signature.verify(Base64.decodeBase64(sign));

    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
                                                                                     throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
                                                                                   throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param cert 证书
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, Certificate cert) throws Exception {

        // 对数据加密
        PublicKey uk = cert.getPublicKey();
        Cipher cipher = Cipher.getInstance(uk.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, uk);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }
}
