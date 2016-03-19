package shaman.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfMaker {
	public static int WIDTH = 1072 / 2;
	public static int HEIGHT = 1448 / 2;
	public static boolean LEFT = true;
	private Document document;
	private PdfWriter pdfWriter;
	private PdfContentByte pdfContentByte;
	private boolean direction = false;

	public PdfMaker(String filepath, boolean direction)
			throws FileNotFoundException, DocumentException {
		this.direction = direction;
		document = new Document(new Rectangle(0, 0, WIDTH, HEIGHT));

		FileOutputStream fos = new FileOutputStream(new File(filepath));
		pdfWriter = PdfWriter.getInstance(document, fos);
		document.open();
		pdfContentByte = pdfWriter.getDirectContent();
	}

	public void addImage(String filepath) throws MalformedURLException,
			IOException, DocumentException {
		Image img = Image.getInstance(filepath);
		addImage(img);
	}

	public void addImage(byte[] bytes) throws MalformedURLException,
			IOException, DocumentException {
		Image img = Image.getInstance(bytes);
		addImage(img);
	}

	public void addImage(File file) throws MalformedURLException, IOException,
			DocumentException {
		addImage(file.getAbsolutePath());
	}

	public void addWidePage(Image img, float width, float height, float posX,
			float posY) throws DocumentException {
		int count = (int) width / WIDTH;
		for (int i = 1; i <= count + 1; i++) {
			img.setAbsolutePosition((WIDTH * (direction ? i : (count - i)))
					- width, 0);
			img.scaleAbsolute(width, height);
			pdfContentByte.addImage(img);
			document.newPage();
		}
	}

	public void addImage(Image img) throws DocumentException {
		float width = img.getWidth();
		float height = img.getHeight();
		float magnification = HEIGHT / height;
		width = width * magnification;
		height = height * magnification;
		float posX = (WIDTH - width) / 2;
		float posY = (HEIGHT - width) / 2;

		if (width > WIDTH * 1.5) {
			addWidePage(img, width, height, posX, posY);
			return;
		} else if (width > WIDTH) {
			magnification = WIDTH / width;
			width = width * magnification;
			height = height * magnification;
		}
		img.setAbsolutePosition(posX > 0 ? posX : 0, 0);
		img.scaleAbsolute(width, height);
		pdfContentByte.addImage(img);
		document.newPage();
	}

	public void close() {
		document.close();
		pdfWriter.close();
	}

}
