package ai.techfin.tradesystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删掉csrfFilter中设置的cookie
 */
@Component
public class CsrfCookieFilterFilter extends OncePerRequestFilter {

    private final CsrfTokenRepository csrfTokenRepository;

    @Autowired
    public CsrfCookieFilterFilter(CsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (request.getRequestURI().equals(SecurityConfiguration.LOGIN_URI) && request.getMethod().equals("POST")) {
            logger.info("A new auth req");
        } else {
            if (csrfTokenRepository.loadToken(request) == null) {
                logger.info("A new evil req");
                Cookie cookie = new Cookie("XSRF-TOKEN", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setSecure(request.isSecure());
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }

        filterChain.doFilter(request, response);
    }

}
