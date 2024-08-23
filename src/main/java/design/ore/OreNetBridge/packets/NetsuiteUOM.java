package design.ore.OreNetBridge.packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id"})
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NetsuiteUOM
{
	String abbreviation;
	String internalID;
	String baseUnit;
	String unitName;
	String unitsType;
	
	public boolean isBaseUnit() { return baseUnit.equalsIgnoreCase("T"); }
}