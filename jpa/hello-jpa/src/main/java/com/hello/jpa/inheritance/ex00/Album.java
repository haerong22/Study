package com.hello.jpa.inheritance.ex00;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
}
