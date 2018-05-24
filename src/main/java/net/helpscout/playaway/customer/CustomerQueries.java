package net.helpscout.playaway.customer;

import lombok.RequiredArgsConstructor;
import net.helpscout.playaway.stereotype.Queries;

@Queries
@RequiredArgsConstructor
public class CustomerQueries {

    private final CustomerRepository customerRepository;

    CustomerEntity getCustomer(long customerId) {
        // existence verified by precondition
        return customerRepository.findById(customerId).get();
    }
}
