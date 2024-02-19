package org.example;

import org.junit.Assert;
import org.junit.Test;

public class MoimTest {

    @Test
    public void isFull() throws Exception {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 100;
        moim.numberOfEnrollment = 10;
        Assert.assertFalse(moim.isEnrollmentFull());
    }
}