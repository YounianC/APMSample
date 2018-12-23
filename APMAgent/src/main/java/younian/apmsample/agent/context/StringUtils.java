package younian.apmsample.agent.context;

public class StringUtils {
	public static String join(final char delimiter, final String... strings) {
		if (strings.length == 0) {
			return null;
		}
		if (strings.length == 1) {
			return strings[0];
		}
		int length = strings.length - 1;
		for (final String s : strings) {
			if (s == null) {
				continue;
			}
			length += s.length();
		}
		final StringBuilder sb = new StringBuilder(length);
		if (strings[0] != null) {
			sb.append(strings[0]);
		}
		for (int i = 1; i < strings.length; ++i) {
			if (!isEmpty(strings[i])) {
				sb.append(delimiter).append(strings[i]);
			} else {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	public static boolean isEmpty(String string) {
		return org.apache.commons.lang3.StringUtils.isEmpty(string);
	}
	
	public static boolean isBlank(String string) {
		return org.apache.commons.lang3.StringUtils.isBlank(string);
	}
	
	public static boolean isNotBlank(String string){
		return org.apache.commons.lang3.StringUtils.isNotBlank(string);
	}
	
}
