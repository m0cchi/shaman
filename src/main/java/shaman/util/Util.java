package shaman.util;

import java.util.Arrays;

public class Util {
	final public static String[] FORMATS = { "jpg", "png" , "jpeg", "gif"};

	public static boolean isSupportedFormats(String path) {
		return Arrays.asList(FORMATS).contains(getExt(path.toLowerCase()));
	}

	public static String getExt(String path) {
		if (path == null)
			return null;
		int point = path.lastIndexOf(".");
		if (point != -1) {
			return path.substring(point + 1);
		}
		return "";
	}

	public static String getFileName(String path) {
		int point = path.lastIndexOf("/");
		if (point < 0) {
			point = path.lastIndexOf("\\");
		}
		
		path = path.substring(point + 1);
		point = path.indexOf(".");
		if (point != -1) {
			return path.substring(0, point);
		}
		return null;
	}

	public static boolean isEmpty(String str) {
		return str == null ? true : str.isEmpty();
	}

	public static boolean isHiddenPath(String path) {
		return isEmpty(getFileName(path));
	}
}
