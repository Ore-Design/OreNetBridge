package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.generic.NsItemList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WOCompletion
{
	Double completedQuantity;
	NsID startOperation;
	NsID endOperation;
	NsItemList<OperationCompletion> operation;
	String trandate;
	@JsonProperty("custbody_flore_user") NsID user;
	@JsonProperty("custbody_flore_session") NsID flORESession;
	@JsonProperty("custbody_flore_time") Double time;
	@JsonProperty("custbody_ore3d_soline_id") Integer associatedBuildUID;
}
