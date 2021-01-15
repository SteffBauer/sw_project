package de.othr.sw.bank.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUtils {

    private String tokenVigoPay;
    // todo load all tokens beginning with token- from app properties in list

    @Autowired
    public AuthenticationUtils(@Value("${token-vigo-pay}") String tokenVigoPay) {
        this.tokenVigoPay = tokenVigoPay;
    }

    public boolean equalsTokenVigoPay(String token){
        return token.equals(tokenVigoPay);
    }
}
