package net.helpscout.playaway.customer;

import lombok.RequiredArgsConstructor;
import net.helpscout.playaway.stereotype.Commands;

@Commands
@RequiredArgsConstructor
public class CustomerCommands {

    private final CustomerRepository customerRepository;

    void saveCustomer(CustomerEntity customer) {
        customerRepository.save(customer);
    }
}
