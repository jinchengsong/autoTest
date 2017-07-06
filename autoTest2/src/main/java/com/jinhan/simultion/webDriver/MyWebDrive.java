package com.jinhan.simultion.webDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jinhan.consts.ConfigConts;
import com.jinhan.consts.JsCommandEum;
import com.jinhan.simultion.webDriver.action.Handle;
import com.jinhan.simultion.webDriver.utils.StringUtil;

public class MyWebDrive {

 protected ChromeDriverService service;
 protected WebDriver driver;
 /**持久化的数据*/
 protected Properties properties;
 protected static Logger log;

 private HashMap persistentData;
 
 public HashMap<String,Object> getPersistentData() {
  return persistentData;
 }

 public void setPersistentData(HashMap persistentData) {
  this.persistentData = persistentData;
 }

 public Properties getProperties() {
  return properties;
 }

 public MyWebDrive() throws IOException {
  initConfig();
  service = new ChromeDriverService.Builder()
    .usingDriverExecutable(new File(properties.getProperty(ConfigConts.CHROME_DIRVER_PATH))).usingAnyFreePort().build();
  service.start();
  log.info("本地服务地址：" + service.getUrl());
  // 创建一个 Chrome 的浏览器实例
  driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
  driver.manage().window().maximize();
  persistentData = new HashMap<String,Object>();
 }

 /**
  * 初始化配置文件
  * 
  * @throws IOException
  */
 private void initConfig() throws IOException {
  String customizedPath = "resouces/log4j.properties";
  System.setProperty("log4j.configuration", customizedPath);
  log = Logger.getLogger(getClass());
  log.debug(Thread.currentThread().getContextClassLoader().getResource("").getPath());
  Properties props = new Properties();
  FileInputStream istream = null;
  String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "resouces/";
  try {
   istream = new FileInputStream(path + "config.properties");
   props.load(istream);
   istream.close();
  } finally {
   if (istream != null) {
    try {
     istream.close();
    } catch (InterruptedIOException ignore) {
     Thread.currentThread().interrupt();
    } catch (Throwable ignore) {
    }
   }
  }
  this.properties = props;
 }

 /**
  * 对单个页面进行仿真测试
  * 
  * @param fileName
  * @throws Exception
  */
 public void doPageSimultion(String fileName) throws Exception {
  // 解析json中的数据
  log.info("开始：");
  Gson gson = new GsonBuilder().create();
  Target target = gson.fromJson(replaceJsonTemplate(fileName), Target.class);
  log.info("json对象转换完毕");
  log.info("当前目标： " + target.getUrl());
  this.driver.get(target.getUrl());
  // 执行设定的操作
  List<Handle> handles = getAction(target.getHandles());
  for (Handle handle : handles) {
   try {
    handle.handing();
   } catch (UnhandledAlertException e) {
    Thread.sleep(5000);
   }
   
  }
  log.info("结束：");

 }

 
 /**
  * 替换json字符串中的模板
  * @param src
  * @return 
  */
 private String replaceJsonTemplate(String fileName){
  String json = "";
  try {
   json = (FileUtils.readFileToString(new File(fileName), "UTF-8"));
   json = StringUtil.replace(json, getPersistentData());
   if(log.isDebugEnabled())
    log.debug(json);
  log.info("加载文件完毕");
  } catch (IOException e) {
   log.info("读取本地json文件出错！" + e.getMessage());
   e.printStackTrace();
   return null;
  }

  return json;
 }
 
 public void injectJs(JsCommandEum js) {
  JavascriptExecutor jsExec = (JavascriptExecutor) driver;
  // 修改浏览器自带的confirm
    jsExec.executeScript(js.getCommand());
 }

 public void stop() throws InterruptedException {

  log.info("正在结束");
  JavascriptExecutor jsexec = (JavascriptExecutor) getDriver();
  Thread.sleep(5000);
  log.info("执行关闭确认弹窗");
  Boolean result = WebConfirm("模拟结束，是否关闭浏览器？");
  if (result) {
   driver.close();
   service.stop();
   log.info("关闭浏览器");
  } else {
   log.info("未关闭浏览器");
  }
  

 }

 private List<Handle> getAction(JsonArray handles) throws Exception {
  log.info("开始读取操作信息");
  List<Handle> result = new ArrayList<Handle>();
  Iterator<JsonElement> it = handles.iterator();
  JsonObject jso = null;
  Handle handle = null;
  String action = "";
  while (it.hasNext()) {
   jso = it.next().getAsJsonObject();
   log.info("原始json对象："+jso);
   action = "com.jinhan.simultion.webDriver.action." + StringUtil.upperFirstChar(jso.get("action").getAsString());
   log.info("当前的操作为：" + action);
   handle = (Handle) new GsonBuilder().create().fromJson(jso, Class.forName(action));
   handle.setMyWebDriver(this);
   result.add(handle);
  }
  log.info("结束读取");
  return result;
 }

 public WebDriver getDriver() {

  return this.driver;
 }

 public Boolean WebConfirm(String msg) throws InterruptedException {

  injectJs(JsCommandEum.CONFIRM);
  JavascriptExecutor jsexec = (JavascriptExecutor) getDriver();
  jsexec.executeScript("return confirm('" + msg + "');");
  log.info("弹窗操作结束");
  Boolean result = null;
  while (result == null) {
   Thread.sleep(1000);
   try {
    result = (Boolean) jsexec.executeScript("return window.operateState;");
   } catch (UnhandledAlertException e) {
    log.info("等待用户操作结果...");
   }

  }
  log.info("用户操作结束");
  return result;
 }
 
 public Boolean hasTaAlert() throws Exception{
  injectJs(JsCommandEum.TA_ALERT);
  JavascriptExecutor jsexec = (JavascriptExecutor) getDriver();
  Boolean result = (Boolean) jsexec.executeScript("return window.operateState;");
  return result;
 }

}
