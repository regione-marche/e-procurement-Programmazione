package it.appaltiecontratti.autenticazione.utils;

import java.util.HashMap;

public class UtilityHashMap
{
  public static Object getObjectParent(@SuppressWarnings("rawtypes") HashMap map, String fixedText, String id, boolean searchStar)
  {
    if (fixedText == null) {
      fixedText = "";
    } else if (fixedText.length() > 0) {
      fixedText = fixedText + ".";
    }
    if (map.get(fixedText + id) != null) {
      return map.get(fixedText + id);
    }
    while ((id != null) && (id.length() > 0))
    {
      if (id.indexOf('.') >= 0) {
        id = id.substring(0, id.lastIndexOf('.'));
      } else {
        id = "";
      }
      if (searchStar)
      {
        String key = id;
        if (key.length() > 0) {
          key = key + ".";
        }
        key = key + "*";
        if (map.get(fixedText + key) != null) {
          return map.get(fixedText + key);
        }
      }
      if ((id.length() > 0) && (map.get(fixedText + id) != null)) {
        return map.get(fixedText + id);
      }
    }
    return null;
  }
  
  public static Object getValueCaseInsensitive(@SuppressWarnings("rawtypes") HashMap hash, String key)
  {
    Object valoreCampo = null;
    if (hash != null) {
      if (hash.containsKey(key.toLowerCase())) {
        valoreCampo = hash.get(key.toLowerCase());
      } else {
        valoreCampo = hash.get(key.toUpperCase());
      }
    }
    return valoreCampo;
  }
}