package cn.xuetang.common.dataTable;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.nutz.log.Log;
import org.nutz.log.Logs;

public final class StringUtils
{
  private static final Log logger = Logs.get();
  /**
   * 默认字符集
   */
  private static String defaultCharset = "UTF-8";




  public static String newString(String value, int length)
  {
    if (value == null) {
      return null;
    }
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < length; i++) {
      buffer.append(value);
    }
    return buffer.toString();
  }

  public static String newString(char inChr, int length)
  {
    return newString(String.valueOf(inChr), length);
  }

  public static String copyString(String str, int copyTimes)
  {
    if (str == null) {
      return null;
    }
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < copyTimes; i++) {
      buffer.append(str);
    }
    return buffer.toString();
  }



  public static int indexOf(String str, String subStr, int startIndex, int occurrenceTimes)
  {
    int foundCount = 0;
    int index = startIndex;
    if (occurrenceTimes <= 0) {
      return -1;
    }
    if (str.length() - 1 < startIndex) {
      return -1;
    }
    if ("".equals(subStr)) {
      return 0;
    }
    int substrLength = subStr.length();
    while (foundCount < occurrenceTimes) {
      index = str.indexOf(subStr, index);
      if (index == -1) {
        return -1;
      }
      foundCount++;
      index += substrLength;
    }
    return index - substrLength;
  }

  public static int indexOf(String str, String subStr, int occurrenceTimes)
  {
    return indexOf(str, subStr, 0, occurrenceTimes);
  }

  public static int indexOf(String str, String subStr, int fromIndex, boolean caseSensitive)
  {
    if (!caseSensitive) {
      return str.toLowerCase().indexOf(subStr.toLowerCase(), fromIndex);
    }
    return str.indexOf(subStr, fromIndex);
  }

  public static String replace(String str, String searchStr, String replaceStr, boolean caseSensitive)
  {
    int i = 0;
    int j = 0;
    if (str == null) {
      return null;
    }
    if ("".equals(str)) {
      return "";
    }
    if ((searchStr == null) || (searchStr.equals(""))) {
      return str;
    }
    String newReplaceStr = replaceStr;
    if (replaceStr == null) {
      newReplaceStr = "";
    }
    StringBuilder buffer = new StringBuilder();
    while ((j = indexOf(str, searchStr, i, caseSensitive)) > -1) {
      buffer.append(str.substring(i, j));
      buffer.append(newReplaceStr);
      i = j + searchStr.length();
    }
    buffer.append(str.substring(i, str.length()));
    return buffer.toString();
  }
  /**
   * 将字符串str中所有的searchChar都替换为newStr.  
   * @param str
   * @param searchChar
   * @param replaceStr
   * @return
   */
  public static String replace(String str, String searchStr, String replaceStr)
  {
    return replace(str, searchStr, replaceStr, true);
  }
  /**
   * 将字符串str中所有的searchChar都替换为newStr.  
   * @param str
   * @param searchChar
   * @param replaceStr
   * @return
   */
  public static String replace(String str, char searchChar, String replaceStr)
  {
    return replace(str, String.valueOf(searchChar), replaceStr, true);
  }

  public static String replace(String str, int beginIndex, String replaceStr)
  {
    if (str == null) {
      return null;
    }
    String newReplaceStr = replaceStr;
    if (replaceStr == null) {
      newReplaceStr = "";
    }
    StringBuilder buffer = new StringBuilder(str.substring(0, beginIndex));
    buffer.append(newReplaceStr);
    buffer.append(str.substring(beginIndex + newReplaceStr.length()));
    return buffer.toString();
  }


  public static String[] split(String originalString, int splitByteLength)
  {
    return split(originalString, splitByteLength, defaultCharset);
  }

  public static String[] split(String originalString, int splitByteLength, String charsetName)
  {
    if (originalString == null) {
      return new String[0];
    }
    if ("".equals(originalString)) {
      return new String[0];
    }
    if (originalString.trim().equals("")) {
      return new String[] { "" };
    }
    if (splitByteLength <= 1) {
      return new String[] { originalString };
    }

    int size = originalString.length();
    List strList = new ArrayList();
    try {
      int count = 0;
      int start = 0;
      int len = 0;
      for (int i = 0; i < size; i++) {
        len = String.valueOf(originalString.charAt(i)).getBytes(charsetName).length;

        count += len;
        if (count == splitByteLength) {
          strList.add(originalString.substring(start, i + 1));
          count = 0;
          start = i + 1;
        } else if (count > splitByteLength) {
          strList.add(originalString.substring(start, i));
          count = len;
          start = i;
        }
      }
      if (start < size)
        strList.add(originalString.substring(start));
    }
    catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    String[] arrReturn = new String[strList.size()];
    strList.toArray(arrReturn);
    return arrReturn;
  }
  /**
   * 按字符拆分字符串
   * @param originalString
   * @param delimiterString
   * @return
   */
  public static String[] split(String originalString, String delimiterString)
  {
    int index = 0;
    String[] returnArray = null;
    int length = 0;

    if ((originalString == null) || (delimiterString == null) || ("".equals(originalString))) {
      return new String[0];
    }

    if (("".equals(originalString)) || ("".equals(delimiterString)) || (originalString.length() < delimiterString.length()))
    {
      return new String[] { originalString };
    }

    String strTemp = originalString;
    while ((strTemp != null) && (!strTemp.equals(""))) {
      index = strTemp.indexOf(delimiterString);
      if (index == -1) {
        break;
      }
      length++;
      strTemp = strTemp.substring(index + delimiterString.length());
    }
    length++; returnArray = new String[length];

    strTemp = originalString;
    for (int i = 0; i < length - 1; i++) {
      index = strTemp.indexOf(delimiterString);
      returnArray[i] = strTemp.substring(0, index);
      strTemp = strTemp.substring(index + delimiterString.length());
    }
    returnArray[(length - 1)] = strTemp;
    return returnArray;
  }

  public static String rightTrim(String str)
  {
    if (str == null) {
      return "";
    }
    int length = str.length();
    for (int i = length - 1; (i >= 0) && 
      (str.charAt(i) == ' '); i--)
    {
      length--;
    }
    return str.substring(0, length);
  }

  public static String leftTrim(String str)
  {
    if (str == null) {
      return "";
    }
    int start = 0;
    int i = 0; for (int n = str.length(); (i < n) && 
      (str.charAt(i) == ' '); i++)
    {
      start++;
    }
    return str.substring(start);
  }

  public static String absoluteTrim(String str)
  {
    return replace(str, " ", "");
  }

  public static String lowerCase(String str, int beginIndex, int endIndex)
  {
    StringBuilder buffer = new StringBuilder();
    buffer.append(str.substring(0, beginIndex));
    buffer.append(str.substring(beginIndex, endIndex).toLowerCase());
    buffer.append(str.substring(endIndex));
    return buffer.toString();
  }

  public static String upperCase(String str, int beginIndex, int endIndex)
  {
    StringBuilder buffer = new StringBuilder();
    buffer.append(str.substring(0, beginIndex));
    buffer.append(str.substring(beginIndex, endIndex).toUpperCase());
    buffer.append(str.substring(endIndex));
    return buffer.toString();
  }

  public static String lowerCaseFirstChar(String iString)
  {
    if ((iString == null) || (iString.length() < 1)) {
      throw new IllegalArgumentException("String must have at least one character.");
    }
    return iString.length() + iString.substring(0, 1).toLowerCase() + iString.substring(1);
  }

  public static String upperCaseFirstChar(String iString)
  {
    if ((iString == null) || (iString.length() < 1)) {
      throw new IllegalArgumentException("String must have at least one character.");
    }
    return  iString.substring(0, 1).toUpperCase() + iString.substring(1);
  }

  public static int timesOf(String str, String subStr)
  {
    int foundCount = 0;
    if ("".equals(subStr)) {
      return 0;
    }
    int fromIndex = str.indexOf(subStr);
    while (fromIndex != -1) {
      foundCount++;
      fromIndex = str.indexOf(subStr, fromIndex + subStr.length());
    }
    return foundCount;
  }

  public static int timesOf(String str, char ch)
  {
    int foundCount = 0;
    int fromIndex = str.indexOf(ch);
    while (fromIndex != -1) {
      foundCount++;
      fromIndex = str.indexOf(ch, fromIndex + 1);
    }
    return foundCount;
  }

  public static Map<String, String> toMap(String str, String splitString)
  {
    Map map = new HashMap();
    String[] values = split(str, splitString);

    String key = "";
    String value = "";
    for (int i = 0; i < values.length; i++) {
      String tempValue = values[i];
      int pos = tempValue.indexOf('=');
      key = "";
      value = "";
      if (pos > -1) {
        key = tempValue.substring(0, pos);
        value = tempValue.substring(pos + splitString.length());
      } else {
        key = tempValue;
      }
      map.put(key, value);
    }
    return map;
  }

  public static String native2ascii(String str)
  {
    char[] ca = str.toCharArray();
    StringBuilder buffer = new StringBuilder(ca.length * 6);
    for (int x = 0; x < ca.length; x++) {
      char a = ca[x];
      if (a > 'y')
        buffer.append("\\u").append(Integer.toHexString(a));
      else {
        buffer.append(a);
      }
    }
    return buffer.toString();
  }

  public static String concat(Object[] sources)
  {
    if (sources == null) {
      return "";
    }
    if (sources.length == 1) {
      return String.valueOf(sources[0]);
    }
    StringBuilder sb = new StringBuilder();
    for (Object o : sources) {
      if (o != null) {
        sb.append(o);
      }
    }
    return sb.toString();
  }
  
	public static final String inputStream2String(InputStream in) throws UnsupportedEncodingException, IOException{
		if(in == null)
			return "";
		
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}
	/**
	 * 计算一串字符的字节数
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return size;
	}
  /**
   * 数组字典排序
   * @return
   */
  public static String[] sortArray(String[] str){
	   Arrays.sort(str); // 字典序排序
	   return str;
  }
  /**
   * 生成随机长度的字符串
   * @param length
   * @return
   */
  public static String RandomString(int length)
  {
   String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
   Random  random = new Random();
   StringBuffer buf = new StringBuffer();
   
   for(int i = 0 ;i < length ; i ++)
   {
    int num = random.nextInt(62);
    buf.append(str.charAt(num));
   }
   
   return buf.toString();
  }
	//把字符串转换为HashMap
	public static Map CoverListToMapping(List list,String mapKey){
		Map<String, Object> map = new HashMap<String, Object>();  
		for(Object object : list){//循环入参
			final String key = getProperty(object,mapKey).toString();
		   if(object==null){
			    continue;
			   }
			map.put(key, object);
		}
		return map;		
	}
	private static Object getProperty(Object object, String mapKey) {
		Field[] fields = object.getClass().getDeclaredFields();//获取所有自定义属性
        for (int i = 0, j = fields.length; i < j; i++) {
            String propertyName = fields[i].getName();
            try {
                Class clazz = object.getClass();
                Field field = clazz.getDeclaredField(propertyName);
                Method method = clazz.getDeclaredMethod(getgetterName(field
                        .getName()));
                return  method.invoke(object);
            } catch (Exception e) {
            }
            
         }		
		return null;
	}
	// 获得set方法名
    private static String getgetterName(String propertyName) {
        String method = "get" + propertyName.substring(0, 1).toUpperCase()
                + propertyName.substring(1);
        return method;
    }   
}