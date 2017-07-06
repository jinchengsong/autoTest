package com.jinhan.simultion.webDriver.action;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.jinhan.simultion.webDriver.MyWebDrive;
import com.jinhan.simultion.webDriver.utils.StringUtil;

public abstract class Handle {
  
 private String action;
 private String cssSelector;
 private MyWebDrive myWebDriver;

 protected transient  Logger log;

 public Handle() {
  log = Logger.getLogger(getClass());
 }
 public MyWebDrive getMyWebDriver() {
  return myWebDriver;
 }

 public void setMyWebDriver(MyWebDrive myWebDriver) {
  this.myWebDriver = myWebDriver;
 }

 public By getCssSelector() {

  return By.cssSelector(this.cssSelector);
 }
 
 public String getCssSelectorValue(){
  
  return this.cssSelector;
 }

 public void setCssSelector(String cssSelector) {
  this.cssSelector = cssSelector;
 }

 public String getAction() {
  return action;
 }

 public void setAction(String action) {

  this.action = getClass().getPackage().getName() + "." + StringUtil.upperFirstChar(action);
 }

 public void handing() throws Exception {
  //添加判断元素是否存在的代码
   
  if(getCssSelectorValue() != null){

   while(true){
    Thread.sleep(100);
    try {
     myWebDriver.getDriver().findElement(getCssSelector());
     System.out.println("指定元素出现");
     break;
    } catch (Exception e) {
     System.out.println("指定元素未出现：" + e.getMessage());
    }

   }

  try{
   while(true){
    Thread.sleep(100);
    myWebDriver.getDriver().findElement(By.id("pageloading"));
    System.out.println("出现等待蒙层");
   }
  }catch (Exception e) {
   System.out.println("没有等待蒙层");
  }
  try{
   while(true){
    Thread.sleep(100);
    myWebDriver.getDriver().findElement(By.className("window-mask"));
    System.out.println("出现蒙层");
   }
  }catch (Exception e) {
   System.out.println("没有蒙层");
  }
  }
  doAction();
 }

 abstract void doAction() throws NumberFormatException, InterruptedException;

}
