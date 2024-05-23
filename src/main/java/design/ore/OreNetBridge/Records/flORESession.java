package design.ore.OreNetBridge.Records;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime startTime;
	@JsonProperty("custrecord_flore_end_time")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime endTime;
	@JsonProperty("custrecord_flore_routing_step") String routingStep;
	@JsonProperty("custrecord_flore_time_minutes") double minutes;
	@JsonProperty("custrecord_flore_time_hours") double hours;
	@JsonProperty("custrecord_flore_completed_qty") int completed;
	@JsonIgnore boolean selected;
	@JsonIgnore NsID customer;
	@JsonIgnore int requiredQty;
	@JsonProperty("custrecord_flore_wocompletion") NsID completion;

	public flORESession(String id, NsID user, NsID workOrder, LocalDateTime startTime, LocalDateTime endTime, String routingStep, double minutes, double hours, int completed)
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

	public flORESession(String id, NsID user, NsID workOrder, NsID customer, LocalDateTime startTime, LocalDateTime endTime, String routingStep, double minutes, double hours, int completed, int requiredQty)
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
		this.startTime = LocalDateTime.now();
		this.routingStep = routingStep;
	}
	
	public long getCurrentElapsedMinutes()
	{
		if(endTime != null ) return ChronoUnit.MINUTES.between(startTime, endTime);
		else return ChronoUnit.MINUTES.between(startTime, LocalDateTime.now());
	}
	
	public double getCurrentElapsedHours()
	{
		return Double.parseDouble(new DecimalFormat("###.##").format(getCurrentElapsedMinutes() / 60d));
	}
	
	public String getTableDisplay()
	{
		return getCurrentElapsedMinutes() + " Min (" + getCurrentElapsedHours() + " Hrs)";
	}
}
