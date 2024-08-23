package design.ore.OreNetBridge.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.generic.NsID;
import design.ore.OreNetBridge.generic.NsItemList;
import design.ore.OreNetBridge.sublistitems.BOMRecordComponent;
import design.ore.OreNetBridge.sublistitems.ComponentItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyItem
{
	@Getter @Setter String id;
	@Getter @Setter String itemId;
	@Getter @Setter int internalId;
	@Getter @Setter @JsonProperty("custitem_ore3d_linear_inch_mod") boolean usesLinearInchModel;
	@Getter @Setter @JsonProperty("custitem3") NsID itemCategory;
	@Getter @Setter @JsonProperty("class") NsID itemClass;
	@Getter @Setter @JsonProperty("isPhantom") boolean phantom;
	@Getter @Setter Account incomeAccount;
	@Getter @Setter Account assetAccount;
	@Getter @Setter Account cogsAccount;
	@Getter @Setter NsItemList<ComponentItem> member;
	@Getter @Setter NsItemList<BOMRecordComponent> billOfMaterials;
}
