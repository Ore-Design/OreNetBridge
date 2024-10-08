package design.ore.OreNetBridge.records;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.generic.NsID;
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
public class NSEmployee
{
	String id;
	String entityId;
	NsID department;
	@JsonProperty("custentity_ore3d_settings") String settings;
	@JsonProperty("custentity_flore_pin") Integer florePin;
	@JsonProperty("custentity_flore_perms") String florePerms;
	
	@JsonProperty("dept_") public void setDepartmentFromId(String id) { department = new NsID(id); }
	
	public List<String> getFlorePermsAsList()
	{
		if(florePerms == null) return List.of();
		return Arrays.asList(florePerms.trim().split(","));
	}
}
