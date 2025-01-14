package design.ore.api.orenetbridge.packets;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class flORESessionStopRequest
{
	String sessionId, note;
	int quantityCompleted;
	Timestamp completedTime;
}
