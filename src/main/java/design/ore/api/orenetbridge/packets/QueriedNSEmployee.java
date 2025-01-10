package design.ore.api.orenetbridge.packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.api.orenetbridge.generic.NsID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor
@NoArgsConstructor
public class QueriedNSEmployee
{
	@Getter @Setter String id, name, department, defaultOpStep;
	@Setter Integer dayMinGoalOverride, weekMinGoalOverride, deptDayMinGoalOverride, deptWeekMinGoalOverride;
	@Getter @Setter Long dayMillis = 0L, weekMillis = 0L;
	@Getter @Setter Integer count = 0;
	
	public Long getGoalDayMillis()
	{
		// 30600000L Company default is 8.5 hrs a day
		// 60000L is min to millis conversion
		return dayMinGoalOverride != null ? dayMinGoalOverride * 60000L : deptDayMinGoalOverride != null ? deptDayMinGoalOverride * 60000L : 30600000L;
	};
	
	public Long getGoalWeekMillis()
	{
		// 122400000L Company default is 34 hrs a week
		// 60000L is min to millis conversion
		return weekMinGoalOverride != null ? weekMinGoalOverride * 60000L : deptWeekMinGoalOverride != null ? deptWeekMinGoalOverride * 60000L : 122400000L;
	};
	
	public NsID toNsID() { return new NsID(id, name); }
	
	public static String getNSEmployeeQueryWithID(String id)
	{
		String query = """
			SELECT\s
				employee.id,\s
				employee.entityid AS name,\s
				employee.custentity_flore_day_goal_override AS dayMinGoalOverride,\s
				employee.custentity_flore_week_time_goal_override AS weekMinGoalOverride,\s
				customrecordot_dept_resp.name AS department,\s
				customrecordot_dept_resp.custrecord_flore_default_op_step AS defaultOpStep,\s
				customrecordot_dept_resp.custrecord_flore_day_goal_override AS deptDayMinGoalOverride,\s
				customrecordot_dept_resp.custrecord_flore_week_time_goal_override AS deptWeekMinGoalOverride\s
				
				FROM employee\s
				
				LEFT JOIN customrecordot_dept_resp ON customrecordot_dept_resp.id = employee.custentity_functional_department\s
				
				WHERE employee.isinactive LIKE 'F' AND employee.id LIKE '""" + id + "'";
		
		query = query.replace("\n", "").replace("\t", "");
		return query;
	}
}
