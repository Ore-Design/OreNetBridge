package design.ore.api.orenetbridge.packets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import design.ore.api.orenetbridge.generic.TransactionReference;
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
public class OperationSelectionData
{
	Map<TransactionReference, List<QueriedManufacturingOperationTask>> data = new HashMap<>();
	boolean hasNCR = false;
	
	public void put(TransactionReference ref, List<QueriedManufacturingOperationTask> tasks) { data.put(ref, tasks); }
}
