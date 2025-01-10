package design.ore.api.orenetbridge.restlets;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.api.orenetbridge.NetsuiteAPI;
import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.records.flORESession;
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
public class RESTletFS
{
	String id;
	RESTletFSValues values;
	
	public flORESession toflORESession()
	{
		try
		{	
			Instant startTime = Instant.parse(values.startTime);
			Instant endTime = null;
			
			if(!values.endTime.equals(""))
			{ endTime = Instant.parse(values.endTime); }
			
			double minutes = 0;
			try { minutes = Double.parseDouble(values.minutes); }
			catch(NumberFormatException e) { minutes = 0; }
			
			double hours = 0;
			try { hours = Double.parseDouble(values.hours); }
			catch(NumberFormatException e) { hours = 0; }
			
			int qty = 0;
			try { qty = Integer.parseInt(values.completed); }
			catch(NumberFormatException e) { qty = 0; }
			
			int input = 0;
			try { input = Integer.parseInt(values.quantity); }
			catch(NumberFormatException e) { input = 0; }
			
			NsID customer = null;
			if(values.customer.size() > 0)
			{
				NetsuiteAPI.getLogger().debug("Found customer for FS!");
				customer = values.customer.get(0).toNsID();
			}
			
			NsID associatedNCR = null;
			if(values.associatedNCR != null && values.associatedNCR.size() > 0) associatedNCR = values.associatedNCR.get(0).toNsID();
			
			flORESession newSesh = new flORESession(id, values.user.get(0).toNsID(), values.workOrder.get(0).toNsID(), customer, startTime, endTime, values.routingStep, minutes, hours, qty, input);
			
			if(associatedNCR != null) newSesh.setAssociatedNCR(associatedNCR);
			
			return newSesh;
		}
		catch(Exception e)
		{
			NetsuiteAPI.getLogger().error("Error converting RESTlet flORE Session into standard flORE Session!");
			e.printStackTrace();
			return null;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class RESTletFSValues
	{
		String id;
		@JsonProperty("custrecord_flore_user") List<RESTletNsID> user;
		@JsonProperty("custrecord_flore_work_order") List<RESTletNsID> workOrder;
		@JsonProperty("CUSTRECORD_FLORE_WORK_ORDER.entity") List<RESTletNsID> customer;
		@JsonProperty("custrecord_flore_start_time") String startTime;
		@JsonProperty("CUSTRECORD_FLORE_WORK_ORDER.quantity") String quantity;
		@JsonProperty("custrecord_flore_end_time") String endTime;
		@JsonProperty("custrecord_flore_routing_step") String routingStep;
		@JsonProperty("custrecord_flore_time_minutes") String minutes;
		@JsonProperty("custrecord_flore_time_hours") String hours;
		@JsonProperty("custrecord_flore_completed_qty") String completed;
		@JsonProperty("custrecord_associated_ncr") List<RESTletNsID> associatedNCR;
	}
}
