package com.jinhan.simultion.webDriver;

import com.google.gson.JsonArray;

public class Target {

 private String url;
 private JsonArray handles;
 public String getUrl() {
  return url;
 }
 public void setUrl(String url) {
  this.url = url;
 }
 public JsonArray getHandles() {
  return handles;
 }
 public void setHandles(JsonArray handles) {
  this.handles = handles;
 }

}
