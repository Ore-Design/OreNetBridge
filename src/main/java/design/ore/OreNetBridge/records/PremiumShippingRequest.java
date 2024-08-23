package design.ore.OreNetBridge.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.abstractions.ValueStorageRecord;
import design.ore.OreNetBridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PremiumShippingRequest extends ValueStorageRecord
{
	String id;
	@JsonProperty("custrecord_psr_associated_tran") NsID associatedTransaction;
	@JsonProperty("custrecord_psr_request_details") String details;
	@JsonProperty("custrecord_psr_price") Double price;
	@JsonProperty("custrecord_psr_build_uid") Integer buildUID;
}
