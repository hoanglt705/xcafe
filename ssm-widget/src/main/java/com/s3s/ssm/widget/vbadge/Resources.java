package com.s3s.ssm.widget.vbadge;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.util.ImageUtils;

public class Resources {

  /**  */
  private static Map<String, Object> fResources = new HashMap<String, Object>();

  static {

    fResources.put("images.badge.24", ImageUtils.getImageFromFile("/images/Badge24.png"));
    fResources.put("images.badge.24.white", ImageUtils.getImageFromFile("/images/Badge24White.png"));
    fResources.put("images.badge.24.lightGray",
            ImageUtils.getImageFromFile("/images/Badge24LightGray.png"));
    fResources.put("images.badge.24.gray", ImageUtils.getImageFromFile("/images/Badge24Gray.png"));
    fResources.put("images.badge.24.darkGray", ImageUtils.getImageFromFile("/images/Badge24DarkGray.png"));
    fResources.put("images.badge.24.black", ImageUtils.getImageFromFile("/images/Badge24Black.png"));
    fResources.put("images.badge.24.red", ImageUtils.getImageFromFile("/images/Badge24Red.png"));
    fResources.put("images.badge.24.pink", ImageUtils.getImageFromFile("/images/Badge24Pink.png"));
    fResources.put("images.badge.24.orange", ImageUtils.getImageFromFile("/images/Badge24Orange.png"));
    fResources.put("images.badge.24.yellow", ImageUtils.getImageFromFile("/images/Badge24Yellow.png"));
    fResources.put("images.badge.24.green", ImageUtils.getImageFromFile("/images/Badge24Green.png"));
    fResources.put("images.badge.24.magenta", ImageUtils.getImageFromFile("/images/Badge24Magenta.png"));
    fResources.put("images.badge.24.cyan", ImageUtils.getImageFromFile("/images/Badge24Cyan.png"));
    fResources.put("images.badge.24.blue", ImageUtils.getImageFromFile("/images/Badge24Blue.png"));
//    fResources.put("empty-icon", ImageUtils.getImageFromFile("/images/empty_icon.png"));
  }

  /**
   * @param key
   * @return resource from the loaded map
   */
  public static Object getResources(String key) {
    return fResources.get(key);
  }
}
