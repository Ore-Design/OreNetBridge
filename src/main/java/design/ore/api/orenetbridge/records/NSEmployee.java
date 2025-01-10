package design.ore.api.orenetbridge.records;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.api.orenetbridge.generic.NsID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(Include.NON_NULL)
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
	@JsonProperty("custentity_flore_day_goal_override") Integer dayMinGoalOverride;
	@JsonProperty("custentity_flore_week_time_goal_override") Integer weekMinGoalOverride;
	
	@JsonProperty("dept_") public void setDepartmentFromId(String id) { department = new NsID(id); }
	
	@JsonIgnore public List<String> getFlorePermsAsList()
	{
		if(florePerms == null) return List.of();
		return Arrays.asList(florePerms.trim().split(","));
	}
}
