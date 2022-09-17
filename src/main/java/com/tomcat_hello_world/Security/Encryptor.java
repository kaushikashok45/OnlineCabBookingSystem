package com.tomcat_hello_world.Security;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Encryptor {
    protected static String encrypt(String pwd){
        String encryptedPwd=null;
        try{
            MessageDigest m=MessageDigest.getInstance(Constants.md5);
            m.update(pwd.getBytes());
            byte[] bytes=m.digest();
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
            encryptedPwd=s.toString();
        }
        catch(NoSuchAlgorithmException e){
           e.printStackTrace();
        }
        return encryptedPwd;
    }
}

