package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Ore3DDataRecord
{
	String created;
	@JsonProperty("custrecord_ore3d_data_routing_steps") String routingSteps;

	public String[] getRoutingStepIDs() { return routingSteps.replace(" ", "").split(","); }
}
