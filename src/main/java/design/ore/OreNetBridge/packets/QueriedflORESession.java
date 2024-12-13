package design.ore.OreNetBridge.packets;

import java.text.MessageFormat;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.NetsuiteAPI;
import design.ore.OreNetBridge.generic.NsID;
import design.ore.OreNetBridge.records.flORESession;
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
public class QueriedflORESession
{	
	@JsonProperty String id, userId, userName, entityId, entityName, proposalId, proposalName, salesOrderId, salesOrderName,
		workOrderId, workOrderName, buildRecordId, buildRecordName, ncrId, ncrName, completionId, completionName;
	
	Integer buildUuid;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	Instant startTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	Instant endTime;

	String routingStep;
	double minutes;
	double hours;
	int completed;

	@JsonIgnore
	public static String openSessionByUserIdQuery(String id)
	{
		String query = """
			SELECT\s
				customrecord_flore_session.id,\s
				custrecord_flore_completed_qty AS completed,\s
				TO_CHAR(custrecord_flore_end_time, 'YYYY-MM-DD"T"HH:mm:SS') AS endTime,\s
				custrecord_flore_routing_step AS routingStep,\s
				TO_CHAR(custrecord_flore_start_time, 'YYYY-MM-DD"T"HH:mm:SS') AS startTime,\s
				custrecord_flore_time_hours AS hours,\s
				custrecord_flore_time_minutes AS minutes,\s
				custrecord_flore_user AS userId,\s
				BUILTIN.DF(custrecord_flore_user) AS userName,\s
				custrecord_associated_ncr AS ncrId,\s
				BUILTIN.DF(custrecord_associated_ncr) AS ncrName,\s
				custrecord_flore_wocompletion AS completionId,\s
				BUILTIN.DF(custrecord_flore_wocompletion) AS completionName,\s
				custrecord_flore_build_record AS buildRecordId,\s
				BUILTIN.DF(custrecord_flore_build_record) AS buildRecordName,\s
				custrecord_flore_build_uuid AS buildUuid,\s
				
				pr.id AS proposalId,\s
				pr.tranid AS proposalName,\s
				
				so.id AS salesOrderId,\s
				so.tranid AS salesOrderName,\s
				
				wo.id AS workOrderId,\s
				wo.tranid AS workOrderName,\s
					
				wo.entity AS entityId,\s
				BUILTIN.DF(wo.entity) AS entityName\s
				
				FROM customrecord_flore_session\s
				
				LEFT JOIN Transaction AS so ON so.id = custrecord_flore_associated_so\s
				LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_associated_wo\s
				LEFT JOIN Transaction AS pr ON pr.id = custrecord_flore_associated_pr\s
				
				WHERE custrecord_flore_user LIKE\s""" + id + "\sAND custrecord_flore_end_time IS NULL";
		
		query = query.replace("\n", "").replace("\t", "");
		return query;
	}
	
	public flORESession toflORESession()
	{
		flORESession session = new flORESession(id, new NsID(userId, userName), new NsID(workOrderId, workOrderName),
			new NsID(entityId, entityName), startTime, endTime, routingStep, minutes, hours, completed, 0);

		if(proposalId != null && !proposalId.equals("")) session.setAssociatedProposal(new NsID(proposalId, proposalName));
		if(salesOrderId != null && !salesOrderId.equals("")) session.setAssociatedSO(new NsID(salesOrderId, salesOrderName));
		if(workOrderId != null && !workOrderId.equals("")) session.setAssociatedWO(new NsID(workOrderId, workOrderName));
		
		if(buildRecordId != null && !buildRecordId.equals("")) session.setBuildRecord(new NsID(buildRecordId, buildRecordName));
		if(buildUuid != null) session.setBuildUuid(buildUuid);
		
		NetsuiteAPI.getLogger().debug(MessageFormat.format("Session Items - PR: {0} - SO: {1} - WO: {2} ", session.getAssociatedProposal(), session.getAssociatedSO(), session.getAssociatedWO()));
		
		return session;
	}
}
