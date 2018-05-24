package net.helpscout.playaway.web;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class SpringHttpContext implements HttpContext {

    @Override
    public <T> T get(String key, Class<T> expectedType) {
        return (T) RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public boolean contains(String key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST) != null;
    }

    @Override
    public <T> void save(String key, T payload) {
        RequestContextHolder.getRequestAttributes().setAttribute(key, payload, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public Optional<String> getHeader(String headerName) {
        return Optional.empty();
    }

    @Override
    public String getNullableHeader(String headerName) {
        return null;
    }

    @Override
    public void setHeader(String headerName, String value) {

    }

    @Override
    public void setHeader(String headerName, Number value) {

    }

    @Override
    public void setLocationHeader(String location) {

    }

    @Override
    public void setResourceIdHeader(Object resourceId) {

    }

    @Override
    public ResourcePath.PathVariables getPathVariables() {
        return null;
    }

    @Override
    @SneakyThrows
    public boolean hasRequestBody() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getReader().lines().findAny().isPresent();
    }

    @Override
    @SneakyThrows
    public Optional<String> getBody() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        return Optional.of(body);
    }

    @Override
    public Optional<String> getQueryParameter(String parameterName) {
        return Optional.empty();
    }
}
