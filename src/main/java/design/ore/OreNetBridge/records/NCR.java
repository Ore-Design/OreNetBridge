package design.ore.OreNetBridge.records;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.abstractions.ValueStorageRecord;
import design.ore.OreNetBridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NCR extends ValueStorageRecord
{
	String id;
	@JsonProperty("custrecordot_proposal") NsID proposal;
	@JsonProperty("custrecordot_sales_order") NsID salesOrder;
	@JsonProperty("custrecordot_work_order") NsID workOrder;
	@JsonProperty("custrecordot_purchase_order") NsID purchaseOrder;
	@JsonProperty("custrecordot_department") NsID departmentResponsible;
	@JsonProperty("custrecord_ncr_op_responsible") NsID operatorResponsible;
	@JsonProperty("custrecord_ncr_reporting_department") NsID reportingDepartment;
	@JsonProperty("custrecordot_description") String defectDescription;
	@JsonProperty("custrecord_associated_build_uid") Integer associatedBuildUID;
	@JsonProperty("custrecordot_defect") NsID defect;
	@JsonProperty("custrecordot_part") String partNumber;
	@JsonProperty("custrecord_created_from_flore") Boolean createdFromFlore = true;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="UTC")
	@JsonProperty("custrecordot_date_issued") Date dateIssued;
	
	@JsonProperty("custrecord_rwt_fo") Integer frontOfficeTime;
	@JsonProperty("custrecordot_rwt_cad") Integer cadTime;
	@JsonProperty("custrecordot_rwt_prefab") Integer prefabTime;
	@JsonProperty("custrecordot_rwt_fab") Integer fabTime;
	@JsonProperty("custrecordot_rwt_finish") Integer finishingTime;
	@JsonProperty("custrecordot_rwt_pack") Integer packagingTime;
	
	public static NCR proposal(NsID proposalID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber, int buildUID)
	{
		NCR newNCR = new NCR();
		newNCR.setProposal(proposalID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(Date.from(Instant.now()));
		newNCR.setAssociatedBuildUID(buildUID);
		
		return newNCR;
	}
	
	public static NCR salesOrder(NsID salesOrderID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber, int buildUID)
	{
		NCR newNCR = new NCR();
		newNCR.setSalesOrder(salesOrderID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(Date.from(Instant.now()));
		newNCR.setAssociatedBuildUID(buildUID);
		
		return newNCR;
	}
	
	public static NCR workOrder(NsID workOrderID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber, int buildUID, NsID salesOrderID)
	{
		NCR newNCR = new NCR();
		newNCR.setWorkOrder(workOrderID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(Date.from(Instant.now()));
		newNCR.setAssociatedBuildUID(buildUID);
		newNCR.setSalesOrder(salesOrderID);
		
		return newNCR;
	}
}
