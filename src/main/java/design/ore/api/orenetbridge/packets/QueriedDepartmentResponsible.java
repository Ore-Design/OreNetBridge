package design.ore.api.orenetbridge.packets;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.api.orenetbridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class QueriedDepartmentResponsible
{
	@Getter @Setter String id, name, department;
	Integer dayMinGoalOverride, weekMinGoalOverride;
	
	@Getter private final List<String> defectsList = new ArrayList<>();
	
	public void setDefects(String defects)
	{
		defectsList.clear();
		for(String str : defects.replaceAll(" " , "").split(","))
		{ defectsList.add(str); }
	}
	
	public NsID toNsID() { return new NsID(id, name); }
}
