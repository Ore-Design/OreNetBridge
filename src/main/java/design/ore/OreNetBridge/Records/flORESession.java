package design.ore.OreNetBridge.Records;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(value = "tableDisplay", ignoreUnknown = true)
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
	@JsonProperty("custrecord_flore_start_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	OffsetDateTime startTime;
	@JsonProperty("custrecord_flore_end_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	OffsetDateTime endTime;
	@JsonProperty("custrecord_flore_routing_step") String routingStep;
	@JsonProperty("custrecord_flore_time_minutes") double minutes;
	@JsonProperty("custrecord_flore_time_hours") double hours;
	@JsonProperty("custrecord_flore_completed_qty") int completed;
	@JsonIgnore boolean selected;
	@JsonIgnore NsID customer;
	@JsonIgnore int requiredQty;
	@JsonProperty("custrecord_flore_wocompletion") NsID completion;

	public flORESession(String id, NsID user, NsID workOrder, OffsetDateTime startTime, OffsetDateTime endTime, String routingStep, double minutes, double hours, int completed)
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

	public flORESession(String id, NsID user, NsID workOrder, NsID customer, OffsetDateTime startTime, OffsetDateTime endTime, String routingStep, double minutes, double hours, int completed, int requiredQty)
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
	public flORESession(String id, NsID user, NsID workOrder, String routingStep)
	{
		this.id = id;
		this.user = user;
		this.workOrder = workOrder;
		this.startTime = OffsetDateTime.now();
		this.routingStep = routingStep;
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
