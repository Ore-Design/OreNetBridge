package design.ore.api.orenetbridge;

public class Util
{
	public static String throwableToString(Throwable e)
	{
		if(e.getCause() != null)
			return e.getLocalizedMessage() + stackTraceArrayToString(e.getStackTrace()) + "\nCaused by: " +
			e.getCause().getLocalizedMessage() + stackTraceArrayToString(e.getCause().getStackTrace());
		return e.getLocalizedMessage() + stackTraceArrayToString(e.getStackTrace());
	}

	public static String stackTraceArrayToString(StackTraceElement[] e)
	{
		StringBuilder str = new StringBuilder();
		for(StackTraceElement el : e) { str.append("\n\t" + el.toString()); }
		return str.toString();
	}
	
	public static String formatThrowable(String userDefinedMessage, Throwable e) { return userDefinedMessage + " - " + throwableToString(e); }
}
