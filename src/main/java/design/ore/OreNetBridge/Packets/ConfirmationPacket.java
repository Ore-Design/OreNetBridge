package design.ore.OreNetBridge.Packets;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.OreNetBridge.Generic.TransactionReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationPacket
{
	Map<TransactionReference, QueriedManufacturingOperationTask> data = new HashMap<>();
	
	public void put(TransactionReference ref, QueriedManufacturingOperationTask task) { data.put(ref, task); }
	public int size() { return data.size(); }
}
