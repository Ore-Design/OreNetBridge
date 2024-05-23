package design.ore.OreNetBridge.Records;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Generic.NsID;
import design.ore.OreNetBridge.Generic.NsItemList;
import design.ore.OreNetBridge.SubListItems.BOMRevisionComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class BOMRevision
{	
	@Getter @Setter String id;
	@Getter @Setter String name;
	@Getter @Setter NsID billOfMaterials;
	@Getter @Setter @JsonProperty("custrecord_ore3d_build_data") String ore3DBuildData;
	@Getter @Setter boolean isInactive;
	@Getter @Setter NsItemList<BOMRevisionComponent> component;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy h:mm a")
	@Getter @Setter LocalDateTime createdDate;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	@Getter @Setter LocalDate effectiveStartDate;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	@Getter @Setter LocalDate effectiveEndDate;
}
