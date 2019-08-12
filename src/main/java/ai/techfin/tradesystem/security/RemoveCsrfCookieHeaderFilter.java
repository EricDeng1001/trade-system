package ai.techfin.tradesystem.security;

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
public class RemoveCsrfCookieHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        Cookie cookie = new Cookie("XSRF-TOKEN", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        filterChain.doFilter(request, response);
    }

}
