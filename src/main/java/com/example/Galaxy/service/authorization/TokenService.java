package com.example.Galaxy.service.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Galaxy.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    public String getToken(User user){
        Date start = new Date();
        long timeSpan = System.currentTimeMillis()+60*60*1000;//一个小时有效时间
        Date end = new Date(timeSpan);
        String token = JWT.create()
                .withAudience(user.getUserId()
                .toString())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPasswd()));
        return token;
    }
}
