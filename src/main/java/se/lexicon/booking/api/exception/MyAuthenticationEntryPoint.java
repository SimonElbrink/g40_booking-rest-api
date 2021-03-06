package se.lexicon.booking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import se.lexicon.booking.api.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        APIError apiError = new APIError(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.name(),authException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(JSONUtil.getInstance().getObjectMapper().writeValueAsString(apiError));
    }
}
