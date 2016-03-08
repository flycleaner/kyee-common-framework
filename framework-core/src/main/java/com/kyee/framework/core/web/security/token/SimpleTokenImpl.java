package com.kyee.framework.core.web.security.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyee.framework.core.web.security.utils.ResponseHelper;
import com.kyee.framework.core.web.security.session.SessionUser;
import com.kyee.framework.core.web.security.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author 程峰
 * 创建时间：15/8/14 下午5:10
 * 任务号：MOBILEDEVELOP-10006
 * 创建说明：实现token生成解析接口
 */
@Service
public class SimpleTokenImpl extends AbstractTokenService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private Environment environment;

    /**
     * hash生成算法
     */
    private static final String HMAC_ALGO = "HmacSHA256";

    private Mac hmac = null;

    private static final int DEFAULT_TOKEN_LENGTH = 32;

    private final int tokenLength = DEFAULT_TOKEN_LENGTH;

    /**
     * 过期时间
     */
    @Value("${kyee.security.expire:7200}")
    private long expire;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTokenImpl.class);

    public int getTokenLength() {
        return tokenLength;
    }

    public SimpleTokenImpl(){
        super();
        LOGGER.info("采用简单token生成策略: {}", SimpleTokenImpl.class);
    }

    @PostConstruct
    public void init(){
        String  secretKey = environment.getProperty("kyee.security.token.secretKey");
        byte[] secretKeyByte = new byte[this.tokenLength];

        LOGGER.info("token过期时间: {}", expire);
        if(null == secretKey){
            LOGGER.warn("生成token的私钥 ${kyee.security.token.secretKey}没有找到");
            new SecureRandom().nextBytes(secretKeyByte);
            LOGGER.warn("使用随机生成私钥：{}",secretKeyByte);
        } else {
            secretKeyByte = secretKey.getBytes();
        }

        try {
            hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(secretKeyByte, HMAC_ALGO));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    /**
     * 通过用户信息生成token
     * @param username 用户名
     * @param response 响应
     * @return token
     */
    @Override
    public String parseTokenFromUser(String username,String domain,SessionUser sessionUser,HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities(),sessionUser.getCreateTime());
        user.setId(sessionUser.getId());
        user.setDomain(domain);
        try {
            byte[] userByte = new ObjectMapper().writeValueAsBytes(user);
            byte[] hash = createHmac(userByte);
            return encodeToken(new String []{new String(Base64.encode(userByte)),new String (Base64.encode(hash))});
        } catch (JsonProcessingException e) {
            ResponseHelper.createExceptionResponse(response, "json解析错误", e);
            return null;
        }
    }

    /**
     * 通过token解析用户信息
     * @param token token
     * @param response 响应
     * @return 用户信息
     */
    @Override
    public UserDetails parseUserFromToken(String token,HttpServletResponse response) {
        String [] tokens = decodeToken(token);
        return processTokens(tokens);
    }

    /**
     * 处理用户信息
     * @param tokens token
     * @return 用户信息
     */
    private UserDetails processTokens(String[] tokens) {
        if(2 != tokens.length){
            return null;
        }else{
            byte[] userByte = Base64.decode(tokens[0].getBytes());
            byte[] hash = Base64.decode(tokens[1].getBytes());
            if(Arrays.equals(createHmac(userByte),hash)){
                try { //反序列化 userByte
                    User user = new ObjectMapper().readValue(new ByteArrayInputStream(userByte),User.class);
                    if(user.getDate().getTime() + expire*1000L > System.currentTimeMillis()){
                        return user;
                    }
                } catch (IOException e) { //出错，返回空
                    return null;
                }
            }
            return null;
        }
    }


    /**
     * 创建hash
     * */
    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }
}
