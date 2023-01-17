package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String streets;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String streets, String zipcode) {
        this.city = city;
        this.streets = streets;
        this.zipcode = zipcode;
    }
}
