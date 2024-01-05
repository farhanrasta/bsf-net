package com.farhan.bsfnet.service.Impl;

import com.farhan.bsfnet.dto.JwtData;
import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.UserResponse;
import com.farhan.bsfnet.repository.UserLogonRepository;
import com.farhan.bsfnet.service.AuthService;
import com.farhan.bsfnet.service.JwtService;
import com.farhan.bsfnet.service.LoginService;
import com.farhan.bsfnet.util.AppUtil;
import com.farhan.bsfnet.util.CipherUtil;
import com.farhan.bsfnet.util.MD5Util;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("30")
    private Integer tokenExpire;

    @Autowired
    private UserLogonRepository userLogonRepository;

    @Autowired
    private LoginService loginService;

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMddHHmmss");

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public JwtResponse generate(HttpServletRequest httpServletRequest, UserResponse userResponse) {

        Calendar calnow = Calendar.getInstance();
        Calendar calexp1 = Calendar.getInstance();
        calexp1.set(Calendar.MINUTE, calnow.get(Calendar.MINUTE)+tokenExpire);

        Calendar calexp = Calendar.getInstance();
        calexp.setTime(calexp1.getTime());
        if(tokenExpire>7200) {calexp.set(Calendar.HOUR_OF_DAY, 23);}
        calexp.set(Calendar.MINUTE, 59);
        calexp.set(Calendar.SECOND, 59);

        Long userId = userResponse.getId();

        String jtiBuilder = userId +
                sdf4.format(calnow.getTime());
        String jti = MD5Util.generate(jtiBuilder);
        String userAgent = AppUtil.getUserAgent(httpServletRequest);

        loginService.loginSave(userId, jti, userAgent);

        JwtData jwtData = JwtData.builder()
                .date(Calendar.getInstance().getTime())
                .f1(userResponse.getId()!=null?userResponse.getId():null)
                .f2(userResponse.getFirstname()!=null? CipherUtil.encrypt(userResponse.getFirstname()) :null)
                .f3(userResponse.getLastname()!=null? CipherUtil.encrypt(userResponse.getLastname()):null)
                .f4(userResponse.getUsername()!=null? CipherUtil.encrypt(userResponse.getUsername()):null)
                .f5(userResponse.getIsUserToken()!=null?userResponse.getIsUserToken():false)
                .build();

        Map<String, Object> claims = new HashMap<>();
        claims.put("jwtData", jwtData);
        claims.put("ip", AppUtil.getClientIp(httpServletRequest));
        claims.put("userAgent", userAgent);

        StringBuilder builder = new StringBuilder();
        builder.append("USER_");
        builder.append(userResponse.getId());

        String jwtToken = Jwts.builder()
                .setSubject(builder.toString())
                .setClaims(claims)
                .setIssuedAt(calnow.getTime())
                .signWith(SignatureAlgorithm.HS256, "8ypZSm5FUuPKmBFTVwJ2gzkMxAjqE")
                .setExpiration(calexp.getTime())
                .setId(jti)
                .compact();

        return JwtResponse.builder()
                .token(jwtToken)
                .expiredDate(sdf1.format(calexp.getTime()))
                .build();

    }

    @Override
    public JwtResponse filter(HttpServletRequest httpServletRequest) {

        String authHeader = httpServletRequest.getHeader("authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        final String token = authHeader.substring(7);

        try {
            final Claims claims = Jwts.parser().setSigningKey("8ypZSm5FUuPKmBFTVwJ2gzkMxAjqE").parseClaimsJws(token).getBody();

            //String subject = claims.getSubject();
            //Date expDate = claims.getExpiration();
            String jti = claims.getId();
//			String ipJwt = (String) claims.get("ip");
//			String userAgentJwt = (String) claims.get("userAgent");

//			String ipIn = AppUtil.getClientIp(httpreq);
//			String uaIn = AppUtil.getUserAgent(httpreq);

//			if(!(ipJwt.equalsIgnoreCase(ipIn) && userAgentJwt.equalsIgnoreCase(uaIn))) {
//				log.info("IP-JWT: "+ipJwt+"; IP-In: "+ipIn+";");
//				log.info("UA-JWT: "+userAgentJwt+"; UA-In: "+uaIn+";");
//				throw new GlobalException("004", HttpStatus.BAD_REQUEST);
//			}

            Map<String, Object> map = (Map<String, Object>) claims.get("jwtData");
            //log.info("Map: "+new Gson().toJson(map));

            Integer f1 = (Integer) map.get("f1");
            String f2 = (String) map.get("f2");
            String f3 = (String) map.get("f3");
            String f4 = (String) map.get("f4");
            Boolean f5 = (Boolean) map.get("f5");

            Long id = Long.valueOf(f1);
            String firstname = f2!=null?CipherUtil.decrypt(f2):null;
            String lastname = f3!=null?CipherUtil.decrypt(f3):null;
            String username = f4!=null?CipherUtil.decrypt(f4):null;

            Boolean exists = userLogonRepository.existsByUserIdAndJti(id, jti);
            if(!exists) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token digunakan User lain");
            }

            return JwtResponse.builder()
                    .id(id)
                    .firstName(firstname )
                    .lastName(lastname)
                    .username(username)
                    .isUserToken(f5)
                    .build();

        } catch (final SignatureException e) {
            log.info("SignatureException: "+e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SignatureException");
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException: "+e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ExpiredJwtException");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ExceptionJwt: "+e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ExceptionJwt");
        }
    }
}
