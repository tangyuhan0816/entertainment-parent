package com.entertainment.asset.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.entity.sys.TbUser;
import com.entertainment.common.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: JwtService
 *  @package: com.entertainment.asset.service.jwt
 *  @Date: Created in 2018/7/19 下午6:02
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    @Value("${jwt.expire_time}")
    private long expireTime;

    @Value("${jwt.sign_key}")
    private String signKey;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public boolean verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(signKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            logger.error("verify jwt token error with token : {}", token);
        }
        return false;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public String getValueByParams(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            if(claims != null && claims.size() > 0 && claims.containsKey(key)){
                return claims.get(key).asString();
            }
        } catch (JWTDecodeException e) {
            logger.error("get value from jwt token error with token : {} and key : {}", token, key);
        }
        return null;
    }

    public Long getAccountIdFromToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");

        String accountId = this.getValueByParams(authorization, "account_id");

        return Long.valueOf(accountId);
    }

    /**
     * 生成jwt token，暂时只保存username, 角色,用于之后手动清某一用户或者某一角色shiro缓存使用
     * @param sysUser
     * @return
     */
    public String createJwt(TbUser sysUser) {
        try {
            Algorithm al = Algorithm.HMAC256(signKey);
            String token = JWT.create()
                    .withIssuer("asset")
                    .withSubject("custom")
                    .withClaim("username", sysUser.getPhoneNum())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime*1000))
                    .sign(al);
            return token;
        } catch (UnsupportedEncodingException e) {
            logger.error("create jwt token error with username: {}", sysUser.getPhoneNum());
        }
        return null;
    }

    public boolean checkTokenValid(String token){
        return Preconditions.isBlank(valueOperations.get(token));
    }

    public void addTokenToBlacklist(String token){
        valueOperations.set(token, "1", expireTime, TimeUnit.SECONDS);
    }
}