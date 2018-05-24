package net.helpscout.playaway.web;

import java.util.Optional;

public interface HttpContext {

    <T> T get(String key, Class<T> expectedType);

    boolean contains(String key);

    <T> void save(String key, T payload);

    Optional<String> getHeader(String headerName);

    String getNullableHeader(String headerName);

    void setHeader(String headerName, String value);

    void setHeader(String headerName, Number value);

    void setLocationHeader(String location);

    void setResourceIdHeader(Object resourceId);

    ResourcePath.PathVariables getPathVariables();

    boolean hasRequestBody();

    Optional<String> getBody();

    Optional<String> getQueryParameter(String parameterName);

}
