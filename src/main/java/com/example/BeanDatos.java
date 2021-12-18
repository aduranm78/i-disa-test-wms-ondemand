package com.example;

import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("beanDatos")
public class BeanDatos {

    private String name;

    private String edad;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEdad() {
        return this.edad;
    }
    
    public String saludo() {
        return "Hola " +  this.getName() + " Edad " + this.getEdad();
    }

}