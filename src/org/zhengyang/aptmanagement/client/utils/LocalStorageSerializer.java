package org.zhengyang.aptmanagement.client.utils;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.gwt.core.client.GWT;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 15, 2012
 */
public class LocalStorageSerializer {
  // TODO : need to add test for this class
  private LocalStorageSerializer() {
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromJson(String str) {
    int index = str.indexOf('=');
    String type = str.substring(0, index);
    String jsonString = str.substring(index + 1);
    if (type.equals("User")) {
      return (T) USER_READER.read(jsonString);
    }
    throw new IllegalArgumentException("Not supported object to be deserialied from JSON: type="
        + type);
  }

  public static String toJson(Object object) {
    if (object instanceof User) {
      return "User=" + USER_WRITER.toJson((User) object);
    }
    throw new IllegalArgumentException("Not supported object to be serialied to JSON");
  }

  static {
  }

  public interface UserReader extends JsonReader<User> {
  }
  public interface UserWriter extends JsonWriter<User> {
  }

  private static final UserReader USER_READER = GWT.create(UserReader.class);
  private static final UserWriter USER_WRITER = GWT.create(UserWriter.class);
}
