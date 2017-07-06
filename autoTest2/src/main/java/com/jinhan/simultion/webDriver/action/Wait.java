package com.jinhan.simultion.webDriver.action;

public class Wait extends Handle{

 private int value;
 
 public int getValue() {
  return value;
 }

 public void setValue(int value) {
  this.value = value;
 }

 @Override
 public void doAction() throws NumberFormatException, InterruptedException {
  Thread.sleep(getValue());
  
 }

}
