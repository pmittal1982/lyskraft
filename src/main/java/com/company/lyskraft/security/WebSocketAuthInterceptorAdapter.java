package com.company.lyskraft.security;

import com.company.lyskraft.service.JwtService;
import com.company.lyskraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptorAdapter implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;
    private final UserService userService;
    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {

        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand cmd = accessor.getCommand();

        if (StompCommand.CONNECT == cmd || StompCommand.SEND == cmd || StompCommand.SUBSCRIBE == cmd) {

            final String authHeader = accessor.getFirstNativeHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
                throw new AccessDeniedException("Access denied");
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);
            if (StringUtils.isNotEmpty(userEmail)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    accessor.setUser(authToken);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }
        return message;
    }
}