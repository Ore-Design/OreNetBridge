package design.ore.api.orenetbridge.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignatureRequest
{
	Long signerId;
	String url, requestMethod;
}
