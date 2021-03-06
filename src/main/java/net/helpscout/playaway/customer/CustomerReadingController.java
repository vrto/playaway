package net.helpscout.playaway.customer;

import lombok.RequiredArgsConstructor;
import net.helpscout.playaway.precondition.PreconditionsAction;
import net.helpscout.playaway.stereotype.ReadingController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.inject.Provider;


@ReadingController
@RequiredArgsConstructor
public class CustomerReadingController {

    private final Provider<CustomerPrecondition> customerPreconditionProvider;
    private final CustomerQueries customerQueries;

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable long customerId, @RequestHeader("CompanyID") long companyId) {
        return new PreconditionsAction()
                .addPrecondition(customerPreconditionProvider.get().forCustomer(customerId))
                .whenAllPreconditionsMatch(() -> ResponseEntity.ok(customerQueries.getCustomer(customerId)))
                .call();
    }
}
