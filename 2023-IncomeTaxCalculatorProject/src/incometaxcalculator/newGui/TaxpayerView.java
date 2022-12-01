package incometaxcalculator.newGui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import net.miginfocom.swing.MigLayout;

import incometaxcalculator.gui.ChartDisplay;

public class TaxpayerView {
    private JFrame frame;
    private JPanel panel;
    private TaxpayerManager taxpayerManager;
    private int trn;
    private JTable receiptsTable;
    private DefaultTableModel receiptTableModel;

    public TaxpayerView(int trn, TaxpayerManager taxpayerManager) {
	this.trn = trn;
	this.taxpayerManager = taxpayerManager;
	taxpayerManager = new TaxpayerManager();
	initialize();
    }

    private void initialize() {
	frame = new JFrame();
	String nameAndTRN = taxpayerManager.getTaxpayerName(trn) + " " + trn;
	frame.setTitle("Taxpayer View: " + nameAndTRN);
	frame.setSize(450, 500);
	frame.setLocationRelativeTo(null); // center window
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	panel = new JPanel(new MigLayout("wrap, insets 5 , fill, debug", "[]5[]5[]5[]", "[]5[]5[]5[]5[]5[]5[]5[]"));

	addTaxpayersInfo();
	addTaxpayersReceipts();
	addButtons();

	frame.add(panel, BorderLayout.CENTER);
	frame.setVisible(true);
    }

    private void addTaxpayersInfo() {
	String taxpayerName = taxpayerManager.getTaxpayerName(trn);
	JLabel name = new JLabel("Name:    " + taxpayerName);
//	name.setForeground(Color.WHITE);
	JLabel lblTrn = new JLabel("TRN:    " + trn);
	JLabel lblStatus = new JLabel("Status:    " + taxpayerManager.getTaxpayerStatus(trn));
	JLabel lblIncome = new JLabel("Income:    " + taxpayerManager.getTaxpayerIncome(trn));
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
	List<Receipt> receiptsList = taxpayerManager.getReceiptListOfTaxpayer(trn);
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
	JButton addReceipt = new JButton("Add Receipt");
	addReceipt.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		new ReceiptFormView();
	    }
	});
	panel.add(addReceipt, "cell 0 7, grow");
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
			    taxpayerManager.removeReceipt(id);
			    receiptTableModel.removeRow(receiptsTable.getSelectedRow());
			    JOptionPane.showMessageDialog(null,
				    "Receipt with id:[" + id + "] was deleted sucessfully from taxpayer",
				    "Succesful Receipt Deletion", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | WrongReceiptKindException e1) {
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
		ChartDisplay.createBarChart(taxpayerManager.getTaxpayerBasicTax(trn),
			taxpayerManager.getTaxpayerVariationTaxOnReceipts(trn),
			taxpayerManager.getTaxpayerTotalTax(trn));
		ChartDisplay.createPieChart(
			taxpayerManager.getTaxpayerAmountOfReceiptKind(trn, (short) 0),
			taxpayerManager.getTaxpayerAmountOfReceiptKind(trn, (short) 1),
			taxpayerManager.getTaxpayerAmountOfReceiptKind(trn, (short) 2),
			taxpayerManager.getTaxpayerAmountOfReceiptKind(trn, (short) 3),
			taxpayerManager.getTaxpayerAmountOfReceiptKind(trn, (short) 4));
	    }
	});
	panel.add(viewReceipt, "cell 2 7, grow");
    }
    
    private void addSaveLogButton() {
	JButton saveLog = new JButton("Save Log");
	saveLog.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Object[] possibilities = {"txt","xml"};
		String s = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Select the file format of LOG file",
		                    "Save LOG", JOptionPane.PLAIN_MESSAGE, 
		                    null, possibilities,
		                    "txt");

		//If a string was returned, say so.
		if ((s != null) && (s.equals("txt"))) {
		    try {
			taxpayerManager.saveLogFile(trn, "txt");
		    } catch (IOException | WrongFileFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		    return;
		} else if ((s != null) && s.equals("xml")) {
		    try {
			
			taxpayerManager.saveLogFile(trn, "xml");
		    } catch (IOException | WrongFileFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		} 
	    }
	});
	panel.add(saveLog, "cell 3 7, grow");
    }
}
