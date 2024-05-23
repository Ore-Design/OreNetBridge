package design.ore.OreNetBridge.SubListItems;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Abstract.ValueStorageRecord;
import design.ore.OreNetBridge.Generic.NsID;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class LineItem extends ValueStorageRecord
{
	public LineItem()
	{
		// This constructor creates the empty "Experlogix Default" item.

		item = new NsID("7146");
		price = new NsID("-1");
		quantity = 0;
		rate = 0;
	}
	
	double amount;
	@JsonProperty("custcol_ore3d_build") String buildJSON;
	@JsonProperty("custcol_ore3d_build_record") NsID buildRecord;
	@JsonProperty("custcol_ore3d_uid") int buildUID;
	@JsonProperty("custcolhours_cad") int cadTime;
	@JsonProperty("custcol_estnotes_customer") String customerNotes;
	String description;
	@JsonProperty("custcolhours_fab") int fabTime;
	@JsonProperty("custcolhours_finish") int finishTime;
	@JsonProperty("custcolore3d_fusep_quantity") int fuseParentQuantity;
	@JsonProperty("custcol_estnotes_internal") String internalNotes;
	NsID item;
	@JsonProperty("custcolhours_pack") int packagingTime;
	@JsonProperty("custcolhours_prefab") int prefabTime;
	@JsonProperty("custcol1") double weight;
	NsID price;
	Integer line;
	double quantity;
	double rate;
	@JsonProperty("custcol_square_feet") double squareFeet;
//	@JsonProperty("custcol_ore3d_warnings") String warnings;
	@JsonProperty("custcol_work_order_s") String childWorkOrders;
	@JsonProperty("custcol_ore3d_work_order") NsID workOrder;
}
