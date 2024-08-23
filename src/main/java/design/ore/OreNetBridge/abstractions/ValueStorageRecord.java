package design.ore.OreNetBridge.abstractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import design.ore.OreNetBridge.NetsuiteAPI;
import design.ore.OreNetBridge.Util;
import lombok.Getter;

public abstract class ValueStorageRecord
{
	@JsonIgnore @Getter private Map<String, StoredValue> storedValues = new HashMap<String, StoredValue>();

	@JsonIgnore public StoredValue getStoredValue(String key) { return storedValues.get(key); }
	@JsonIgnore public StoredValue putStoredValue(String key, StoredValue value) { return storedValues.put(key, value); }
	@JsonIgnore public StoredValue putStoredValue(String key, String value, boolean userViewable, boolean userEditable) { return putStoredValue(key, new StoredValue(value, userViewable, userEditable)); }
	@JsonIgnore public void putStoredValues(Map<String, StoredValue> values) { this.storedValues.putAll(values); }
	@JsonIgnore public StoredValue removeStoredValue(String key) { return this.storedValues.remove(key); }
	@JsonIgnore
	public StoredValue putStoredValueIgnoringKeyCase(String key, StoredValue value)
	{
		removeValueFromKeyIgnoreKeyCase(key);
		return storedValues.put(key, value);
	}
	@JsonIgnore
	public StoredValue putStoredValueIgnoringKeyCase(String key, String value, boolean userViewable, boolean userEditable)
	{
		removeValueFromKeyIgnoreKeyCase(key);
		return putStoredValue(key, new StoredValue(value, userViewable, userEditable));
	}
	@JsonIgnore
	public void removeValueFromKeyIgnoreKeyCase(String key)
	{
		List<String> keysToRemove = new ArrayList<>();
		for(Entry<String, StoredValue> entry : storedValues.entrySet())
		{ if(entry.getKey().equalsIgnoreCase(key)) keysToRemove.add(entry.getKey()); }
		
		for(String str : keysToRemove) storedValues.remove(str);
	}
	
	@JsonAnySetter
	public void addAdditionalValue(String name, JsonNode value)
	{
		putStoredValue(name, value.toString(), true, false);
	}
	
	@JsonAnyGetter
	public Map<String, JsonNode> getAdditionalValues() throws Exception
	{
		Map<String, JsonNode> values = new HashMap<>();
		for(Entry<String, StoredValue> entry : getStoredValues().entrySet())
		{
			if(entry.getKey() != null && !entry.getKey().equals("") && entry.getValue() != null && entry.getValue().getValue() != null && !entry.getValue().getValue().equals(""))
			{
				if(NetsuiteAPI.getMapper() == null) throw new Exception ("Netsuite API Mapper is not initialized!");
				if(NetsuiteAPI.getLogger() == null) throw new Exception ("Netsuite API Logger is not initialized!");
				
				try { values.put(entry.getKey(), NetsuiteAPI.getMapper().readValue(entry.getValue().getValue(), JsonNode.class)); }
				catch (Exception e)
				{
					try { values.put(entry.getKey(), NetsuiteAPI.getMapper().readValue("\"" + entry.getValue().getValue() + "\"", JsonNode.class)); }
					catch (Exception ex) { NetsuiteAPI.getLogger().warn(Util.formatThrowable("Error serializing stored value from class type '" + getClass().toString() + "'! Skipping!", e)); }
				}
			}
			else NetsuiteAPI.getLogger().warn("Stored value with key " + entry.getKey() + " has null/empty data! Skipping...");
		}
		return values;
	}
}
