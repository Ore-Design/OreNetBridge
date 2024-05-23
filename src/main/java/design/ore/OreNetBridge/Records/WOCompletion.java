package design.ore.OreNetBridge.Records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Generic.NsID;
import design.ore.OreNetBridge.Generic.NsItemList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WOCompletion
{
	double completedQuantity;
	NsID startOperation;
	NsID endOperation;
	NsItemList<OperationCompletion> operation;
	@JsonProperty("custbody_flore_user") NsID user;
	@JsonProperty("custbody_flore_session") NsID flORESession;
	@JsonProperty("custbody_flore_time") double time;
	@JsonProperty("custbody_ore3d_soline_id") int associatedBuildUID;
}
