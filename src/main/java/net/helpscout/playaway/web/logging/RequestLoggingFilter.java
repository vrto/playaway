package net.helpscout.playaway.web.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long requestStartTimeMs = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long requestTotalTimeMs = System.currentTimeMillis() - requestStartTimeMs;
            logRequest(request, response, requestTotalTimeMs);
        }
    }

    @SneakyThrows(JsonProcessingException.class)
    private void logRequest(HttpServletRequest request, HttpServletResponse response, long requestTotalTimeMs) {
        val requestLine = RequestLogLine.builder()
                .method(request.getMethod())
                .url(getUrl(request))
                .status(response.getStatus())
                .timeMs(requestTotalTimeMs)
                .userAgent(getHeader(request, "User-Agent"))
                .timestamp(ZonedDateTime.now(ZoneOffset.UTC).toString())
                .build();

        log.info(objectMapper.writeValueAsString(requestLine));
    }

    private String getHeader(HttpServletRequest request, String header) {
        return Optional.ofNullable(request.getHeader(header)).orElse("-");
    }

    private String getUrl(HttpServletRequest request) {
        val requestUrl = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestUrl.append('?').append(request.getQueryString());
        }
        return requestUrl.toString();
    }

    @Value
    @Builder
    static class RequestLogLine {
        String method;
        String url;
        int status;
        long timeMs;
        String ip;
        String userAgent;
        String timestamp;
    }

}
