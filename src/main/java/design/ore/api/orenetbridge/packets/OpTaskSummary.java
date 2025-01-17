package design.ore.api.orenetbridge.packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpTaskSummary
{
	Integer inputQuantity, completed = 0;
}
