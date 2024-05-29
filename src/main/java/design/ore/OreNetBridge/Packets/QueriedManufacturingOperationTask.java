package design.ore.OreNetBridge.Packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class QueriedManufacturingOperationTask implements Comparable<QueriedManufacturingOperationTask>
{
	String id = "";
	String title = "UNKNOWN";
	String operationSequence = "0";
	String workOrder = "";
	String status = "NOTSTART";
	Integer inputQuantity = 0;
	Integer completedQuantity = 0;
	@JsonIgnore String description = null;
	@JsonIgnore boolean disable = false;
	@JsonIgnore String newWorkOrder = "";
	
	public int getSequenceAsInt()
	{
		try { return Integer.parseInt(operationSequence); }
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public String getStatusDisplayName()
	{
		switch(this.status)
		{
			case Status.NOT_STARTED: return "Not Started";
			case Status.IN_PROGRESS: return "In Progress";
			case Status.COMPLETED: return "Completed";
			default: return "Unknown";
		}
	}
	
	public class Status
	{
		public static final String NOT_STARTED = "NOTSTART";
		public static final String IN_PROGRESS = "PROGRESS";
		public static final String COMPLETED = "COMPLETE";
		
	}

	@Override
	public int compareTo(QueriedManufacturingOperationTask o) {
		return Integer.compare(this.getSequenceAsInt(), o.getSequenceAsInt());
	}
	
	public boolean matchesSequence(QueriedManufacturingOperationTask o)
	{
		return this.operationSequence.equals(o.operationSequence);
	}
}
