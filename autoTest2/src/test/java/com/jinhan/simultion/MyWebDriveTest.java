package com.jinhan.simultion;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jinhan.simultion.webDriver.MyWebDrive;
import com.jinhan.simultion.webDriver.action.ExecJs;

public class MyWebDriveTest {
 
  MyWebDrive driver;
  @BeforeClass
  public void beforeClass() throws IOException {
   driver = new MyWebDrive();
  }



  String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "resouces/";
  @Test
  public void doPageSimultion() throws Exception {

    driver.doPageSimultion(path+ "doLogin.json");
    
    for(int i=3;i>=0;i--){
     driver.doPageSimultion(path+ "doTrans.json");
    }
    for(int i=3;i>=0;i--){
     driver.setPersistentData();
     driver.doPageSimultion(path+ "doConfirm.json");
    }
    for(int i=3;i>=0;i--){
     driver.doPageSimultion(path+ "doRecev.json");
    }
    for(int i=3;i>=0;i--){
     driver.doPageSimultion(path+ "doUf12Collect.json");
    }
    //driver.doPageSimultion(path+ "doUf12Store.json");
  }
  
  public void doUf12Collect() throws Exception{
   driver.doPageSimultion(path+ "doLogin.json");
   driver.getDriver().get("http://10.163.27.70/cddaGams/archives/business/sort/uf12CollectAction.do");
   Thread.sleep(5000);
   ExecJs execJs = new ExecJs();
  }
  
  @AfterClass
  public void afterClass() throws InterruptedException{
    driver.stop();
  }
}
