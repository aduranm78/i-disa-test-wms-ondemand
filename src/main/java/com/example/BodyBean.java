package com.example;

import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("bodyBean")
public class BodyBean {

    private String folio;

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return this.folio;
    }

}