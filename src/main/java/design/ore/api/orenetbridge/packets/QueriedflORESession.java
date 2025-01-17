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
		workOrderId, workOrderName, buildRecordId, buildRecordName, ncrId, ncrName, completionId, completionName, lineName, oneDriveLink;
	
	Integer buildUuid;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss xxx")
	Instant startTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss xxx")
	Instant endTime;

	String routingStep;
	double minutes;
	double hours;
	int completed;
	
	private final static String commonQueryData = """
		SELECT\s
			customrecord_flore_session.id,\s
			custrecord_flore_completed_qty AS completed,\s
			custrecord_flore_line_item_name AS lineName,\s
			TO_CHAR(custrecord_flore_end_time, 'YYYY-MM-DD HH24:MI:SS TZH:TZM') AS endTime,\s
			custrecord_flore_routing_step AS routingStep,\s
			custrecord_fs_onedrive_link AS oneDriveLink,\s
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
			
			CASE\s
			WHEN custrecord_flore_associated_so IS NOT NULL THEN so.entity\s
			WHEN custrecord_flore_associated_wo IS NOT NULL THEN wo.entity\s
			WHEN custrecord_flore_associated_pr IS NOT NULL THEN pr.entity\s
			ELSE NULL\s
			END AS entityId,\s
			
			CASE\s
			WHEN custrecord_flore_associated_so IS NOT NULL THEN BUILTIN.DF(so.entity)\s
			WHEN custrecord_flore_associated_wo IS NOT NULL THEN BUILTIN.DF(wo.entity)\s
			WHEN custrecord_flore_associated_pr IS NOT NULL THEN BUILTIN.DF(pr.entity)\s
			ELSE NULL\s
			END AS entityName\s
			
			FROM customrecord_flore_session\s
			
			LEFT JOIN Transaction AS so ON so.id = custrecord_flore_associated_so\s
			LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_associated_wo\s
			LEFT JOIN Transaction AS pr ON pr.id = custrecord_flore_associated_pr\s
		""";

	@JsonIgnore
	public static String openSessionByUserIdQuery(String id)
	{
		// TODO: Replace WO retrieval with backdated after update
		// LEFT JOIN Transaction AS wo ON wo.id = custrecord_flore_associated_wo\s
		
		String query = commonQueryData + "WHERE custrecord_flore_user LIKE " + id + " AND custrecord_flore_end_time IS NULL";
		
		query = query.replace("\n", "").replace("\t", "");
		return query;
	}

	@JsonIgnore
	public static String getSessionByIdWithData(String id)
	{
		String query = commonQueryData + "WHERE customrecord_flore_session.id LIKE " + id + " AND custrecord_flore_end_time IS NULL";
		
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
		if(lineName != null) session.setLineItemName(lineName);
		if(oneDriveLink != null) session.setOneDriveLink(oneDriveLink);
		
		return session;
	}
}
