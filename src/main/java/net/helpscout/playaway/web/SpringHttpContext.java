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
        return (T) getAttributeFromRequestContext(key);
    }

    @Override
    public boolean contains(String key) {
        return getAttributeFromRequestContext(key) != null;
    }

    private Object getAttributeFromRequestContext(String key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public <T> void save(String key, T payload) {
        RequestContextHolder.getRequestAttributes().setAttribute(key, payload, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public Optional<String> getHeader(String headerName) {
        // can use @RequestHeader instead
        return Optional.empty();
    }

    @Override
    public String getNullableHeader(String headerName) {
        // can use @RequestHeader instead
        return null;
    }

    @Override
    public void setHeader(String headerName, String value) {
        // can use ResponseEntity.header
    }

    @Override
    public void setHeader(String headerName, Number value) {
        // can use ResponseEntity.header
    }

    @Override
    public void setLocationHeader(String location) {
        // can use ResponseEntity.created(URI)
    }

    @Override
    public void setResourceIdHeader(Object resourceId) {
        // can use ResponseEntity.header
    }

    @Override
    public ResourcePath.PathVariables getPathVariables() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new ResourcePath(request.getRequestURI()).parse();
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
        // can use @RequestParam
        return Optional.empty();
    }
}
