package design.ore.api.orenetbridge.sublistitems;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.generic.NsItemList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@NoArgsConstructor
public class BOMRecordComponent
{
	NsID billOfMaterials;
	NsItemList<NsID> defaultForLocation;
	
	public BOMRecordComponent(String bomID, String defaultForLocation)
	{
		this.billOfMaterials = new NsID(bomID);
		this.defaultForLocation = new NsItemList<>();
		this.defaultForLocation.addItem(new NsID(defaultForLocation));
	}
	
	public BOMRecordComponent(NsID bom, boolean setDefault)
	{
		this.billOfMaterials = bom;
		this.defaultForLocation = new NsItemList<>();
		if(setDefault) this.defaultForLocation.addItem(new NsID("3")); // Woods Cross
	}
}
