package design.ore.OreNetBridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BOMComponentType
{
	String id;
	String isInactive;
	@Getter String name;
	@Getter @JsonProperty("custrecord_ore3d_component_fields") String requiredFields;
	@Getter @JsonProperty("custrecord_ore3d_optional_comp_fields") String optionalFields;
	@Getter @JsonProperty("custrecord_ore3d_bom_template") String templateRecord;
	
	public boolean isInavtive() { return isInactive != null && isInactive.equalsIgnoreCase("T"); }
	public boolean customCanBeMade() { return templateRecord != null && !templateRecord.equals(""); }
	public int getID() { try { return Integer.parseInt(id); } catch(Exception e) { return 0; } }
	public String[] getRequiredFieldsSplit() { return requiredFields.replaceAll(" ", "").split(","); }
	public String[] getOptionalFieldsSplit() { return optionalFields.replaceAll(" ", "").split(","); }
}
