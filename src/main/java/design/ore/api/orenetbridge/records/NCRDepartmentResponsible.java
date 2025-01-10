package design.ore.api.orenetbridge.records;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.api.orenetbridge.abstractions.ValueStorageRecord;
import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.generic.NsItemList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NCRDepartmentResponsible extends ValueStorageRecord
{
	String id;
	String name;
	@JsonProperty("custrecord_ncr_linked_department") NsID linkedDepartment;
	@JsonProperty("custrecord18") NsItemList<NsID> defects;
	
	public NsID toNsID() { return new NsID(id, name); }
	public List<NsID> getDefects()
	{
		if(defects != null) return defects.getItems();
		else return new ArrayList<>();
	}
}
