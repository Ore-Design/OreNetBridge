package design.ore.OreNetBridge.sublistitems;

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
public class RoutingItem
{
	String id;
	String name;
	@JsonProperty("custrecord_ore3d_routing_costoverride") String costPerMinute;
	@JsonProperty("custrecord_ore3d_routing_costtemp") String costTemplateID;
	@JsonProperty("custrecord_ore3d_routing_opsequence") String operationSequenceID;
	@JsonProperty("custrecord_ore3d_routing_workcenter") String workCenterID;
}
