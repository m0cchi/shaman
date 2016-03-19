package shaman.frame;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import shaman.frame.ev.DropHandler;
import shaman.frame.ev.Status;
import shaman.pdf.Converter;

public class MainFrame extends JFrame {
	private JTable table;
	private DefaultTableModel model;

	public MainFrame() {
		super("drop here");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		JPanel panel = (JPanel) getContentPane();
		String[] header = { "status", "name" };
		model = new DefaultTableModel(header, 0);
		table = new JTable(model);
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table
				.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(450);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		setTransferHandler(new DropHandler(this));

		panel.add(scrollPane);
		new Thread(new Monitor()).start();
	}

	public void add(File file) {
		model.addRow(new Object[] { Status.WAIT, new FileObject(file) });
	}

	private class Monitor implements Runnable {
		@Override
		public void run() {
			while (true) {
				int size = model.getRowCount();
				for (int i = 0; i < size; i++) {
					Status status = (Status) model.getValueAt(i, 0);
					if (Status.WAIT == status) {
						model.setValueAt(Status.RUNNING, i, 0);
						File file = ((FileObject) model.getValueAt(i, 1))
								.getFile();
						try {
							Converter converter = Converter.instance(
									file.getAbsolutePath(), true);
							converter.toPdf();
							model.setValueAt(Status.DONE, i, 0);
						} catch (Throwable e) {
							model.setValueAt(Status.ERROR, i, 0);
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
