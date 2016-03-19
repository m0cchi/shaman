package shaman;

import java.io.IOException;
import java.util.zip.ZipException;

import javax.swing.JFrame;

import shaman.exception.UnSupportedExtensionException;
import shaman.frame.MainFrame;
import shaman.pdf.Converter;

import com.itextpdf.text.DocumentException;

public class Boot {

	public static void main(String[] args) {
		String filepath = null;
		boolean direction = true;
		switch (args.length) {
		case 0:
			new MainFrame().setVisible(true);
			break;
		case 2:
			direction = Boolean.getBoolean(args[1]);
		case 1:
			filepath = args[0];
		default:
			try {
				System.out.println("filepath: " + filepath);
				System.out.println("page progression direction: " + (direction ? "left" : "right"));
				Converter converter = Converter.instance(filepath, direction);
				converter.toPdf();
			} catch (ZipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnSupportedExtensionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
