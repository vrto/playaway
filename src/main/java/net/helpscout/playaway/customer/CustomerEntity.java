package net.helpscout.playaway.customer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customers")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @NotEmpty
    @Column(name = "first_name")
    String first;

    @NotEmpty
    @Column(name = "last_name")
    String last;

    public CustomerEntity(String first, String last) {
        this.first = first;
        this.last = last;
    }
}
