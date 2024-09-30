package design.ore.OreNetBridge.records;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.abstractions.ValueStorageRecord;
import design.ore.OreNetBridge.generic.NSAddress;
import design.ore.OreNetBridge.generic.NsID;
import design.ore.OreNetBridge.generic.NsItemList;
import design.ore.OreNetBridge.sublistitems.LineItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class Order extends ValueStorageRecord
{
	NsID entity;
	String id;
	@JsonProperty("custbody_ore3d_pricingdata") String pricingData;
	@JsonProperty("custbody_ore3d_version_flag") String versionFlag;
	String email;
	String tranId;
	@JsonProperty("custbody_ore3d_tag_data")String tagData;
	@JsonProperty("custbody_ore3d_record_is_locked") String recordIsLocked;
	NSAddress billingAddress;
	NSAddress shippingAddress;
	NsItemList<LineItem> item;
	LocalDate dueDate;
	@JsonProperty("custbody_ore3d_copied_from") NsID copiedFrom;
	@JsonProperty("custbody_ore3d_edit_log") String editLog;
	@JsonProperty("custbody_ore3d_save_cycle_count") Integer saveCycleCount;
	@JsonProperty("custbody_so_flore_notes") String floreNotes;
	
	public Order(String id, String tranId, String jsonPricing, NsID entityId, NSAddress billingAddress, NSAddress shippingAddress, NsItemList<LineItem> item, String versionFlag)
	{
		this.id = id;
		this.tranId = tranId;
		this.pricingData = jsonPricing;
		this.entity = entityId;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.item = item;
		this.versionFlag = versionFlag;
	}
}
