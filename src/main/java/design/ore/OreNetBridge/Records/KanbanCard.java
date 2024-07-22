package design.ore.OreNetBridge.Records;

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
public class KanbanCard extends ValueStorageRecord
{
	String id;
	@JsonProperty("custrecord_associated_kanban") NsID associatedItem;
	@JsonProperty("custrecord_kanban_launcher") NsID launcher;
	@JsonProperty("custrecord_kanban_completed") Boolean completed;
}
