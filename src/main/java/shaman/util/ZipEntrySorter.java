package shaman.util;

import java.util.Comparator;
import java.util.zip.ZipEntry;

public class ZipEntrySorter implements Comparator<ZipEntry> {
	private static NameSorter sorter = NameSorter.getInstance();

	@Override
	public int compare(ZipEntry o1, ZipEntry o2) {
		return o1 == null ? -1 : o2 == null ? 1 : sorter.compare(o1.getName(),
				o2.getName());
	}
}
