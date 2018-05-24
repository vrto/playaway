package net.helpscout.playaway.customer;

import lombok.RequiredArgsConstructor;
import lombok.val;
import net.helpscout.playaway.precondition.PreconditionsAction;
import net.helpscout.playaway.stereotype.WritingController;
import net.helpscout.playaway.web.HttpContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Provider;

@WritingController
@RequiredArgsConstructor
public class CustomerWritingController {

    public static final String NEW_CUSTOMER = "new_customer";

    private final HttpContext httpContext;
    private final Provider<ValidCustomerPrecondition> customerPrecondition;
    private final CustomerCommands customerCommands;

    @PostMapping("/customers")
    ResponseEntity<?> saveCustomer() {
        return new PreconditionsAction()
                .addPrecondition(customerPrecondition.get().saveWithKey(NEW_CUSTOMER))
                .whenAllPreconditionsMatch(() -> {
                    val customer = httpContext.get(NEW_CUSTOMER, CustomerEntity.class);
                    customerCommands.saveCustomer(customer);
                    return ResponseEntity.status(201).build();
                })
                .call();
    }
}
