package com.example.obhibernateproyecto.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ob_billing_info")
public class BillingInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String street;

    @Column(name="postal_code")
    private String postalCode;

    private String city;

    private String country;

    private String iban;

    @OneToOne(mappedBy="billingInfo")
    private User user;

    // CONSTRUCTORES

    public BillingInfo(){};

    public BillingInfo(User user, String iban, String country, String city, String postalCode, String street, Long id) {
        this.user = user;
        this.iban = iban;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.id = id;
    }

    //GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        //Conviene no poner las asociaciones (en este caso 'user'), ya que si llamamos al proceso, este llamará a user, que a su vez
        //llama a BillingInfo, estableciéndose una recursion.
        return "BillingInfo{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
