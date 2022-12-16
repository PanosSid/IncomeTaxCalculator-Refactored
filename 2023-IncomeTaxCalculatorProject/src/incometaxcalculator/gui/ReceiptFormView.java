package incometaxcalculator.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import incometaxcalculator.controller.IncomeTaxManager;
import incometaxcalculator.controller.MainManager;
import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.exceptions.ReceiptAlreadyExistsException;
import net.miginfocom.swing.MigLayout;

public class ReceiptFormView {
//    private TaxpayerManager taxpayerManager;
    private IncomeTaxManager mainManager;
    private TaxpayerView taxpayerView;
    private JFrame frame;
    private JPanel panel;
    private HashMap<String, JComponent> componentMap;
    private int trn;

    public ReceiptFormView(int trn, TaxpayerView taxpayerView) {
	this.trn = trn;
	this.taxpayerView = taxpayerView;
	mainManager = MainManager.getInstance();
	componentMap = new HashMap<String, JComponent>();
	frame = new JFrame("Add new Receipt");
	frame.setSize(300, 400);
	frame.setLocationRelativeTo(null); // center window
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//	panel = new JPanel(new MigLayout("wrap, insets 5 , fill, debug", "[]5[]", "[]5[]5[]5[]5[]5[]5[]5[]5[]5[]"));
	panel = new JPanel(new MigLayout("wrap, insets 10", "[]5[]5[]5[]"));
//	panel = new JPanel(new GridLayout(0,2, 0,5));
	initialize();
	frame.add(panel, BorderLayout.CENTER);
	frame.setVisible(true);
    }

    private void initialize() {
	addFormField("Receipt Id", "ec. 10");
	addFormField("Issue Date", "dd/mm/yy");
	JLabel lbl = new JLabel("Kind");

	lbl.setFont(new Font(null, Font.BOLD, 14));
	panel.add(lbl);
	String choices[] = { "Entertainment", "Basic", "Travel", "Health", "Other" };
	JComboBox<String> kindComboBox = new JComboBox<String>(choices);
	panel.add(kindComboBox, "wrap, span");
	componentMap.put("Kind", kindComboBox);

	addFormField("Amount", "112.5");
	addFormField("Company", "UOI");
	addFormField("Country", "Greece");
	addFormField("City", "Ioannina");
	addFormField("Street", "Dodonis");
	addFormField("Street Number", "23");
	addSaveReceiptButton();
	panel.add(new JButton("Cancel"));

    }

    private void addFormField(String lblTxt, String txtPromt) {
	JLabel lbl = new JLabel(lblTxt);
	lbl.setFont(new Font(null, Font.BOLD, 14));
	panel.add(lbl);

	JTextField tf = new JTextField(10);
	TextPrompt tp = new TextPrompt(txtPromt, tf);
	tp.changeStyle(Font.ITALIC);
	panel.add(tf, "wrap, span");

	componentMap.put(lblTxt, tf);
    }

    private void addSaveReceiptButton() {
	JButton savebtn = new JButton("Save");
	savebtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int receiptIDValue = Integer.parseInt(((JTextField) componentMap.get("Receipt Id")).getText());
		int numberValue = Integer.parseInt(((JTextField) componentMap.get("Street Number")).getText());
		;
		float amountValue = Float.parseFloat(((JTextField) componentMap.get("Amount")).getText());
		String dateValue = ((JTextField) componentMap.get("Issue Date")).getText();
		String kindValue = String.valueOf(((JComboBox<String>) componentMap.get("Kind")).getSelectedItem());
		String companyValue = ((JTextField) componentMap.get("Company")).getText();
		String countryValue = ((JTextField) componentMap.get("Country")).getText();
		String streetValue = ((JTextField) componentMap.get("Street")).getText();
		String cityValue = ((JTextField) componentMap.get("City")).getText();
		try {
		    mainManager.addReceiptToTaxpayer(receiptIDValue, dateValue, amountValue, kindValue, companyValue,
			    countryValue, cityValue, streetValue, numberValue, trn);
		    String[] receiptContets = { "" + receiptIDValue, dateValue, "" + amountValue };
		    taxpayerView.addNewReceiptToTable(receiptContets);
		    closeWindow();
		    JOptionPane.showMessageDialog(null, "Receipt added Succesfully", "Succesful Receipt Addition",
			    JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (WrongReceiptKindException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (WrongReceiptDateException e1) {
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Wrong date format",
			    JOptionPane.ERROR_MESSAGE);
		    e1.printStackTrace();
		} catch (ReceiptAlreadyExistsException e1) {
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Receipt Already Exists",
			    JOptionPane.ERROR_MESSAGE);
		    e1.printStackTrace();
		}

	    }
	});
	panel.add(savebtn);
    }

    private void closeWindow() {
	frame.setVisible(false); 
	frame.dispose(); 
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    if ("Nimbus".equals(info.getName())) {
			try {
			    UIManager.setLookAndFeel(info.getClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			break;
		    }
		}
		new ReceiptFormView(123456789, new TaxpayerView(123456789));
	    }
	});
    }

}
