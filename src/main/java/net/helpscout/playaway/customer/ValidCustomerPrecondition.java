package net.helpscout.playaway.customer;

import net.helpscout.playaway.precondition.PreconditionResult;
import net.helpscout.playaway.precondition.ValidPayloadPrecondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static net.helpscout.playaway.precondition.PreconditionResult.badRequest;
import static net.helpscout.playaway.precondition.PreconditionResult.passed;

@Component
@Scope("prototype")
public class ValidCustomerPrecondition extends ValidPayloadPrecondition<CustomerEntity> {

    public ValidCustomerPrecondition() {
        this.payloadType = CustomerEntity.class;
    }

    @Override
    public PreconditionResult verify() {
        addAdditionalCheck(req -> req.getLast().equals("Kustomer")? badRequest("Kustomer already taken") : passed());
        return super.verify();
    }
}
