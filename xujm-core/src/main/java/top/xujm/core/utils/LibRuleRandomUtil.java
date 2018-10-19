package top.xujm.core.utils;

import java.util.Random;

/**
 * 根据一个值随机一个数并能反解出原值
 * @author Xujm
 */
public class LibRuleRandomUtil {


    /**
     * 自定义进制,包含字母数字(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] ALL_CHAR = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};
    /**
     * 自定义进制,包含字母
     */
    private static final char[] LETTER_CHAR = new char[]{'q', 'w', 'e', 'a', 's','d', 'z', 'x', 'c','p', 'i', 'k', 'm', 'j', 'u', 'f', 'r', 'v', 'y', 'l', 't', 'n', 'b', 'g', 'h'};
    /**
     * 自定义进制,包含字母
     */
    private static final char[] NUMBER_CHAR = new char[]{ '8', '2', '9',  '7',  '5',  '3', '4', '6','1'};

    /** 序列随机数默认最小长度 */
    private static final int DEFAULT_LENGTH = 6;

    /** (自定义包含字母进制中应不包含该字母) */
    private static final char LETTER = 'o';
    /** (自定义包含数字进制中应不包含该数字) */
    private static final char NUMBER = '0';

    /**
     * 随机数包含字母和数字
     */
    public static String allToCode(int id){
        return allToCode(id,DEFAULT_LENGTH);
    }

    public static String allToCode(int id,int l){
        return toSerialCode(id,ALL_CHAR,LETTER,l);
    }

    public static int allToId(String code){
        return codeToId(code,ALL_CHAR,LETTER);
    }

    /**
     * 随机数只包含字母
     */
    public static String letterToCode(int id){
        return letterToCode(id,DEFAULT_LENGTH);
    }

    public static String letterToCode(int id,int l){
        return toSerialCode(id,LETTER_CHAR,LETTER,l);
    }

    public static int letterToId(String code){
        return codeToId(code,LETTER_CHAR,LETTER);
    }

    /**
     * 随机数只包含字母
     */
    public static String numberToCode(int id){
        return numberToCode(id,DEFAULT_LENGTH);
    }

    public static String numberToCode(int id,int l){
        return toSerialCode(id,NUMBER_CHAR,NUMBER,l);
    }

    public static int numberToId(String code){
        return codeToId(code,NUMBER_CHAR,NUMBER);
    }

    /**
     * 根据ID生成随机码
     */
    private static String toSerialCode(int id,char[] chars,char b,int l) {
        int charLen = chars.length;
        char[] buf=new char[32];
        int charPos=32;
        while((id / charLen) > 0) {
            int ind=(id % charLen);
            buf[--charPos]=chars[ind];
            id /= charLen;
        }
        buf[--charPos]=chars[(id % charLen)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < l) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd=new Random();
            for(int i=1; i < l - str.length(); i++) {
                sb.append(chars[rnd.nextInt(charLen)]);
            }
            str+=sb.toString();
        }
        return str.toUpperCase();
    }

    /**
     * 反转原值
     */
    private static int codeToId(String code,char[] chars,char b) {
        int charLen = chars.length;
        code = code.toLowerCase();
        char[] chs=code.toCharArray();
        int res=0;
        for(int i=0; i < chs.length; i++) {
            int ind=0;
            for(int j=0; j < charLen; j++) {
                if(chs[i] == chars[j]) {
                    ind=j;
                    break;
                }
            }
            if(chs[i] == b) {
                break;
            }
            if(i > 0) {
                res=res * charLen + ind;
            } else {
                res=ind;
            }
        }
        return res;
    }


    public static void main(String[] args) {
//        String code = letterToCode(621);
//        System.out.println(code);
//
//        System.out.println(letterToId(code));
    }


}
