package incometaxcalculator.newGui;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.TaxpayerAlreadyLoadedException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import net.miginfocom.swing.MigLayout;


public class MainView {
    
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JTable loadedTaxpayersTable;
    private TaxpayerManager taxpayerManager;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    
    public MainView(String text) {
	taxpayerManager = TaxpayerManager.getInstance();
        initialize();    
    }

    private void initialize() {
	mainFrame = new JFrame();
	mainFrame.setTitle("Minessota Tax Calculator");
	mainFrame.setSize(500, 350);
	mainFrame.setLocationRelativeTo(null);	// center window
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	mainFrame.setLayout();
	
	mainPanel = new JPanel(new MigLayout("wrap, insets 5 , fill", "[]5[]5[]5[]", "[]5[]5[]5[]5[]5[]"));
	addLogoAndLabel();
	
	mainFrame.add(mainPanel, BorderLayout.CENTER);	
	addTableWithLoadedTaxpayers();

	addButtonsToFrame();
	show();
    }
    
    public void show() {	
	mainFrame.setVisible(true);
    }
    
    private void addLogoAndLabel() {
	welcomeLabel = new JLabel("Income Tax Calculator");
	welcomeLabel.setForeground(Color.WHITE);
	welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
	ImageIcon labelIcon = new ImageIcon("resources/logo.png");
	welcomeLabel.setIconTextGap(10);
	welcomeLabel.setIcon(labelIcon);
	mainPanel.add(welcomeLabel, "span, wrap");
    }
    
    private void addTableWithLoadedTaxpayers() {	
 	JLabel lblLoadedTaxpayers = new JLabel("Loaded Taxpayers:");
 	lblLoadedTaxpayers.setFont(new Font("Sans-serif", Font.BOLD, 16));
 	
 	loadedTaxpayersTable = new JTable() {
 	    @Override
 	    public boolean isCellEditable(int row, int column) {
 	       return false;
 	    }
 	};
 	tableModel = new DefaultTableModel(new Object[]{"Taxpayer Name", "Tax Registration Number"},0);
 	loadedTaxpayersTable.setModel(tableModel);
 	loadedTaxpayersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 	
 	scrollPane = new JScrollPane(loadedTaxpayersTable);
 	JButton help = new JButton("Help");
 	help.addActionListener(new ActionListener() {
 	    @Override
 	    public void actionPerformed(ActionEvent e) {
 		System.out.println("you need help");
 	    }
 	});
 	
 	mainPanel.add(lblLoadedTaxpayers, "");
 	mainPanel.add(help, "cell 3 1, grow,  wrap");
 	mainPanel.add(scrollPane, "span, grow");
 	
     }

    
    private void addButtonsToFrame() {
	addLoadTaxpayerButton();	
	addViewTaxpayer();
	addRemoveTaxpayerButton();
	JButton settings = new JButton("Settings");
	mainPanel.add(settings, "grow");
    }
    
    
    private void addViewTaxpayer() {
	JButton display = new JButton("View Taxpayer");
	display.addActionListener(new ActionListener() {
 	    @Override
 	    public void actionPerformed(ActionEvent e) {
 		int selectedRow = loadedTaxpayersTable.getSelectedRow();
 		if (selectedRow >= 0) {
 		    int trn = getSelectedTrnFromTable(); 
 		    String nameAndTRN = taxpayerManager.getTaxpayerName(trn)+" "+trn;
 		    new TaxpayerView(trn);
 		} else {
 		   JOptionPane.showMessageDialog(null, "To view a taxpayer info please select one from table", "No taxpayer selected", JOptionPane.INFORMATION_MESSAGE);
 		}
 	    }
 	});
	mainPanel.add(display,"grow" );
    }
    
    private void addRemoveTaxpayerButton() {
	JButton Remove = new JButton("Remove Taxpayer");
	Remove.addActionListener(new ActionListener() {
 	    @Override
 	    public void actionPerformed(ActionEvent e) {
 		int trn = getSelectedTrnFromTable();
 		if (trn > 0) {
 		    int result = JOptionPane.showConfirmDialog(null,
 			    "Are you sure you want to remove selected Taxpayer from the List?", "Remove Taxpayer?",
 			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
 		    if (result == JOptionPane.YES_OPTION) {
 			taxpayerManager.removeTaxpayer(trn);
 			tableModel.removeRow(loadedTaxpayersTable.getSelectedRow());
 		    } 		    
 		}
 	    }
 	});
	mainPanel.add(Remove, "grow");
    }
    
    private int getSelectedTrnFromTable() {
	int row = loadedTaxpayersTable.getSelectedRow();
	if (row >= 0) {
	    int trn = Integer.parseInt((String) tableModel.getValueAt(row, 1));	// prosoxi allazoun thesei oi stiles ???
	    return trn; 
	}
	return -1;
    }
    
    private void addLoadTaxpayerButton() {
	JButton load = new JButton("Load Taxpayer");
	load.addActionListener(new ActionListener() {
		@Override
                public void actionPerformed(ActionEvent e) {
		    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+"\\resources\\INFO files\\");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		    chooser.addChoosableFileFilter(new FileNameExtensionFilter("INFO", "txt"));
//		    chooser.addChoosableFileFilter(new FileNameExtensionFilter("INFO", "xml"));
		    chooser.setFileFilter(new InfoFileFilter());
		    chooser.setAcceptAllFileFilterUsed(false);
		    if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
                         if (file == null) {
                             return;
                         }
                         String fileName = chooser.getSelectedFile().getAbsolutePath();
                         try {
			    taxpayerManager.loadTaxpayer(fileName);
			    addNewTaxpayerToLoadedTable();
			    JOptionPane.showMessageDialog(null, "File: "+file.getName()+" was loaded succesfully.", "Succesful Load", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException | IOException | WrongFileFormatException
				| WrongFileEndingException | WrongTaxpayerStatusException | WrongReceiptKindException
				| WrongReceiptDateException | WrongFileReceiptSeperatorException e1) {
			    e1.printStackTrace();
			} catch (TaxpayerAlreadyLoadedException e1) {
			    JOptionPane.showMessageDialog(null, "Taxpayer in file: "+file.getName()+" is already loaded", "Taxpayer Already Loaded", JOptionPane.ERROR_MESSAGE);
			    e1.printStackTrace();
			} catch (Exception e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		    }
        	}
    	});
	mainPanel.add(load, "grow");
    }
    
    private void addNewTaxpayerToLoadedTable() {
	String lastLoadedTaxpayer[] = taxpayerManager.getLastLoadedTaxpayerNameAndTrn();
	tableModel.addRow(lastLoadedTaxpayer);	    
    }
}


