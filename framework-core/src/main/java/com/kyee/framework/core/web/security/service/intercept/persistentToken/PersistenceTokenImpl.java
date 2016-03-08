//package com.kyee.framework.core.web.security.service.intercept.persistentToken;
//
//import com.kyee.framework.core.web.security.session.SessionUser;
//import com.kyee.framework.core.web.security.token.AbstractTokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.codec.Base64;
//import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.servlet.http.HttpServletResponse;
//import java.security.SecureRandom;
//import java.util.Date;
//
///**
// * @author 程峰
// * 创建时间：15/8/14 下午4:46
// * 任务号：MOBILEDEVELOP-10006
// * 创建说明：实现token生成和解析接口
// */
////@Service
//public class PersistenceTokenImpl extends AbstractTokenService {
//    /**
//     * 随机函数
//     * */
//    private SecureRandom random = new SecureRandom();
//    /**
//     * 生成series长度
//     * */
//    private int seriesLength = 16;
//    /**
//     * 生成token长度
//     * */
//    private int tokenLength = 16;
//
//    /**
//     * token过期时间
//     * */
//    @Value("${kyee.security.expire:7200}")
//    private final long expire = 7200;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PersistentTokenRepository tokenRepository;
//
//    /**
//     * 生成token,并保存至数据库
//     * @param username 用户
//     * @param response 响应
//     * @return token  格式: Base64{series:tokenData }
//     * */
//    public String parseTokenFromUser(String username,SessionUser sessionUser,HttpServletResponse response){
//        String series = generateSeriesData();
//        String tokenData = generateTokenData();
//        PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, series, tokenData, new Date());
//        this.tokenRepository.removeUserTokens(username);   //移除旧的token
//        this.tokenRepository.createNewToken(persistentToken);
//        return encodeToken(new String[]{series, tokenData});
//    }
//
//    /**
//     * 通过token解析用户信息
//     * @param token token
//     * @param response 返回给调用者的响应
//     * @return 返回用户详细信息
//     */
//    public UserDetails parseUserFromToken(String token,HttpServletResponse response){
//        String[] tokens = decodeToken(token);
//        return processTokens(tokens);
//    }
//
//    /**
//     * 处理tokens
//     * @param tokens 解码后的token, String[]{series,token}
//     * @return 用户详细信息
//     */
//    private UserDetails processTokens(String[] tokens) {
//        if(2 != tokens.length){ //token长度不符合
//            return null;
//        }else{
//            String presentedSeries = tokens[0];
//            String presentedToken = tokens[1];
//            PersistentRememberMeToken token = this.tokenRepository.getTokenForSeries(presentedSeries);
//            if(null == token) {
//                return null;
//            } else if(!presentedToken.equals(token.getTokenValue())) {  //token值和数据库中不符
//                return null;
//            } else if(token.getDate().getTime() + this.getExpire()*1000L < System.currentTimeMillis()) {//token过期
//                return null;
//            } else {
//                return this.userDetailsService.loadUserByUsername(token.getUsername()); //返回用户信息
//            }
//        }
//    }
//
//
//    /**
//     * 随机生成series,一个用户的token 唯一用一个series标识
//     * */
//    private String generateSeriesData() {
//        byte[] newSeries = new byte[this.seriesLength];
//        this.random.nextBytes(newSeries);
//        return new String(Base64.encode(newSeries));
//    }
//
//    /**
//     * 随机生成token的内容
//     * */
//    private String generateTokenData() {
//        byte[] newToken = new byte[this.tokenLength];
//        this.random.nextBytes(newToken);
//        return new String(Base64.encode(newToken));
//    }
//
//
//
//
//
//    public long getExpire() {
//        return expire;
//    }
//
//    public int getSeriesLength() {
//        return seriesLength;
//    }
//
//    public void setSeriesLength(int seriesLength) {
//        this.seriesLength = seriesLength;
//    }
//
//    public int getTokenLength() {
//        return tokenLength;
//    }
//
//    public void setTokenLength(int tokenLength) {
//        this.tokenLength = tokenLength;
//    }
//
//}
