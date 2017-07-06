package com.jinhan.consts;

public enum JsCommandEum {
 CONFIRM("oldConfirm = confirm;confirm = function (msg){msg +='来自仿真'; window.operateState = oldConfirm(msg);}"),
 //TA_ALERT("oldTaAlert = Base.alert;Base.alert = function (msg,type,callback){window.operateState = true;oldTaAlert(msg,type,callback);} ");
 TA_ALERT("var result = (function(){try{return Base,true; }catch(e){}}());if(result){Base.alert = null;}");
 String command;
 JsCommandEum(String command){
  this.command = command;
 }
 public String getCommand() {
  return command;
 }
 public void setCommand(String command) {
  this.command = command;
 }
 
}
