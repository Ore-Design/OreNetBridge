package design.ore.api.orenetbridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
public class OperationCompletion
{
//	double laborResources = 1;
//	double laborResourcesLimit = 1;
//	double machineResources = 1;
//	double machineResourcesLimit = 1;
	int operationSequence;
	double laborRunTime;
	double machineRunTime;
	int taskId;
	
	public OperationCompletion(int operationSequence, double runRate, int taskId)
	{
		this.operationSequence = operationSequence;
		this.laborRunTime = runRate;
		this.machineRunTime = runRate;
		this.taskId = taskId;
	}
}
