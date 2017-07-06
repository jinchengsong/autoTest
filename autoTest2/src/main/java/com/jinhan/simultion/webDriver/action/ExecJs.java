package com.jinhan.simultion.webDriver.action;

import org.openqa.selenium.JavascriptExecutor;

public class ExecJs extends Handle{

 private String value;
 
 public String getValue() {
  return value;
 }

 public void setValue(String value) {
  this.value = value;
 }

 @Override
 void doAction()  {
  log.info("js执行开始");
  JavascriptExecutor jsExce = (JavascriptExecutor) getMyWebDriver().getDriver();
  log.info("js为：" + getValue());
  jsExce.executeScript(getValue());
  log.info("js执行完毕");
 }

}
