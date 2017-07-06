package com.jinhan.simultion;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

public class TempTest {
  @Test
  public void f() throws IOException {
   String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "resouces/";

   String json;
    json = (FileUtils.readFileToString(new File(path+ "doConfirm.json"), "UTF-8"));
    System.out.println(replace(json));
  }
  
  private String replace(String src) {
   
   String pattern = "(\\$\\{(.*)\\})";
   // 创建 Pattern 对象
   Pattern r = Pattern.compile(pattern);
   // 现在创建 matcher 对象
   Matcher m = r.matcher(src);
   if (m.find()) {
    System.out.println(m.group(2) + "长度："+ m.groupCount());
    src = m.replaceFirst("642");
    System.out.println(src);
    return replace(src);
   }
   return src;
  }
    
}
