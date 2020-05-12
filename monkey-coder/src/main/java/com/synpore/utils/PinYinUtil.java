package com.synpore.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinYinUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinYinUtil.class);
    private static final HanyuPinyinOutputFormat FORMAT=new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 中文转换成拼音
     * @param src
     * @return
     */
    public static String convertChineseToPinYin(String src) {
        if (StringUtils.isBlank(src)) {
            return "";
        }
        char[] srcChars = src.toCharArray();
        StringBuffer buffer = new StringBuffer();
        try {
            for (int i = 0; i < srcChars.length; i++) {
                if (Character.toString(srcChars[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] pyArray= PinyinHelper.toHanyuPinyinStringArray(srcChars[i], FORMAT);
                    buffer.append(StringUtils.capitalize(pyArray[0]));
                } else {
                    buffer.append(srcChars[i]);
                }
            }
        } catch (Exception e) {
            LOGGER.error("convertChineseToPinYin exception,src={}", src, e);
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
}
