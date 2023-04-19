package com.iberdrola.controlhorario.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecurityFilter implements Filter{


    private static String HS512key = null;
    
    @Value("${token.HS512key}")
    public void setNameStatic(String property){
        HS512key = property;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                HttpServletRequest req = (HttpServletRequest) request; 
                HttpServletResponse res = (HttpServletResponse) response;  

                

                String usuarioSession = (String) req.getSession().getAttribute("usuario");



                
                
                if(usuarioSession == null){
                    String usuarioRecibido = null;
                    String token = null;
                    
                    if(req.getCookies() != null){
                        for(Cookie cookier: req.getCookies()){
                            if("token".equals(cookier.getName())){
                                token = cookier.getValue();
                            }
                        }
                    }

                    
                    if(validateToken(token) == null && req.getUserPrincipal() == null){
                        String redirectURL = "/ibersso";
                        if(req.getContextPath() != null){
                            redirectURL = req.getContextPath() + redirectURL;
                        }
                        res.sendRedirect(redirectURL);
                        return;
                    }else{
                        if(req.getUserPrincipal()!= null){

                            usuarioRecibido = req.getUserPrincipal().getName();
                            String newToken = generateToken(usuarioRecibido);
                            Cookie cookie = new Cookie("token", newToken);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            cookie.setPath("/");
                            cookie.setMaxAge(60*60*24*30);
                            
                            res.addCookie(cookie);
                            
                            
                        }else{
                            usuarioRecibido = validateToken(token);
                        }
                    }
                    
                        req.getSession().setAttribute("usuario", usuarioRecibido);
        
                }
                
                    
            
                
                chain.doFilter(req, res);
            
    }

    public static String generateToken(String usuario){
        Map<String, Object> claims = new HashMap<String,Object>();
        claims.put("usuario", usuario);
        Date expirationDate = new Date(System.currentTimeMillis() + 1000*60*60*24);
        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.YEAR, 1);

        return Jwts.builder()
        .setClaims(claims)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, HS512key)
        .compact();
    }
 
    public static String validateToken(String token){
        try{
            
            
            Claims claims = Jwts.parser().setSigningKey(HS512key).parseClaimsJws(token).getBody();
            
            return claims.get("usuario",java.lang.String.class);
        }
        catch(Exception e){
            
            return null;
        }
        
        
        
    
    }
}

