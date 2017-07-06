package com.jinhan.simultion.webDriver.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

 /**
  * 首字母大写
  * @param str
  * @return
  */
 public static String upperFirstChar(String str) {  

  char[] ch = str.toCharArray();  
  if (ch[0] >= 'a' && ch[0] <= 'z') {  
      ch[0] = (char) (ch[0] - 32);  
  }  
  return new String(ch);  
}  
 
 public static String replace(String src,HashMap persistentData) {
  
  String pattern = "(\\$\\{(.*)\\})";
  // 创建 Pattern 对象
  Pattern r = Pattern.compile(pattern);
  // 现在创建 matcher 对象
  Matcher m = r.matcher(src);
  if (m.find()) {
   src = m.replaceFirst(List2StrWithSpace(persistentData.get(m.group(2))));
   return replace(src,persistentData);
  }
  return src;
 }
 
 /**
  * 将list转化为以空格分隔的字符串
  * @param src
  * @return
  */
 public static String List2StrWithSpace(Object src){
  if(src instanceof ArrayList){
   return StringUtils.join((ArrayList)src, " ");
  }
  return "";
 }
}
