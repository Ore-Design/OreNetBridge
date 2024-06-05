package design.ore.OreNetBridge.Records;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Abstract.ValueStorageRecord;
import design.ore.OreNetBridge.Generic.NsID;
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
	@JsonProperty("custrecordot_defect") NsID defect;
	@JsonProperty("custrecordot_part") String partNumber;
	@JsonProperty("custrecord_created_from_flore") Boolean createdFromFlore = true;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="UTC")
	@JsonProperty("custrecordot_date_issued") OffsetDateTime dateIssued;
	
	public static NCR proposal(NsID proposalID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber)
	{
		NCR newNCR = new NCR();
		newNCR.setProposal(proposalID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(OffsetDateTime.now());
		
		return newNCR;
	}
	
	public static NCR salesOrder(NsID salesOrderID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber)
	{
		NCR newNCR = new NCR();
		newNCR.setSalesOrder(salesOrderID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(OffsetDateTime.now());
		
		return newNCR;
	}
	
	public static NCR workOrder(NsID workOrderID, NsID departmentResponsible, NsID reportingDepartment, NsID defect, String partNumber)
	{
		NCR newNCR = new NCR();
		newNCR.setWorkOrder(workOrderID);
		newNCR.setDepartmentResponsible(departmentResponsible);
		newNCR.setReportingDepartment(reportingDepartment);
		newNCR.setDefect(defect);
		newNCR.setPartNumber(partNumber);
		newNCR.setDateIssued(OffsetDateTime.now());
		
		return newNCR;
	}
}
