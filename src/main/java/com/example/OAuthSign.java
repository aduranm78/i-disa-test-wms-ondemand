package com.example;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.methods.HttpPost;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.HmacSha256MessageSigner;

public class OAuthSign {
	public static String getAuthHeader(String uri) throws IOException {           
	    String consumer_key = "594f75ba092ebe059027f6af0a6eb3b78cdb94419282f35c1de6b91803d708aa";
		String consumer_secret = "eba80a6a6f7374c5ba30220ca2fc7d4bf67db5120b8b327effadfe21c558de03";
		String access_token = "1baa836e6e43d0c06aea6eb30a7233ae1974969a367092574b95f5b2a715bc98";
		String access_secret= "e74aebe07b3827288b18776788d20b68ceae94777e4d26765884176fbabfa5b2";

	    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumer_key, consumer_secret);
	    consumer.setMessageSigner(new HmacSha256MessageSigner());
	    consumer.setTokenWithSecret(access_token, access_secret);
	    
	    HttpPost httppost= new HttpPost(uri);
	    
	    try {
	        HttpRequest signedReq = consumer.sign(httppost);
	        String realm = "OAuth realm=\"5298967_SB1\",";
	        return signedReq.getHeader("Authorization").toString().replace("OAuth", realm);
	    } catch (OAuthMessageSignerException ex) {
	        Logger.getLogger(HttpPost.class.getName()).log(Level.SEVERE, null, ex);
	        return ex.getMessage();
	    } catch (OAuthExpectationFailedException ex) {
	        Logger.getLogger(HttpPost.class.getName()).log(Level.SEVERE, null, ex);
	        return ex.getMessage();
	    } catch (OAuthCommunicationException ex) {
	        Logger.getLogger(HttpPost.class.getName()).log(Level.SEVERE, null, ex);
	        return ex.getMessage();
	    }
	    
	    // HttpParameters httpParams = consumer.getRequestParameters();
	    // Set<String> paramKeys = httpParams.keySet();
	    
	    // for (String k : paramKeys) {
	    // 	System.out.println(httpParams.getAsHeaderElement(k));
	    // }
	    // System.out.println(httpParams.getAsHeaderElement("oauth_signature"));
	}
}
