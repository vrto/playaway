package net.helpscout.playaway.web;

import lombok.AllArgsConstructor;

import java.util.Optional;

public class ResourcePath {

    private final String[] pathParts;

    public ResourcePath(String uri) {
        this.pathParts = uri.split("/");
    }

    public PathVariables parse() {
        return new PathVariables(source(), companyId(), userId(), conversationId());
    }

    private Optional<Long> companyId() {
        return pathParts.length > 3 ? asId(pathParts[3]) : Optional.empty();
    }

    private Optional<Long> userId() {
        return pathParts.length > 4 ? asId(pathParts[4]) : Optional.empty();
    }

    private String source() {
        return pathParts.length > 2 ? pathParts[2] : "unspecified_source";
    }

    private Optional<Long> conversationId() {
        return pathParts.length > 6 ? asId(pathParts[6]) : Optional.empty();
    }

    private Optional<Long> asId(String maybeNumber) {
        try {
            return Optional.of(Long.parseLong(maybeNumber));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @AllArgsConstructor
    public static class PathVariables {
        public final String source;
        public final Optional<Long> companyId;
        public final Optional<Long> userId;
        public final Optional<Long> conversationId;
    }

}
