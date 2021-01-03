package com.example.Galaxy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Galaxy.exception.CodeEnums;
import com.example.Galaxy.exception.GalaxyException;

import java.util.Date;

public class JWTUtil {

    // 过期时间24小时
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String account, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", account)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (TokenExpiredException exception) {
            throw new GalaxyException(CodeEnums.ERROR_TOKEN_EXPIRED.getCode(), CodeEnums.ERROR_TOKEN_EXPIRED.getMessage());
        } catch (Exception e) {
            throw new GalaxyException(CodeEnums.ERROR_TOKEN_INVALID.getCode(), CodeEnums.ERROR_TOKEN_INVALID.getMessage());
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (TokenExpiredException e) {
            throw new GalaxyException(CodeEnums.ERROR_TOKEN_EXPIRED.getCode(), CodeEnums.ERROR_TOKEN_EXPIRED.getMessage());
        }
    }

    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (TokenExpiredException e) {
            throw new GalaxyException(CodeEnums.ERROR_TOKEN_EXPIRED.getCode(), CodeEnums.ERROR_TOKEN_EXPIRED.getMessage());
        } catch (Exception e) {
            throw new GalaxyException(CodeEnums.ERROR_TOKEN_INVALID.getCode(), CodeEnums.ERROR_TOKEN_INVALID.getMessage());
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param account 账号
     * @param secret  用户的密码
     * @return 加密的token
     */
    public static String sign(String account, String secret, Long userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new GalaxyException(CodeEnums.ERROR_SIGN_TOKEN.getCode(), CodeEnums.ERROR_SIGN_TOKEN.getMessage());
        }
    }
}
