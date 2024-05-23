package design.ore.OreNetBridge.Records;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Abstract.ValueStorageRecord;
import design.ore.OreNetBridge.Generic.NSAddress;
import design.ore.OreNetBridge.Generic.NsID;
import design.ore.OreNetBridge.Generic.NsItemList;
import design.ore.OreNetBridge.SubListItems.LineItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class Order extends ValueStorageRecord
{
	@Getter @Setter NsID entity;
	@Getter @Setter String id;
	@Getter @Setter @JsonProperty("custbody_ore3d_pricingdata") String pricingData;
	@Getter @Setter @JsonProperty("custbody_ore3d_version_flag") String versionFlag;
	@Getter @Setter String email;
	@Getter @Setter String tranId;
	@Getter @Setter @JsonProperty("custbody_ore3d_tag_data")String tagData;
	@Getter @Setter @JsonProperty("custbody_ore3d_record_is_locked") String recordIsLocked;
	@Getter @Setter NSAddress billingAddress;
	@Getter @Setter NSAddress shippingAddress;
	@Getter @Setter NsItemList<LineItem> item;
	@Getter @Setter LocalDate dueDate;
	
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
