package design.ore.OreNetBridge.Packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import design.ore.OreNetBridge.Abstract.ValueStorageRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueriedBOMComponent extends ValueStorageRecord
{
	String id;
	String displayName;
	String itemID;
	String averageCost;
	String cost;
	@JsonProperty("custitem_ore3d_nomargin") String noMargin;
	@JsonProperty("custitem_ore3d_price") String overridePrice;
	@JsonProperty("custitem_ore3d_searchtag") String searchableTags;
	@JsonProperty("custitem_ore3d_unittype") String unitTypeOverride;
	String stockUnit;
}
