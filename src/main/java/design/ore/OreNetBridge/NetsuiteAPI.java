package design.ore.OreNetBridge;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import design.ore.OreNetBridge.enums.NetsuiteEndpoint;
import design.ore.OreNetBridge.generic.NsID;
import design.ore.OreNetBridge.generic.Query;
import design.ore.OreNetBridge.generic.QueryResults;
import design.ore.OreNetBridge.records.NSEmployee;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class NetsuiteAPI
{
	@Getter private static ObjectMapper mapper;
	@Getter private static Logger logger;
	
	private final String accountID;
	private final String consumerKey;
	private final String consumerSecret;
	private final String tokenID;
	private final String tokenSecret;
	private final String accountRealm;
	
	@Getter @Setter private NSEmployee currentUsingEmployee = null;
	
	@NonNull
	public NetsuiteAPI(ObjectMapper mapper, Logger logger, String accountID, String consumerKey, String consumerSecret, String tokenID, String tokenSecret, String accountRealm)
	{
		NetsuiteAPI.mapper = mapper;
		NetsuiteAPI.logger = logger;
		this.accountID = accountID;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.tokenID = tokenID;
		this.tokenSecret = tokenSecret;
		this.accountRealm = accountRealm;
	}
	
	public URI apiURL(NetsuiteEndpoint endpoint, String destination, boolean expandSubResources)
	{
		try { return new URI(apiURLRaw(endpoint, destination, expandSubResources)); }
		catch (Exception e) { logger.error(e.toString()); return null; }
	}
	
	public String apiURLRaw(NetsuiteEndpoint endpoint, String destination, boolean expandSubResources) throws Exception
	{
		if(endpoint == NetsuiteEndpoint.QUERY) return "https://" + accountID +
			".suitetalk.api.netsuite.com/services/rest/query/v1/suiteql?limit=1000" + (destination == null ? "" : destination);
		else if(endpoint == NetsuiteEndpoint.RESTlet) return "https://" + accountID +
			".restlets.api.netsuite.com/app/site/hosting/restlet.nl?" + destination;
		else if(endpoint == NetsuiteEndpoint.RECORD)
		{
			String url = "https://" + accountID + ".suitetalk.api.netsuite.com/services/rest/record/v1/" + destination;
			if(expandSubResources) url += "?expandSubResources=true";
			return url;
		}
		else throw new Exception("Invalid NetSuite endpoint " + endpoint + "!");
	}

	public String restLocationResponse(String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		OAuthBase oAuth = new OAuthBase();
		HttpResponse response = null;
		
		try { logger.debug("Sending location response request with payload: " + mapper.writeValueAsString(payload)); }
		catch (JsonProcessingException e) { logger.warn(e.getMessage()); }
		
		try(CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			Pair<Long, HttpRequestBase> requestBase = null;
			while(true)
			{
				requestBase = oAuth.generateRequestBase(apiURL(endpoint, destination, method.equalsIgnoreCase("GET")), consumerKey, consumerSecret, tokenID, tokenSecret, method, accountRealm, payload);	
		        response = httpClient.execute(requestBase.getValue());
			
				if(response == null)
				{
					logger.warn("Response " + requestBase.getKey() + " returned null!");
					return "";
				}
				
				if(response.getStatusLine().getStatusCode() != 429) break;
			}
			
			if(response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299)
			{
				logger.warn("Response " + requestBase.getKey() + " returned error code " + response.getStatusLine().getStatusCode() + "!\n" + EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				return "";
			}
			
			for(Header h : response.getAllHeaders())
			{
				if(h.getName().equals("Location")) return h.getValue();
			}
			
			return "";
		}
		catch (Exception e)
		{
			logger.info("Error parsing response data!\n" + Util.throwableToString(e));
			return "";
		}
	}

	public <T> Optional<T> rest(Class<T> clazz, String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		String responseJson = restRaw(destination, method, payload, endpoint);
		if(responseJson.equals("")) return Optional.empty();
		else
		{
			T val;
			try { val = mapper.readValue(responseJson, clazz); return Optional.of(val); }
			catch (JsonProcessingException e) { logger.warn(Util.formatThrowable("Error parsing response JSON", e)); return Optional.empty(); }
		}
	}

	public <T> Optional<T> rest(TypeReference<T> clazz, String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		String responseJson = restRaw(destination, method, payload, endpoint);
		if(responseJson.equals("")) return Optional.empty();
		else
		{
			if(endpoint == NetsuiteEndpoint.RESTlet && responseJson.startsWith("\"") && responseJson.endsWith("\""))
			{
				try { responseJson = mapper.readValue(responseJson, String.class); }
				catch (Exception e) { logger.debug(Util.formatThrowable("Failed to parse RESTlet response from String!", e)); }
			}
			
			T val;
			try { val = mapper.readValue(responseJson, clazz); return Optional.of(val); }
			catch (JsonProcessingException e) { logger.warn("Error parsing response JSON: " + Util.throwableToString(e)); return Optional.empty(); }
		}
	}

	public <T> Optional<QueryResults<T>> query(Class<T> clazz, Query query)
	{
		int offset = 0;
		QueryResults<T> totalResults = new QueryResults<>();
		totalResults.setHasMore(true);
		
		while(totalResults.isHasMore())
		{
			String responseJson = restRaw(offset != 0 ? "&offset=" + offset : null, "POST", query, NetsuiteEndpoint.QUERY);
			if(responseJson.equals("")) return Optional.empty();
			else
			{
				try { totalResults.merge(mapper.readValue(responseJson, mapper.getTypeFactory().constructParametricType(QueryResults.class, clazz))); }
				catch (JsonProcessingException e) { logger.warn("Error parsing response JSON: " + Util.throwableToString(e)); return Optional.empty(); }
			}
			offset += 1000;
		}
		
		return Optional.of(totalResults);
	}

	public String restRaw(String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		OAuthBase oAuth = new OAuthBase();
		HttpResponse response = null;
		
		Pair<Long, HttpRequestBase> requestBase = null;
		try(CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			while(true)
			{
				requestBase = oAuth.generateRequestBase(apiURL(endpoint, destination, method.equalsIgnoreCase("GET")), consumerKey, consumerSecret, tokenID, tokenSecret, method, accountRealm, payload);
				
				if(payload != null)
				{
					try { logger.debug("Sending request " + requestBase.getKey() + " with payload: " + new String(mapper.writeValueAsBytes(payload), StandardCharsets.UTF_8)); }
					catch (JsonProcessingException e) { logger.warn(e.getMessage()); }
				}
				
		        response = httpClient.execute(requestBase.getValue());
			
				if(response == null)
				{
					logger.warn("Response " + requestBase.getKey() + " returned null!");
					return "";
				}
				
				if(response.getStatusLine().getStatusCode() != 429) break;
			}
			
			if(response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299)
			{
				logger.warn("Response " + requestBase.getKey() + " returned error code " + response.getStatusLine().getStatusCode() + "!\n" + EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				return "";
			}
			
			if(response.getEntity() != null)
			{
				String responseJson = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
				logger.debug("Response Body: " + responseJson);
				
				return responseJson;
			}
			else return "Success";
		}
		catch (Exception e)
		{
			if(requestBase != null) logger.warn(Util.formatThrowable("Error sending request " + requestBase.getKey() + "!", e));
			else logger.warn(Util.formatThrowable("Error sending request!", e));
			
			return "";
		}
	}

	public String restNoResponse(String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		OAuthBase oAuth = new OAuthBase();
		HttpResponse response = null;
		
		try(CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			while(true)
			{
				Pair<Long, HttpRequestBase> requestBase = oAuth.generateRequestBase(apiURL(endpoint, destination, method.equalsIgnoreCase("GET")), consumerKey, consumerSecret, tokenID, tokenSecret, method, accountRealm, payload);
		        response = httpClient.execute(requestBase.getValue());
		        
				if(response == null) return "Response returned null!";
				
				if(response.getStatusLine().getStatusCode() != 429) break;
			}
			
			if(response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299)
				return "Response Returned " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase();
			
			return null;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
			return "Error with request, see log file!";
		}
	}

	public <T> T restGeneric(TypeReference<T> type, String destination, String method, Object payload, NetsuiteEndpoint endpoint)
	{
		OAuthBase oAuth = new OAuthBase();
		HttpResponse response = null;
		
        logger.debug("Request URL: " + destination);
        if(payload != null)
        {
			try { logger.debug("Sending " + method + " request with body: " + mapper.writeValueAsString(payload)); }
			catch (JsonProcessingException e) { logger.warn(e.toString()); }
        }
		
		try(CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			Pair<Long, HttpRequestBase> requestBase = null;
			while(true)
			{
				URI dest = apiURL(endpoint, destination, method.equalsIgnoreCase("GET"));
				logger.debug("Sending request: " + dest.toString());

				requestBase = oAuth.generateRequestBase(apiURL(endpoint, destination, method.equalsIgnoreCase("GET")), consumerKey, consumerSecret, tokenID, tokenSecret, method, accountRealm, payload);
		        response = httpClient.execute(requestBase.getValue());
			
				if(response == null)
				{
					logger.warn("Response " + requestBase.getKey() + " returned null!");
					return null;
				}
				
				if(response.getStatusLine().getStatusCode() != 429) break;
			}
			
			if(response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299)
			{
				logger.warn("Response " + requestBase.getKey() + " returned error!\n" + EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				return null;
			}
			
			String responseJson = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			if(responseJson.startsWith("\"")) responseJson = responseJson.substring(1);
			if(responseJson.endsWith("\"")) responseJson = responseJson.substring(0, responseJson.length() - 1);
			responseJson = responseJson.replace("\\\"", "\"");
			logger.debug("Response Body: " + responseJson);
			
			return mapper.readValue(responseJson, type);
		}
		catch (Exception e)
		{
			logger.warn("Error parsing response data!");
			e.printStackTrace();
			return null;
		}
	}
	
	public NSEmployee getEmployee(String id)
	{
		Optional<NSEmployee> emp = rest(NSEmployee.class, "employee/" + id, "GET", null, NetsuiteEndpoint.RECORD);
		if(emp.isPresent()) return emp.get();
		else return null;
	}
	
	public NsID findItem(String sku)
	{
		Query q = new Query("SELECT id FROM item WHERE itemId LIKE '" + sku + "'");
		
		Optional<QueryResults<NsID>> results = query(NsID.class, q);
		
		if(results.isPresent() && results.get().getItems().size() == 1) return results.get().getItems().get(0);
		else return null;
	}
	
	public NsID findRouting(String routingName)
	{
		Query q = new Query("SELECT id FROM manufacturingRouting WHERE name LIKE '" + routingName + "'");
		
		Optional<QueryResults<NsID>> results = query(NsID.class, q);
		
		if(results.isPresent() && results.get().getItems().size() == 1) return results.get().getItems().get(0);
		else return null;
	}
	
	public NsID findBOM(String bomName)
	{
		Query q = new Query("SELECT id FROM bom WHERE name LIKE '" + bomName + "'");
		
		Optional<QueryResults<NsID>> results = query(NsID.class, q);
		
		if(results.isPresent() && results.get().getItems().size() == 1) return results.get().getItems().get(0);
		else return null;
	}
}
