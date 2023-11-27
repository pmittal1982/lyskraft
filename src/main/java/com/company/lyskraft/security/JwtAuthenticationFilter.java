package com.company.lyskraft.security;

import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.service.JwtService;
import com.company.lyskraft.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                User user = (User)authToken.getPrincipal();
                // Only User details are accessible if KYC not done.
                if (!request.getRequestURI().equals("/api/v1/user") &&
                        !request.getRequestURI().equals("/api/v2/user") &&
                        (user.getCompany() == null || user.getCompany().getStatus() == CompanyStatus.Deleted)) {
                    logger.info("KYC pending for this user : {}", user.getMobileNumber());
                    generateResponseBody(response, "Unauthorized: KYC pending");
                    return;
                }
                // Admin API can only be accessed by ROOT Users.
                if (request.getRequestURI().startsWith("/api/v1/admin") &&
                        user.getRole() != UserRole.ROOT) {
                    logger.info("Root Access Required : {}", user.getMobileNumber());
                    generateResponseBody(response, "Unauthorized: Not a root user");
                    return;
                }
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void generateResponseBody(HttpServletResponse response, String error) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(error);
    }
}