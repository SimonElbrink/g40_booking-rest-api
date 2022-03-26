package se.lexicon.booking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import se.lexicon.booking.api.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
      throws IOException {
        response.setContentType("application/json");
        APIError apiError = new APIError(HttpStatus.UNAUTHORIZED,"Unauthorized",authenticationException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(JSONUtil.getInstance().getObjectMapper().writeValueAsString(apiError));
    }

    public void afterPropertiesSet() {
        setRealmName("booking-realm");
        super.afterPropertiesSet();
    }
}