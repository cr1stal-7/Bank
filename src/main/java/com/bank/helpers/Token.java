package com.bank.helpers;

import java.util.UUID;
//Генерация токена
public class Token {

    public static String generateToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }
}
