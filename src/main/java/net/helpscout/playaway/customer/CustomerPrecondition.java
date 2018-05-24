package net.helpscout.playaway.customer;

import lombok.RequiredArgsConstructor;
import net.helpscout.playaway.precondition.Precondition;
import net.helpscout.playaway.precondition.PreconditionResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPrecondition implements Precondition {

    private final CustomerRepository customerRepository;

    private Long customerId;

    CustomerPrecondition forCustomer(long customerId) {
        this.customerId = customerId;
        return this;
    }

    @Override
    public PreconditionResult verify() {
        return customerRepository.existsById(customerId)
                ? PreconditionResult.passed()
                : PreconditionResult.notFound("The customer doesn't exist!");
    }
}
