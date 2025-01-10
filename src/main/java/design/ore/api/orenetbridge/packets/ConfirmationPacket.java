package design.ore.api.orenetbridge.packets;

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
public class ConfirmationPacket
{
	public ConfirmationPacket(TransactionReference tran, QueriedManufacturingOperationTask task)
	{
		this.tran = tran;
		this.task = task;
	}
	
	TransactionReference tran;
	QueriedManufacturingOperationTask task;
	QueriedNCR ncr;
}
