package net.helpscout.playaway.precondition;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import net.helpscout.playaway.web.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static net.helpscout.playaway.precondition.PreconditionResult.passed;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED) // shall be either extended or instantiated using javax.inject.Provider
public class ValidPayloadPrecondition<T> implements Precondition {

    @Setter(onMethod = @__(@Autowired))
    protected HttpContext httpContext;

    @Setter(onMethod = @__(@Autowired))
    private ObjectMapper objectMapper;

    @Setter(onMethod = @__(@Autowired))
    private Validator defaultValidator;

    protected Class<? extends T> payloadType;
    protected String key;
    private List<Function<? super T, PreconditionResult>> additionalChecks = new ArrayList<>();

    public ValidPayloadPrecondition<T> of(Class<T> payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public ValidPayloadPrecondition<T> saveWithKey(String key) {
        this.key = key;
        return this;
    }

    protected void addAdditionalCheck(Function<? super T, PreconditionResult> check) {
        additionalChecks.add(check);
    }

    @Override
    public PreconditionResult verify() {
        T payload = bindFromRequest(httpContext.getBody().get());

        val violations = defaultValidator.validate(payload);
        if (!violations.isEmpty()) {
            return PreconditionResult.badRequest(violations);
        }

        Optional<PreconditionResult> firstFailure = additionalChecks.stream()
                .map(check -> check.apply(payload))
                .filter(PreconditionResult::isFailed)
                .findFirst();

        if (firstFailure.isPresent()) {
            return firstFailure.get();
        }

        if (key != null) {
            httpContext.save(key, payload);
        }

        // no failures, all good
        return passed();
    }

    // overridable if needed
    @SneakyThrows
    protected T bindFromRequest(String body) {
        return objectMapper.readValue(body, payloadType);
    }
}
