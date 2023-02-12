package com.entity.performancemonitor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.Month;

public class PerfomanceApp {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfiguration.class);
        Person person = new Person("John","Smith", LocalDate.of(1980, Month.JANUARY, 12));
        PersonService personService = (PersonService) context.getBean("personService");
        System.out.println("Name is:"+personService.getFullName(person));
        System.out.println("Age is:"+personService.getAge(person));
    }
}
