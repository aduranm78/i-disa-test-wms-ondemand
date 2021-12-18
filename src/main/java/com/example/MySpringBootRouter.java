package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */

@Component
public class MySpringBootRouter extends RouteBuilder {
	
	@Autowired
	private Environment env;

    @Override
    public void configure() throws Exception {
    	
    	String erpUri = "";
		//String erpBase = "https://5298967-sb1.restlets.api.netsuite.com/app/site/hosting/restlet.nl?";
		String erpBase = "https://localhost/app/site/hosting/restlet.nl?";
    	String erpMovements = "script=907&deploy=2";
		String erpAdjusments = "script=907&deploy=2";
		String erpPicking = "script=907&deploy=2";
		String erpReceipt = "script=907&deploy=2";
		String erpShipment = "script=907&deploy=2";
    	
    	onException(HttpOperationFailedException.class)
    		.handled(true)
    		.process(exchange -> {
    			System.out.println("No hay registros en el periodo de consulta");
    			System.out.println(exchange.getProperties());
    		});
    		// .continued(true); // Para continuar con la ruta

		restConfiguration()
			.component("netty-http")
			.port("8080")
			.bindingMode(RestBindingMode.auto);
	  
		rest()
			.path("/").consumes("application/json").produces("application/json")
			  .get("/reprocesar-wms")
		//          .type(Customer.class).outType(CustomerSuccess.class)
				.to("direct:get-customer");

		/*from("direct:get-customer")
			.setHeader("HTTP_METHOD", constant("GET"))
			.to("direct:request");*/

		from("direct:get-customer")
    		.process(exchange -> {
    			String wmsUri = env.getProperty("wms.uri");
				System.out.println("URL WMS: " + wmsUri);
    			Message inMessage = exchange.getIn();
				String query = inMessage.getHeader(Exchange.HTTP_QUERY, String.class);
				System.out.println("Query:"+query);
				if(query != null){
					wmsUri = wmsUri + "shiptment?" +query;
					System.out.println("Query is not null:"+query);
				}else{
					wmsUri = wmsUri + "shiptment";
				}
				erpUri = erpBase + erpShipment;
    	    	exchange.getMessage().setHeader(Exchange.HTTP_QUERY, query);
    	    	exchange.getMessage().setHeader(Exchange.HTTP_URI, wmsUri);
    		})
    		.to("log:DEBUG?showBody=true&showHeaders=true")
    		//.to("https://test?throwExceptionOnFailure=false") // Para no lanzar errores
    		.to("https://wms")
        	.to("log:DEBUG?showBody=true&showHeaders=true")
        	.removeHeaders("*")
        	.setHeader("CamelHttpMethod", constant("POST"))
        	.setHeader(Exchange.HTTP_URI, constant(erpBase))
        	.process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                	String authHeader = OAuthSign.getAuthHeader(erpUri);
                    exchange.getMessage().setHeader("Authorization", authHeader);
                }
        	})
        	.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        	.to("log:DEBUG?showBody=true&showHeaders=true")
        	.to("https://netsuite")
        	//.to("stream:out")
			.streamCaching()
			.log(LoggingLevel.INFO, "${in.headers.CamelFileName}")
			.to("log:DEBUG?showBody=true&showHeaders=true")
			.removeHeaders("*");
    }

}
