package com.polozov.springeshop.service;

import com.polozov.springeshop.ws.greeting.Greeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

class GreetingServiceTest {

    @Test
    void generateGreeting() throws DatatypeConfigurationException {
        //have
        GreetingService service = new GreetingService();

        String name = "Petr";
        LocalDate expectedDate = LocalDate.now();

        //execute
        Greeting greeting = service.generateGreeting(name);
        //check
        Assertions.assertNotNull(greeting);
        Assertions.assertEquals("Hello, " + name, greeting.getText());

        XMLGregorianCalendar date = greeting.getDate();
        Assertions.assertEquals(expectedDate.getYear(), date.getYear());
        Assertions.assertEquals(expectedDate.getMonthValue(), date.getMonth());
        Assertions.assertEquals(expectedDate.getDayOfMonth(), date.getDay());
    }
}
