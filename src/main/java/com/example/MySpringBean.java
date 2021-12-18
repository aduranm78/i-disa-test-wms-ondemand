package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("myBean")
public class MySpringBean {

    @Value("${greeting}")
    private String say;
    
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//    System.out.println(formatter.format(date));

    private Date fecha_inicio;
    private Date fecha_fin;

    JSONObject item = new JSONObject();
    
        public String saySomething() {
        	Date date = new Date(System.currentTimeMillis());
        	
        	Calendar c = Calendar.getInstance();
            c.setTime(date);
            
            c.add(Calendar.SECOND, -30);
            fecha_inicio = c.getTime();
            fecha_fin = date;
        	
        	item.put("start", formatter.format(fecha_inicio));
            item.put("end", formatter.format(fecha_fin));
        	
            return item.toString();
        }

}
