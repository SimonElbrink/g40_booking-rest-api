package se.lexicon.booking.api.exception;

import lombok.SneakyThrows;
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
public class  MyAccessDeniedHandler implements AccessDeniedHandler {

    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        APIError apiError = new APIError(HttpStatus.FORBIDDEN,"Forbidden",e);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println(JSONUtil.getInstance().getObjectMapper().writeValueAsString(apiError));
    }
}