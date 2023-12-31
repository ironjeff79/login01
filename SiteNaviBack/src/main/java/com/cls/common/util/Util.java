package com.cls.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Util {
    /*
     * md5算法进行密码加密
     * */
    public static String code(String str){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5") ;
            md.update(str.getBytes());
            byte[] byteDigest = md.digest() ;
            int i ;
            StringBuffer buf = new StringBuffer("") ;
            //遍历byteDigest
            for(int offset = 0 ; offset<byteDigest.length ; offset++){
                i = byteDigest[offset] ;
                if(i < 0)
                    i += 256 ;
                if(i < 16)
                    buf.append("0") ;
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                buf.append(Integer.toHexString(i)) ;
            }
            return buf.toString() ;
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null ;
        }
    }
}