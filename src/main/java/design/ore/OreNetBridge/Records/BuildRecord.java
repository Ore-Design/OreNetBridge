package design.ore.OreNetBridge.Records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Generic.NsID;
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
public class BuildRecord
{
	public static final String NS_ENDPOINT = "customrecord_ore3d_build";
	
	public BuildRecord(String buildJson, NsID relatedTransaction)
	{
		this.buildJson = buildJson;
		this.relatedTransaction = relatedTransaction;
	}
	
	String id;
	@JsonProperty("custrecord_build_data_0") String buildJson;
	@JsonProperty("custrecord_related_transaction") NsID relatedTransaction;
}
