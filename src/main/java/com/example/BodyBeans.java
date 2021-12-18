package com.example;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("bodyBeans")
public class BodyBeans {

    private List<BodyBean> folios;

    public void setFolios(List<BodyBean> folios) {
        this.folios = folios;
    }

    public List<BodyBean> getFolios() {
        return this.folios;
    }

}