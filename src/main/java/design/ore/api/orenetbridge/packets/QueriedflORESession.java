package design.ore.api.orenetbridge.packets;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.api.orenetbridge.generic.NsID;
import design.ore.api.orenetbridge.records.flORESession;
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
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss xxx")
	Instant startTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss xxx")
	Instant endTime;

	String routingStep;
	double minutes;
	double hours;
	int completed;

	@JsonIgnore
	public static String openSessionByUserIdQuery(String id)
	{
		// TODO: Replace WO retrieval with backdated after update
		// LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_associated_wo\s
		
		String query = """
			SELECT\s
				customrecord_flore_session.id,\s
				custrecord_flore_completed_qty AS completed,\s
				TO_CHAR(custrecord_flore_end_time, 'YYYY-MM-DD HH24:MI:SS TZH:TZM') AS endTime,\s
				custrecord_flore_routing_step AS routingStep,\s
				TO_CHAR(custrecord_flore_start_time, 'YYYY-MM-DD HH24:MI:SS TZH:TZM') AS startTime,\s
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
				LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_work_order\s
				LEFT JOIN Transaction AS pr ON pr.id = custrecord_flore_associated_pr\s
				
				WHERE custrecord_flore_user LIKE\s""" + id + "\sAND custrecord_flore_end_time IS NULL";
		
		query = query.replace("\n", "").replace("\t", "");
		return query;
	}

	@JsonIgnore
	public static String getSessionByIdWithData(String id)
	{
		String query = """
			SELECT\s
				customrecord_flore_session.id,\s
				custrecord_flore_completed_qty AS completed,\s
				TO_CHAR(custrecord_flore_end_time, 'YYYY-MM-DD HH24:MI:SS TZH:TZM') AS endTime,\s
				custrecord_flore_routing_step AS routingStep,\s
				TO_CHAR(custrecord_flore_start_time, 'YYYY-MM-DD HH24:MI:SS TZH:TZM') AS startTime,\s
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
				LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_work_order\s
				LEFT JOIN Transaction AS pr ON pr.id = custrecord_flore_associated_pr\s
				
				WHERE customrecord_flore_session.id LIKE\s""" + id + "\sAND custrecord_flore_end_time IS NULL";
		
		query = query.replace("\n", "").replace("\t", "");
		return query;
	}
	
	public flORESession toflORESession()
	{
		flORESession session = new flORESession(id, new NsID(userId, userName), new NsID(workOrderId, workOrderName),
			new NsID(entityId, entityName), startTime, endTime, routingStep, minutes, hours, completed, 0);

		if(proposalId != null && !proposalId.equals("")) session.setAssociatedProposal(new NsID(proposalId, proposalName));
		if(salesOrderId != null && !salesOrderId.equals("")) session.setAssociatedSO(new NsID(salesOrderId, salesOrderName));
		if(workOrderId != null && !workOrderId.equals(""))
		{
			session.setAssociatedWO(new NsID(workOrderId, workOrderName));
			session.setWorkOrder(new NsID(workOrderId, workOrderName));
		}
		
		if(buildRecordId != null && !buildRecordId.equals("")) session.setBuildRecord(new NsID(buildRecordId, buildRecordName));
		if(buildUuid != null) session.setBuildUuid(buildUuid);
		
		return session;
	}
}
