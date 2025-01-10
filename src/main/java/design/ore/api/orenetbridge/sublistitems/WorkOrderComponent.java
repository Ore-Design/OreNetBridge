package design.ore.api.orenetbridge.sublistitems;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.api.orenetbridge.generic.NsID;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class WorkOrderComponent
{
	@Getter @Setter NsID item;
	@Getter @Setter double quantity;
	@Getter @Setter NsID commitInventory;
	@Getter @Setter NsID itemSource;
	@Getter @Setter @JsonProperty("custcol_ore3d_work_order") NsID workOrder;

	public WorkOrderComponent() {}

	public WorkOrderComponent(String internalID, double quantity, boolean isPhantom, NsID workOrder)
	{
		this.item = new NsID(internalID);
		this.quantity = quantity;
		
		if(isPhantom)
		{
			commitInventory = new NsID("3");
			itemSource = new NsID("PHANTOM");
		}
		else
		{
			commitInventory = new NsID("1");
			itemSource = new NsID("STOCK");
			if(workOrder != null) this.workOrder = workOrder;
		}
	}
}
