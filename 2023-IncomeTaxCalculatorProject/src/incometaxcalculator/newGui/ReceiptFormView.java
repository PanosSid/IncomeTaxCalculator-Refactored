package incometaxcalculator.newGui;

import javax.swing.JFrame;

public class ReceiptFormView {
    private JFrame frame;
    
    public ReceiptFormView() {
	frame = new JFrame("Add new Receipt");
	frame.setLocationRelativeTo(null);	// center window
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setVisible(true);
    }
}
