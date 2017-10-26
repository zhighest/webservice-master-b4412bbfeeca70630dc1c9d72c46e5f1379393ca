package com.web.util.rsaO2O;

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
import java.util.Map;

import javax.crypto.Cipher;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.util.JsonUtil;


/**
 *
 * <p>RSA签名,加解密处理核心文件，注意：密钥长度1024</p>
 */
public class RSAPublicPrivateLB {
	private static final Log logger = LogFactory.getLog(RSAPublicPrivateLB.class);

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
    private static final String PUBLIC_KEY          = "RSAO2OPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY         = "RSAO2OPrivateKey";

    /**
     * 获取公钥的key RSA加密串
     */
    private static final String PUBLIC_KEY_RSA          = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW/ES8biuc5jJcktvg5j0IpBUphPNo8ouD4/9qlbuuRZZ1MF5qiTgvueVAHZJ6W4uKL5YftoLXhhTLLPSC8ydsZrD4TlE4cjMxznKJdWtOnEs3MWNRIjxUK0Mi6I8XH8OEuaODONWNXrXF+JiCp/5y9xwPXJAfiNoeCyNHcSxmQQIDAQAB";

    /**
     * 获取私钥的key RSA加密串
     */
    private static final String PRIVATE_KEY_RSA         = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJb8RLxuK5zmMlyS2+DmPQikFSmE82jyi4Pj/2qVu65FlnUwXmqJOC+55UAdknpbi4ovlh+2gteGFMss9ILzJ2xmsPhOUThyMzHOcol1a06cSzcxY1EiPFQrQyLojxcfw4S5o4M41Y1etcX4mIKn/nL3HA9ckB+I2h4LI0dxLGZBAgMBAAECgYAL7nWUGnSx2nMiY6yiki9CjozpkgsWQQyLaKoXhyuksvUX37GmY5+gDeWy8mNP7goR6n7HlMm0NHFSOQQ9kO+NfxAm0Rwe2zXy6iDr2WQxh4EXWCbN1X4QrDiJTMnI0hgc4JY/10c2vB+y8d7yRUoHAXx9nzvis7mXP0kbP16eEQJBAOLDAxMvWkkBCR6KGa7hbc5CLAFSyPaUw0wtvFBZ+9wmkLf1cTWD0tfjuxCfS8WPcFa7FPU2Pzl28GZ3VaR7ve0CQQCqdATjMt+goR+8hVRs0gBB6z6h/oo7YN9NpigZN3L1qicImA19HO+ycjGU/Dj/lTSTQVZbnGmloQRf/tAdK18lAkAtjTkX6II9egzH6rwACl7ReS930JZ4GiglLGbQyVKzxbd6jL6CHooDPkb8rtM8y3Sop4otPAl1VLQ0ETLOCOF9AkAD6Ls/9qe4SFmw+iHQiRRmbp22G19SM0uLvVbIFUR2q9tPUCVnDCXXItVORGaOPf7BrcrsOO8XVFUEWpStRpRdAkAsKbwyu4kjNFS+IXs2J204iKkjrx5f0v6HqhNl7amUuQmTaYaZNiV9P+jcDJfn4i/qV9sPVMdfMeN0nS0WZK3y";

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

        String base64publicKey = getPublicKey(genKeyPair);
        System.out.println("公钥 \n" + base64publicKey);
        String base64privateKey = getPrivateKey(genKeyPair);
        System.out.println("私钥\n" + base64privateKey);

//	      String base64publicKey = PUBLIC_KEY_RSA;
//	      System.out.println("公钥 \n" + base64publicKey);
//	      String base64privateKey = PRIVATE_KEY_RSA;
//	      System.out.println("私钥\n" + base64privateKey);
          
          Map map = new HashMap();
          map.put("username", "王恩来");
          map.put("password", "password");
          
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
    	logger.info("加密前的数据：" + data);
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
