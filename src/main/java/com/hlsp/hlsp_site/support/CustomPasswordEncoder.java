package com.hlsp.hlsp_site.support;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CustomPasswordEncoder{

    byte[] salt;

    byte[] hash;

    SecureRandom saltShaker;

    //Returns true on success, false on failure
    public boolean saltAndHash(String password){   
        saltShaker.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try{
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        hash = factory.generateSecret(spec).getEncoded();
        return true;
        }catch(NoSuchAlgorithmException exception){
            // model.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            // model.put("message", exception.getMessage());
            // return new ModelAndView("exception", model);
            return false;
        }catch(InvalidKeySpecException exception){
            // model.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            // model.put("message", exception.getMessage());
            // return new ModelAndView("exception", model);
            return false;
        }
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public boolean hashWithCurrentSalt(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
            return true;
        }catch(NoSuchAlgorithmException exception){
            return false;
        }catch(InvalidKeySpecException exception){
            return false;
        }
    }
    }