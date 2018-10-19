package top.xujm.core.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Xujm
 */
public class LibEncryptUtil {

    public static void main(String[] args) {
        String password = encodeBASE64(d3esEncode("111111"));
        System.out.println(password);

    }

    /**
     * SHA1加密Bit数据
     * @param source byte数组
     * @return 加密后的byte数组
     */
    public static byte[] sha1Bit(byte[] source) {
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            sha1Digest.update(source);
            return sha1Digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA1加密字符串数据
     * @param source 要加密的字符串
     * @return 加密后的字符串
     */
    public static String sha1(String source) {
        return byte2HexStr(sha1Bit(source.getBytes()));
    }

    /**
     * MD5加密Bit数据
     * @param source byte数组
     * @return 加密后的byte数组
     */
    public static byte[] md5Bit(byte[] source) {
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md5Digest.update(source);
            // 获得密文
            return md5Digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5加密字符串,32位长
     * @param source 要加密的内容
     * @return 加密后的内容
     */
    public static String md5(String source) {
        return byte2HexStr(md5Bit(source.getBytes()));
    }

    /**
     * BASE64编码
     * @param source 要编码的字符串
     * @return 编码过的字符串
     */
    public static String encodeBASE64(String source) {
        Class<?> clazz = null;
        Method encodeMethod = null;
        try {// 优先使用第三方库
            clazz = Class.forName("org.apache.commons.codec.binary.Base64");
            encodeMethod = clazz.getMethod("encodeBase64", byte[].class);
            // 反射方法 静态方法执行无需对象
            return new String((byte[]) encodeMethod.invoke(null, (Object) source.getBytes()));
        } catch (ClassNotFoundException e) {
            String vm = System.getProperty("java.vm.name");
            try {
                // Android
                if ("Dalvik".equals(vm)) {
                    clazz = Class.forName("android.util.Base64");
                    encodeMethod = clazz.getMethod("encode", byte[].class, int.class);
                    return new String((byte[]) encodeMethod.invoke(null, source.getBytes(), 0));
                } else {
                    // JavaSE/JavaEE
                    clazz = Class.forName("sun.misc.BASE64Encoder");
                    encodeMethod = clazz.getMethod("encode", byte[].class);
                    return (String) encodeMethod.invoke(clazz.newInstance(), (Object) source.getBytes());
                }
            } catch (Exception e1) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * BASE64解码
     * @param encodeSource 编码过的字符串
     * @return 编码前的字符串
     */
    public static String decodeBASE64(String encodeSource) {
        Class<?> clazz;
        Method decodeMethod;

        try {// 优先使用第三方库
            clazz = Class.forName("org.apache.commons.codec.binary.Base64");
            decodeMethod = clazz.getMethod("decodeBase64", byte[].class);
            // 反射方法 静态方法执行无需对象
            return new String((byte[]) decodeMethod.invoke(null, (Object) encodeSource.getBytes()));
        } catch (ClassNotFoundException e) {
            String vm = System.getProperty("java.vm.name");
            System.out.println(vm);
            try {
                // Android
                if ("Dalvik".equals(vm)) {
                    clazz = Class.forName("android.util.Base64");
                    decodeMethod = clazz.getMethod("decode", byte[].class, int.class);
                    return new String((byte[]) decodeMethod.invoke(null, encodeSource.getBytes(), 0));
                } else {
                    // JavaSE/JavaEE
                    clazz = Class.forName("sun.misc.BASE64Decoder");
                    decodeMethod = clazz.getMethod("decodeBuffer", String.class);
                    System.out.println("decodeBASE64-->" + clazz);
                    System.out.println("decodeMethod-->" + decodeMethod);
                    return new String((byte[]) decodeMethod.invoke(clazz.newInstance(), encodeSource));
                }
            } catch (Exception e1) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES加密
     * @param content 待加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encryptBitAES(byte[] content, String password) {
        try {
            Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(password));// 初始化
            byte[] result = encryptCipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // return null;
    }

    /**
     * AES解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static byte[] decryptBitAES(byte[] content, String password) {
        try {
            Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            decryptCipher.init(Cipher.DECRYPT_MODE, getKey(password));// 初始化
            byte[] result = decryptCipher.doFinal(content);
            return result; // 加密结果
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // return null;
    }

    /**
     * AES字符串加密
     *
     * @param content
     *            待加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static String encryptAES(String content, String password) {
        return byte2HexStr(encryptBitAES(content.getBytes(), password));
    }

    /**
     * AES字符串解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static String decryptAES(String content, String password) {
        return new String(decryptBitAES(hexStr2Bytes(content), password));
    }

    /**
     * 从指定字符串生成密钥
     *
     * @param password
     *            构成该秘钥的字符串
     * @return 生成的密钥
     * @throws NoSuchAlgorithmException
     */
    private static Key getKey(String password) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        // 生成KEY
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        // 转换KEY
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    /**
     * 将byte数组转换为表示16进制值的字符串. 如：byte[]{8,18}转换为：0812 和 byte[]
     * hexStr2Bytes(String strIn) 互为可逆的转换过程.
     *
     * @param bytes
     *            需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byte2HexStr(byte[] bytes) {
        int bytesLen = bytes.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder hexString = new StringBuilder(bytesLen * 2);
        for (byte aByte : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                // 如果为1位 前面补个0
                hexString.append(0);
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组, 和 String byte2HexStr(byte[] bytes) 互为可逆的转换过程.
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2Bytes(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    // 密钥
    private final static String secretKey = "liuyunqiang@lx100$#365#$";
    // 向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     * @param plainText 普通文本
     * @return String
     */
    public static String d3esEncode(String plainText) {
        Key deskey;
        byte[] encryptData = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            encryptData = cipher.doFinal(plainText.getBytes(encoding));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encryptData);

    }

    /**
     * 3DES解密
     * @param encryptText 加密文本
     */
    public static String d3esDecode(String encryptText) {
        Key deskey;
        byte[] decryptData;
        String result = "";
        if (null != encryptText && !"".equals(encryptText)) {
            try {
                DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
                SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
                deskey = keyfactory.generateSecret(spec);
                Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
                IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

                decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
                result = new String(decryptData, encoding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
