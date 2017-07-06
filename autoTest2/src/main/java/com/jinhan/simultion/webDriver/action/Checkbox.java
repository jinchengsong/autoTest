package com.jinhan.simultion.webDriver.action;

import java.util.List;

import org.openqa.selenium.WebElement;

public class Checkbox extends Handle{

 private String method;
 private int count;
 public String getMethod() {
  return method;
 }
 public void setMethod(String method) {
  this.method = method;
 }
 public int getCount() {
  return count;
 }
 public void setCount(int count) {
  this.count = count;
 }
 @Override
 public void doAction() {
  List<WebElement> checkBoxs = getMyWebDriver().getDriver().findElements(getCssSelector());
  log.debug("单选框个数：" + checkBoxs.size());
  if(checkBoxs.size()<=getCount())
    count = checkBoxs.size()-1;
   while(count> 0)
    checkBoxs.get(count--).click();

  
 }

}
