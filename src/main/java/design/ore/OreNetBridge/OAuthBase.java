package design.ore.OreNetBridge;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha256MessageSigner;

public class OAuthBase
{
	protected Random random = new Random();
	
	public Pair<Long, HttpRequestBase> generateRequestBase(URI url, String consumerKey, String consumerSecret, String token, String tokenSecret, String httpMethod, String realm, Object payload) throws Exception
	{		
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(token, tokenSecret);
        HmacSha256MessageSigner messageSigner = new HmacSha256MessageSigner();
        messageSigner.setTokenSecret(tokenSecret); //this is required due to a bug in library
        consumer.setMessageSigner(messageSigner);
        consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());

        HttpParameters params = new HttpParameters();
        params.put("oauth_timestamp", generateTimeStamp());
        params.put("oauth_signature_method", "HMAC-SHA256");
        params.put("oauth_nonce", generateNonce());
        params.put("oauth_version", "1.0");
        params.put("realm", realm);
        consumer.setAdditionalParameters(params);
        
        StringEntity json = null;
        if(payload != null)
        {
        	if(httpMethod.equalsIgnoreCase("GET") || httpMethod.equalsIgnoreCase("DELETE")) throw new Exception("GET and DELETE methods cannot contain a payload, so payload will be ignored!");
        	
        	ObjectMapper map = new ObjectMapper();
        	map.registerModule(new JavaTimeModule());
        	map.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        	
        	byte[] jsonString = map.writeValueAsBytes(payload);
        	json = new StringEntity(new String(jsonString, StandardCharsets.ISO_8859_1));
        }
        
        HttpRequestBase request;
        if(httpMethod.toUpperCase().equals("GET"))
        {
        	request = new HttpGet(url);
        }
        else if(httpMethod.toUpperCase().equals("POST"))
        {
        	request = new HttpPost(url);
            if(json != null) ((HttpPost) request).setEntity(json);
        }
        else if(httpMethod.toUpperCase().equals("PATCH"))
        {
        	request = new HttpPatch(url);
            if(json != null) ((HttpPatch) request).setEntity(json);
        }
        else if(httpMethod.toUpperCase().equals("DELETE"))
        {
        	request = new HttpDelete(url);
        }
        else
        {
        	throw new Exception("Unhandled REST method " + httpMethod);
        }
    	request.setHeader("Content-type", "application/json");
        request.setHeader("Prefer", "transient");
        consumer.sign(request);
        return new Pair<>(random.nextLong(), request);
    }
    
    public String generateTimeStamp() { return String.valueOf(System.currentTimeMillis() / 1000L); }
    public String generateNonce() { return "" + random.nextInt(123400, 9999999); }
}
