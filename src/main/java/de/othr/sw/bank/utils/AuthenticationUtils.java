package de.othr.sw.bank.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationUtils {

    private List<String> authenticationTokens;

    @Autowired
    public AuthenticationUtils(@Value("#{'${authentication-tokens}'.split(',')}") List<String> authenticationTokens) {
        this.authenticationTokens = authenticationTokens;
    }

    public boolean isValidToken(String token){
        return !StringUtils.isNullOrEmpty(token) && authenticationTokens.contains(token);
    }
}
