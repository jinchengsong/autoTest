package com.jinhan.simultion.webDriver.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;

import com.google.gson.Gson;

public class Attribute extends Handle {

 private String method;
 private String attribute;
 private String name;
 private Integer count;
 private Boolean clear;
 
 
 public Boolean getClear() {
  return clear == null?true:false;
 }

 public void setClear(Boolean clear) {
  this.clear = clear;
 }

 public Integer getCount() {
  return count;
 }

 public void setCount(Integer count) {
  this.count = count;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getMethod() {
  return method == null ? "get" : method;
 }

 public void setMethod(String method) {
  this.method = method;
 }

 public String getAttribute() {
  return attribute;
 }

 public void setAttribute(String attribute) {
  this.attribute = attribute;
 }

 @Override
 public void doAction() {

  if ("get".equalsIgnoreCase(getMethod())) {
   getAttributValue();
  } else {
   // 设置元素的属性，虽然不知道有啥用
   // 未实现
  }

 }

 // 有点问题，不能准确的获取
 private void getAttributValue() {
  String data = "";
  Data src = new Data();
  List<WebElement> eles = getMyWebDriver().getDriver().findElements(getCssSelector());
  List<String> dataList = new ArrayList<String>();
  int i = getCount() == null ? 1 : getCount();
  for (WebElement ele : eles) {
   log.info("属性为：" + getAttribute());
   if ("text".equalsIgnoreCase(getAttribute()))
    data = ele.getText();
   else
    data = ele.getAttribute(getAttribute());
   log.info("获取的属性值为： " + data);
   dataList.add(data);
   i--;
   if (i == 0)
    break;
  }
  src.setName(getName() == null ? getCssSelectorValue() : getName());
  
  if(!getClear()){
   if(null != (List<String>) getMyWebDriver().getPersistentData().get(src.getName())){
    List<String> old = (List<String>) getMyWebDriver().getPersistentData().get(src.getName());
    dataList.addAll(old);
   }
  }
   
  src.setValue(dataList);
  Gson gson = new Gson();
  try {
   getMyWebDriver().getPersistentData().put(src.getName(), src.getValue());
   FileUtils.writeStringToFile(new File("data.json"), gson.toJson(src), true);
  } catch (IOException e) {
   log.info("写入持久化文件失败！\r\n" + e.getMessage());
  }
 }

}
