package com.jxzdoing.classjob.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:jack
 * @date:Create on 2022/6/4 20:44
 */
public class XMLMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringContext.xml");
        Student student1 = (Student) context.getBean("student1");
        System.out.println(student1);
    }
}
