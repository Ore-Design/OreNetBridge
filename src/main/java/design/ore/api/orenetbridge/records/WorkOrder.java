package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.generic.NsItemList;
import design.ore.api.orenetbridge.sublistitems.WorkOrderComponent;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class WorkOrder
{
	@JsonProperty("custbody_ore3d_sales_order") NsID salesOrderID;
	@JsonProperty("custbody_ore3d_child_item") NsID childAssembly;
	@JsonProperty("custbody_parentwo") NsID parentWO;
	NsID manufacturingRouting;
	NsID assemblyItem;
	NsID location;
	NsID entity;
	NsID billOfMaterials;
	NsID orderStatus;
	@JsonProperty("custbody_square_feet") Double squareFeet;
	Double quantity;
	@JsonProperty("isWip") Boolean isWip;
	@JsonProperty("custbody_exp_ship_date") String expectedShipDate;
	@JsonProperty("custbodymx_cad_date_wo") String cutFileDate;
	@JsonProperty("custbody_flore_notes") String notes;
	@JsonProperty("custbody_ore3d_soline_id") Integer associatedBuildUID;
	String tranId;
	String memo;
	NsItemList<WorkOrderComponent> item;
	Boolean expandassembly;
	
	public WorkOrder() {}
	
	public WorkOrder(NsID entity, NsID location, NsID salesOrderID, NsID childAssembly, NsID assemblyItem, NsID manufacturingRouting, double squareFeet,
			double quantity, boolean isWip, String expectedShipDate, String cutFileDate, String memo, String tranId, NsItemList<WorkOrderComponent> item)
	{
		this.entity = entity;
		this.location = location;
		this.salesOrderID = salesOrderID;
		this.childAssembly = childAssembly;
		this.assemblyItem = assemblyItem;
		this.manufacturingRouting = manufacturingRouting;
		this.squareFeet = squareFeet;
		this.quantity = quantity;
		this.isWip = isWip;
		this.expectedShipDate = expectedShipDate;
		this.cutFileDate = cutFileDate;
		this.memo = memo;
		this.tranId = tranId;
		this.item = item;
		this.orderStatus = new NsID("B");
	}
}
