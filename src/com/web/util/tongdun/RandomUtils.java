package com.web.util.tongdun;

import java.util.Random;

public class RandomUtils {
	public static final String allChar = 

			"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

			  public static final String letterChar = 

			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

			  public static final String numberChar = "0123456789";    

			  public static String generateString(int length) { 

			  StringBuffer sb = new StringBuffer(); 

			  Random random = new Random(); 

			  for (int i = 0; i < length; i++) { 

			  sb.append(allChar.charAt(random.nextInt(allChar.length()))); 

			  } 

			  return sb.toString(); 

			  } 

			   

			  public static String generateMixString(int length) { 

			  StringBuffer sb = new StringBuffer(); 

			  Random random = new Random(); 

			  for (int i = 0; i < length; i++) { 

			  sb.append(allChar.charAt(random.nextInt(letterChar.length()))); 

			  } 

			  return sb.toString(); 

			  } 

			     public static String generateLowerString(int length) { 

			  return generateMixString(length).toLowerCase(); 

			  } 

			   

			  public static String generateUpperString(int length) { 

			  return generateMixString(length).toUpperCase(); 

			  } 

			   

			  public static String generateZeroString(int length) { 

			  StringBuffer sb = new StringBuffer(); 

			  for (int i = 0; i < length; i++) { 

			  sb.append('0'); 

			  } 

			  return sb.toString(); 

			  } 

			   

			  public static String toFixdLengthString(long num, int fixdlenth) { 

			  StringBuffer sb = new StringBuffer(); 

			  String strNum = String.valueOf(num);   if (fixdlenth - strNum.length() >= 0) { 

			  sb.append(generateZeroString(fixdlenth - strNum.length())); 

			  } else { 

			  throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!"); 

			  } 

			  sb.append(strNum); 

			  return sb.toString(); 

			  } 

			   

			  public static String toFixdLengthString(int num, int fixdlenth) { 

			  StringBuffer sb = new StringBuffer(); 

			  String strNum = String.valueOf(num); 

			  if (fixdlenth - strNum.length() >= 0) { 

			  sb.append(generateZeroString(fixdlenth - strNum.length())); 

			  } else { 

			  throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!"); 

			  }   sb.append(strNum); 

			  return sb.toString(); 

			  } 

			  public static void main(String[] args) { 

			  System.out.println(generateString(15)); 

			  System.out.println(generateMixString(15)); 

			  System.out.println(generateLowerString(15)); 

			  System.out.println(generateUpperString(30)); 

			  System.out.println(generateZeroString(15)); 

			  System.out.println(toFixdLengthString(123, 15)); 

			  System.out.println(toFixdLengthString(123L, 15)); 

			  } 
}
