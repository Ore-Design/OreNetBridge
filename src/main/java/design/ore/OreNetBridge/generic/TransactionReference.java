package design.ore.OreNetBridge.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReference
{
	String id;
	String type;
	String entity;
	@JsonProperty("custbody_flore_notes") String notes;
	@JsonProperty("tranid") String name;
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TransactionReference)) return false;
		
		TransactionReference ref = (TransactionReference) obj;
		
		return ((id == null && ref.id == null) || id.equals(ref.id)) &&
				((type == null && ref.type == null) || type.equals(ref.type)) &&
				((entity == null && ref.entity == null) || entity.equals(ref.entity)) &&
				((name == null && ref.name == null) || name.equals(ref.name));
	}
}
