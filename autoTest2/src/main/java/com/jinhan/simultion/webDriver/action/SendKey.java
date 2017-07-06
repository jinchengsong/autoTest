package com.jinhan.simultion.webDriver.action;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class SendKey extends Handle{

 private Boolean focus;
 private String value;
 private Integer count;


 public Integer getCount() {
  return count == null?0:count;
 }
 public void setCount(Integer count) {
  this.count = count;
 }
 public Boolean getFocus() {
  return focus==null?false:true;
 }
 public void setFocus(Boolean focus) {
  this.focus = focus;
 }
 public String getValue() {
  return value;
 }
 public void setValue(String value) {
  System.out.println(value);
  this.value = value;
 }
 @Override
 public void doAction() {
  String[] values = getValue().split(" ");
  Actions actions = new Actions(getMyWebDriver().getDriver());
  int i = 0;
  for(String value : values){
   if(i != getCount())
    break;
   actions.moveToElement(getMyWebDriver().getDriver().findElement(getCssSelector()));
   actions.click();
   log.info("输入的值为：" + value);
   actions.sendKeys(value);
   actions.click();
   if(!getFocus()){
    try {
       Thread.sleep(1000);
     actions.click(getMyWebDriver().getDriver().findElement(By.tagName("body")));
    } catch (Exception e) {
     log.info("body 不可点击");
    }
   }
   actions.build().perform();  
   i++;
   setCount(i);
  }

  
 }

}
