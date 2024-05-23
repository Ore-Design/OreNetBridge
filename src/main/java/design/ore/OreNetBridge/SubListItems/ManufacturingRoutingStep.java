package design.ore.OreNetBridge.SubListItems;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class ManufacturingRoutingStep
{
	double laborResources;
	double machineResources;
	NsID manufacturingCostTemplate;
	NsID manufacturingWorkCenter;
	String operationName;
	int operationSequence;
	double operationYield;
	double runRate;
	double setupTime;

	public ManufacturingRoutingStep(String operationName, String costTemplateID, String workCenterID, int operationSequence, double runRate, double setupTime)
	{
		laborResources = 1;
		machineResources = 1;

		manufacturingCostTemplate = new NsID(costTemplateID);
		manufacturingWorkCenter = new NsID(workCenterID);
		this.operationSequence = operationSequence;
		this.runRate = runRate;
		this.setupTime = setupTime;
		this.operationName = operationName;
	}
}
