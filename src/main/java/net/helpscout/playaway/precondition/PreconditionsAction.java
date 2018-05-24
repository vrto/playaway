package net.helpscout.playaway.precondition;

import lombok.val;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.helpscout.playaway.precondition.PreconditionResult.passed;

public class PreconditionsAction {

    private List<Precondition> currentPreconditions = new ArrayList<>();
    private Supplier<ResponseEntity<?>> resultSupplier;

    public PreconditionsAction addPrecondition(Precondition precondition) {
        currentPreconditions.add(precondition);
        return this;
    }

    public PreconditionsAction whenAllPreconditionsMatch(Supplier<ResponseEntity<?>> resultSupplier) {
        this.resultSupplier = resultSupplier;
        return this;
    }

    public ResponseEntity<?> call() {
        val firstFailure = verifyAll();
        return firstFailure.isFailed()
                ? ResponseEntity
                    .status(firstFailure.getViolationPayload().getCode())
                    .body(firstFailure.getViolationPayload())
                : resultSupplier.get();
    }

    protected PreconditionResult verifyAll() {
        return currentPreconditions.stream()
                .map(Precondition::verify)
                .filter(PreconditionResult::isFailed)
                .findFirst()
                .orElse(passed());
    }
}