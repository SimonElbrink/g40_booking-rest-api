package se.lexicon.booking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import se.lexicon.booking.api.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        APIError apiError = new APIError(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.name(),accessDeniedException);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println(JSONUtil.getInstance().getObjectMapper().writeValueAsString(apiError));

    }
}
