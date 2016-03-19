package shaman.frame.ev;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.TransferHandler;

import shaman.frame.MainFrame;

public class DropHandler extends TransferHandler {
	private MainFrame frame;
	
	public DropHandler(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean canImport(TransferSupport support) {
		if (!support.isDrop()) {
			return false;
		}

		if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}

		Transferable t = support.getTransferable();
		try {
			List<File> files = (List<File>) t
					.getTransferData(DataFlavor.javaFileListFlavor);

			StringBuffer fileList = new StringBuffer();
			for (File file : files) {
				frame.add(file);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
