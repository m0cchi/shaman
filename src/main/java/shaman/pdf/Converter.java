package shaman.pdf;

import java.io.IOException;
import java.util.zip.ZipException;

import shaman.exception.UnSupportedExtensionException;
import shaman.pdf.plugin.ConverterFromZip;
import shaman.util.Util;

import com.itextpdf.text.DocumentException;

public abstract class Converter {
	public static Converter instance(String filepath, boolean direction)
			throws ZipException, DocumentException, IOException,
			UnSupportedExtensionException {
		Converter converter = null;
		switch (Util.getExt(filepath)) {
		case ConverterFromZip.EXT:
			converter = new ConverterFromZip(filepath, direction);
			break;
		default:
			throw new UnSupportedExtensionException();
		}

		return converter;
	}

	public abstract void toPdf() throws Throwable;

}
