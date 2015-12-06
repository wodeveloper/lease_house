package com.base.entity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class TokenHelper  
{  
      
    /** 
     * 保存token值的默认命名空间 
     */  
    public static final String TOKEN_NAMESPACE = "xxx.tokens";  
      
    /** 
     * 持有token名称的字段名 
     */  
    public static final String TOKEN_NAME_FIELD = "tokenName";  
    private static final Logger LOG = Logger.getLogger(TokenHelper.class);  
    private static final Random RANDOM = new Random();  
      
    private static HashMap redisCacheClient=new HashMap();// 缓存调用,代替session,支持分布式   
      
    public static void setRedisCacheClient(HashMap redisCacheClient)  
    {  
        TokenHelper.redisCacheClient = redisCacheClient;  
    }  
      
    /** 
     * 使用随机字串作为token名字保存token 
     *  
     * @param request 
     * @return token 
     */  
    public static String setToken(HttpServletRequest request)  
    {  
        return setToken(request, generateGUID());  
    }  
      
    /** 
     * 使用给定的字串作为token名字保存token 
     *  
     * @param request 
     * @param tokenName 
     * @return token 
     */  
    private static String setToken(HttpServletRequest request, String tokenName)  
    {  
        String token = generateGUID();  
        setCacheToken(request, tokenName, token);  
        return token;  
    }  
      
    /** 
     * 保存一个给定名字和值的token 
     *  
     * @param request 
     * @param tokenName 
     * @param token 
     */  
    private static void setCacheToken(HttpServletRequest request, String tokenName, String token)  
    {  
        try  
        {  
            String tokenName0 = buildTokenCacheAttributeName(tokenName);  
            redisCacheClient.put(token, token);  
//            request.setAttribute(TOKEN_NAME_FIELD, tokenName);  
            request.setAttribute(TOKEN_NAME_FIELD, token);  
        }  
        catch(IllegalStateException e)  
        {  
            String msg = "Error creating HttpSession due response is commited to client. You can use the CreateSessionInterceptor or create the HttpSession from your action before the result is rendered to the client: " + e.getMessage();  
            LOG.error(msg, e);  
            throw new IllegalArgumentException(msg);  
        }  
    }  
      
    /** 
     * 构建一个基于token名字的带有命名空间为前缀的token名字 
     *  
     * @param tokenName 
     * @return the name space prefixed session token name 
     */  
    public static String buildTokenCacheAttributeName(String tokenName)  
    {  
        return TOKEN_NAMESPACE + "." + tokenName;  
    }  
      
    /** 
     * 从请求域中获取给定token名字的token值 
     *  
     * @param tokenName 
     * @return the token String or null, if the token could not be found 
     */  
    public static String getToken(HttpServletRequest request)  
    {  
        return request.getParameter(TOKEN_NAME_FIELD);  
    }  
      
    /** 
     * 从请求参数中获取token名字 
     *  
     * @return the token name found in the params, or null if it could not be found 
     */  
    public static String getTokenName(HttpServletRequest request)  
    {  
        return request.getParameter(TOKEN_NAME_FIELD);  
    }  
      
    /** 
     * 验证当前请求参数中的token是否合法，如果合法的token出现就会删除它，它不会再次成功合法的token 
     *  
     * @return 验证结果 
     */  
    public static boolean validToken(HttpServletRequest request)  
    {  
        String token = getToken(request);  
          
        if(token == null)  
        {  
            return false;  
        }  
          
        String cacheToken = (String) redisCacheClient.get(token);  
          
        if(cacheToken==null || !token.equals(cacheToken))  
        {  
            LOG.warn("xxx.internal.invalid.token Form token " + token + " does not match the session token " + cacheToken + ".");  
            return false;  
        }  
          
        // remove the token so it won't be used again   
          
        return true;  
    }  
      
    public static void removeToken(HttpServletRequest request){
    	redisCacheClient.remove(request.getParameter(TOKEN_NAME_FIELD));
    }
    public static String generateGUID()  
    {  
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();  
    }  
      
}  
