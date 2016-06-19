package shaman.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameSorter implements java.util.Comparator<String> {
	private static NameSorter sorter;

	public static NameSorter getInstance() {
		return sorter != null ? sorter : (sorter = new NameSorter());
	}

	public int compare(int n1, int n2) {
		return n1 == n2 ? 0 : n1 > n2 ? 1 : -1;
	}

	@Override
	public int compare(String o1, String o2) {
		Pattern number = Pattern.compile("[0-9]+");
		String n1 = null;
		String n2 = null;
		int t1 = -1;
		int t2 = -1;
		Matcher matcher = number.matcher(o1);
		while (matcher.find()) {
			n1 = matcher.group();
		}
		matcher = number.matcher(o2);
		while (matcher.find()) {
			n2 = matcher.group();
		}

		try {
			t1 = Integer.parseInt(n1);
		} catch (Throwable e) {
		}
		try {
			t2 = Integer.parseInt(n2);
		} catch (Throwable e) {
		}

		int result = compare(t1, t2);
		if (result == 0) {
			return o1.compareTo(o2);
		} else {
			return result;
		}
	}
}
