package design.ore.OreNetBridge.misc;

import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.OAuthMessageSigner;

@SuppressWarnings("serial")
public class ServerGeneratedSigner extends OAuthMessageSigner
{
	private final String signature;
	
	public ServerGeneratedSigner(String signature)
	{
		this.signature = signature;
	}
	
    @Override
    public String getSignatureMethod() {
        return "HMAC-SHA256";
    }

    @Override
    public String sign(HttpRequest request, HttpParameters requestParams) throws OAuthMessageSignerException { return signature; }
}
