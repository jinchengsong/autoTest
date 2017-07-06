package com.jinhan.simultion.webDriver.action;

import org.openqa.selenium.By;

public class Click extends Handle{

 @Override
 public void doAction() {
  try{
   while(true){
    Thread.sleep(100);
    getMyWebDriver().getDriver().findElement(By.className("loading"));
    System.out.println("出现等待");
   }
  }catch (Exception e) {
   System.out.println("没有等待");
  }
  getMyWebDriver().getDriver().findElement(getCssSelector()).click();
  
 }

}
