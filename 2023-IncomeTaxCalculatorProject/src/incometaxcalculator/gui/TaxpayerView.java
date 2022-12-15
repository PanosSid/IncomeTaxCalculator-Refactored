package incometaxcalculator.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import incometaxcalculator.controller.MainManager;
import incometaxcalculator.io.exceptions.WrongFileFormatException;
import incometaxcalculator.model.Receipt;
import incometaxcalculator.model.Taxpayer;
import net.miginfocom.swing.MigLayout;

public class TaxpayerView {
    private JFrame frame;
    private JPanel panel;
    private MainManager mainManager;
    private Taxpayer taxpayer;
    private int trn;
    private JTable receiptsTable;
    private DefaultTableModel receiptTableModel;

    public TaxpayerView(int trn) {
	this.trn = trn;
	mainManager = MainManager.getInstance();
	taxpayer = mainManager.getTaxpayer(trn);
	initialize();
    }

    private void initialize() {
	frame = new JFrame();
	String nameAndTRN = taxpayer.getFullname() + " " + trn;
	frame.setTitle("Taxpayer View: " + nameAndTRN);
	frame.setSize(450, 500);
	frame.setLocationRelativeTo(null); // center window
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	panel = new JPanel(new MigLayout("wrap, insets 5 , fill", "[]5[]5[]5[]", "[]5[]5[]5[]5[]5[]5[]5[]"));

	addTaxpayersInfo();
	addTaxpayersReceipts();
	addButtons();

	frame.add(panel, BorderLayout.CENTER);
	frame.setVisible(true);
    }

    private void addTaxpayersInfo() {
	JLabel name = new JLabel("Name:    " + taxpayer.getFullname());
	JLabel lblTrn = new JLabel("TRN:    " + taxpayer.getTaxRegistrationNumber());
	JLabel lblStatus = new JLabel("Status:    " + taxpayer.getTaxpayerCategoryName());
	JLabel lblIncome = new JLabel("Income:    " + taxpayer.getIncome());

//	String taxpayerName = taxpayerManager.getTaxpayerName(trn);
//	JLabel name = new JLabel("Name:    " + taxpayerName);
//	JLabel lblTrn = new JLabel("TRN:    " + trn);
//	JLabel lblStatus = new JLabel("Status:    " + taxpayerManager.getTaxpayerCategoryName(trn));
//	JLabel lblIncome = new JLabel("Income:    " + taxpayerManager.getTaxpayerIncome(trn));
	name.setFont(new Font("Sans-serif", Font.BOLD, 14));
	lblTrn.setFont(new Font("Sans-serif", Font.BOLD, 14));
	lblStatus.setFont(new Font("Sans-serif", Font.BOLD, 14));
	lblIncome.setFont(new Font("Sans-serif", Font.BOLD, 14));

	panel.add(name, "span, grow");
	panel.add(lblTrn, "span, grow");
	panel.add(lblStatus, "span, grow");
	panel.add(lblIncome, "span, grow");

    }

    private void addTaxpayersReceipts() {
	receiptsTable = new JTable() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};

	receiptTableModel = new DefaultTableModel(new Object[] { "Receipt ID", "Date", "Amount" }, 0);
	updateReceiptTable();

