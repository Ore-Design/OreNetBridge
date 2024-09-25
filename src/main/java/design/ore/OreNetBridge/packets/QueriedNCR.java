package design.ore.OreNetBridge.packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class QueriedNCR
{
	String id;
	@JsonProperty("custrecordot_part") String partNumber;
	@JsonProperty("custrecord_associated_build_uid") String buildUID;
	@JsonProperty("custrecordot_department") String deptResponsible;
	@JsonProperty("custrecordot_defect") String defect;
	@JsonProperty("custrecordot_description") String defectDescription;
	
	// Used by flORE once transaction is loaded.
	@JsonIgnore String recordId;
}
