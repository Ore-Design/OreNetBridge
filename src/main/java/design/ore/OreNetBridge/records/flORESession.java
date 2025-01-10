package design.ore.OreNetBridge.records;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.generic.NsID;
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
public class flORESession
{
	String id;
	@JsonProperty("custrecord_flore_user") NsID user;
	@JsonProperty("custrecord_flore_work_order") NsID workOrder;
	@JsonProperty("custrecord_associated_ncr") NsID associatedNCR;
	
	@JsonProperty("custrecord_flore_start_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	Instant startTime;
	@JsonProperty("custrecord_flore_end_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	Instant endTime;
	
	@JsonProperty("custrecord_flore_routing_step") String routingStep;
	@JsonProperty("custrecord_flore_time_minutes") double minutes;
	@JsonProperty("custrecord_flore_time_hours") double hours;
	@JsonProperty("custrecord_flore_completed_qty") int completed;
	@JsonProperty("custrecord_flore_wocompletion") NsID completion;
	@JsonProperty("custrecord_flore_build_record") NsID buildRecord;
	@JsonProperty("custrecord_flore_build_uuid") Integer buildUuid;
	@JsonProperty("custrecord_fs_paused_min") Integer pausedMin;
	
	@JsonProperty("custrecord_flore_associated_wo") NsID associatedWO;
	@JsonProperty("custrecord_flore_associated_so") NsID associatedSO;
	@JsonProperty("custrecord_flore_associated_pr") NsID associatedProposal;
	@JsonProperty("custrecord_flore_line_item_name") String lineItemName;
	
	@JsonIgnore boolean selected;
	@JsonIgnore protected NsID customer;
	@JsonIgnore int requiredQty;
	@JsonIgnore protected int completedPreviously = 0;

	public flORESession(String id, NsID user, NsID workOrder, Instant startTime, Instant endTime, String routingStep, double minutes, double hours, int completed)
	{
		this.id = id;
		this.user = user;
		this.workOrder = workOrder;
		this.startTime = startTime;
		this.endTime = endTime;
		this.routingStep = routingStep;
		this.minutes = minutes;
		this.hours = hours;
		this.completed = completed;
	}

	public flORESession(String id, NsID user, NsID workOrder, NsID customer, Instant startTime, Instant endTime, String routingStep, double minutes, double hours, int completed, int requiredQty)
	{
		this.id = id;
		this.user = user;
		this.workOrder = workOrder;
		this.customer = customer;
		this.startTime = startTime;
		this.endTime = endTime;
		this.routingStep = routingStep;
		this.minutes = minutes;
		this.hours = hours;
		this.completed = completed;
		this.requiredQty = requiredQty;
	}
	public flORESession(String id, NsID user, NsID associatedRecord, String routingStep)
	{
		this.id = id;
		this.user = user;
		this.workOrder = associatedRecord;
		this.startTime = Instant.now();
		this.routingStep = routingStep;
		
		if(associatedRecord.getRefName() != null && !associatedRecord.getRefName().equals(""))
		{
			String upperName = associatedRecord.getRefName().toUpperCase();
			if(upperName.startsWith("WO")) associatedWO = associatedRecord;
			else if(upperName.startsWith("S")) associatedSO = associatedRecord;
			else associatedProposal = associatedRecord;
		}
	}
	
	@JsonIgnore public long getCurrentElapsedMinutes()
	{
		if(endTime != null ) return ChronoUnit.MINUTES.between(startTime, endTime);
		else return ChronoUnit.MINUTES.between(startTime, OffsetDateTime.now());
	}
	
	@JsonIgnore public double getCurrentElapsedHours()
	{
		return Double.parseDouble(new DecimalFormat("###.##").format(getCurrentElapsedMinutes() / 60d));
	}
	
	@JsonIgnore public String getTableDisplay()
	{
		return getCurrentElapsedMinutes() + " Min (" + getCurrentElapsedHours() + " Hrs)";
	}
}
