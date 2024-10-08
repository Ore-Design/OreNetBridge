package design.ore.OreNetBridge.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class NsID
{
	String id;
	String refName;
	
	public NsID(String id) { this.id = id; }
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof NsID)) return false;
		
		NsID nsid = (NsID) obj;
		
		return nsid.getId().equals(id);
	}
}
