package design.ore.OreNetBridge.Records;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.OreNetBridge.Generic.NsID;
import design.ore.OreNetBridge.Generic.NsItemList;
import design.ore.OreNetBridge.SubListItems.ManufacturingRoutingStep;
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
public class ManufacturingRouting
{
	String id;
	NsID item;
	NsID billOfMaterials;
	String name;
	NsItemList<NsID> location;
	NsItemList<ManufacturingRoutingStep> routingStep;

	// Advanced BOM
	public ManufacturingRouting(AssemblyItem item, String name, List<ManufacturingRoutingStep> steps, NsID billOfMaterials)
	{
		this.item = new NsID(item.id);
		this.name = name;
		this.billOfMaterials = billOfMaterials;

		location = new NsItemList<>(Arrays.asList(new NsID("3"))); // Woods Cross

		routingStep = new NsItemList<ManufacturingRoutingStep>(steps);
	}

	// Basic BOM
	public ManufacturingRouting(AssemblyItem item, String name, List<ManufacturingRoutingStep> steps)
	{
		this.item = new NsID(item.id);
		this.name = name;

		location = new NsItemList<>(Arrays.asList(new NsID("3"))); // Woods Cross

		routingStep = new NsItemList<ManufacturingRoutingStep>(steps);
	}
}