	receiptsTable.setModel(receiptTableModel);
	JScrollPane scrollPane = new JScrollPane(receiptsTable);
	panel.add(scrollPane, "span, grow");
    }

    private void updateReceiptTable() {
	List<Receipt> receiptsList = taxpayer.getReceiptList();
	for (Receipt receipt : receiptsList) {
	    String receiptFields[] = new String[3];
	    receiptFields[0] = "" + receipt.getId();
	    receiptFields[1] = receipt.getIssueDate();
	    receiptFields[2] = "" + receipt.getAmount();
	    receiptTableModel.addRow(receiptFields);
	}
    }

    private void addButtons() {
	addReceiptButton();
	addDeleteReceiptButton();
	addViewReportsButton();
	addSaveLogButton();

    }

    private void addReceiptButton() {
	TaxpayerView tpv = this;
	JButton addReceipt = new JButton("Add Receipt");
	addReceipt.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		new ReceiptFormView(trn, tpv);
	    }
	});
	panel.add(addReceipt, "cell 0 7, grow");
    }

    public void addNewReceiptToTable(String receiptIdDateAmount[]) {
	receiptTableModel.addRow(receiptIdDateAmount);
    }

    private void addDeleteReceiptButton() {
	JButton delReceipt = new JButton("Delete Receipt");
	delReceipt.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int row = receiptsTable.getSelectedRow();
		if (row >= 0) {
		    int id = Integer.parseInt((String) receiptTableModel.getValueAt(row, 0)); // prosoxi allazoun thesei
											      // oi stiles ???
		    int result = JOptionPane.showConfirmDialog(null,
			    "Receipt with id:[" + id + "] is about to be deleted.\nContinue?", "Receipt Deletion",
			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (result == JOptionPane.YES_OPTION) {
			try {
			    mainManager.deleteReceiptFromTaxpayer(id, trn);
//			    taxpayerManager.removeReceipt(id);
			    receiptTableModel.removeRow(receiptsTable.getSelectedRow());
			    JOptionPane.showMessageDialog(null,
				    "Receipt with id:[" + id + "] was deleted sucessfully from taxpayer",
				    "Succesful Receipt Deletion", JOptionPane.INFORMATION_MESSAGE);
//			} catch (IOException | WrongReceiptKindException e1) {
//			    // TODO Auto-generated catch block
//			    e1.printStackTrace();
//			}
			} catch (IOException e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		    }
		}
	    }
	});
	panel.add(delReceipt, "cell 1 7, grow");
    }

    private void addViewReportsButton() {
	JButton viewReceipt = new JButton("View Report");
	viewReceipt.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ChartDisplay chartDisplay = new ChartDisplay();
		chartDisplay.createBarChart(taxpayer.getBasicTax(), taxpayer.getVariationTaxOnReceipts(),
			taxpayer.getTotalTax());
		chartDisplay.createPieChart(taxpayer.getAmountOfReceiptKind("Entertainment"),
			taxpayer.getAmountOfReceiptKind("Basic"), taxpayer.getAmountOfReceiptKind("Travel"),
			taxpayer.getAmountOfReceiptKind("Health"), taxpayer.getAmountOfReceiptKind("Other"));
	    }
	});
	panel.add(viewReceipt, "cell 2 7, grow");
    }

    private void addSaveLogButton() {
	JButton saveLog = new JButton("Save Log");
	saveLog.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "\\resources\\LOG files\\");
		chooser.setDialogTitle("Select Destistation Directory For LOG file");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int userSelection = chooser.showOpenDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
//		    chooser.setSelectedFile(null);
		    File fileToSave = chooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
//		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		    openSelectFileTypeToBeSaved(fileToSave.getAbsolutePath());
		}
	    }
	});
	panel.add(saveLog, "cell 3 7, grow");
    }

    private void openSelectFileTypeToBeSaved(String filePath) {
	
	Object[] possibilities = formatsToObjs();
	String s = (String) JOptionPane.showInputDialog(frame, "Select the file format of LOG file", "Save LOG",
		JOptionPane.PLAIN_MESSAGE, null, possibilities, "txt");
	for(int i = 0; i < possibilities.length; i++) {
	    String format = (String) possibilities[i];
	    if ((s != null) && (s.equals(format))) {
		try {
		    mainManager.saveLogFile(trn, filePath, format);
		    JOptionPane.showMessageDialog(null, "LOG file "+trn+"_LOG."+ format +" is saved succesfully to directory : "+filePath, "Succesful LOG save",
				JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException | WrongFileFormatException e1) {
		    e1.printStackTrace();
		}
	    }
	}

    }

    private Object[] formatsToObjs() {
	List<String> fileFormats = mainManager.getFileFormats();
	Object objs[] = new Object[fileFormats.size()];
	for (int i = 0; i < fileFormats.size(); i++) {
	    objs[i] = fileFormats.get(i);
	}
	return objs;
    }
}
