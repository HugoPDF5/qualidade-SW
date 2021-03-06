package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.Package;
import model.ServiceItem;
import util.ButtonColumn;
import view.decorator.ShowPackageDecorator;
import view.forms.ServiceItemForm;
import controller.PackageController;
import exception.PackageException;

@SuppressWarnings("serial")

public class SearchPackage extends ServiceItemView {
	
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JTable jTable;
	private JTextField searchedPackageField;
	private JButton btnPesquisar;

	@Override
	public void createLabelsAndFields(JFrame frame, ServiceItem serviceItem) {

		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
		JLabel searchLabel = new JLabel("Pacotes");
		searchLabel.setBounds(350, 14, 446, 30);
		searchLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(searchLabel);
		
		JTable tableOfPackages = tableOfPackages();
		searchedPackageField.setBounds(227, 56, 446, 29);
		add(searchedPackageField);
		searchedPackageField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 141, 557, 317);
		add(scrollPane);
		scrollPane.setBackground(Color.WHITE);
		
		scrollPane.setViewportView(tableOfPackages);
		
		tableOfPackages.removeColumn(tableOfPackages.getColumnModel().getColumn(4));
		
		searchedPackageField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
				
			}
			
			public void warn(){
				if (searchedPackageField.getText().isEmpty()){
					btnPesquisar.setText("Listar Pacotes");
				} else {
					btnPesquisar.setText("Pesquisar");
				}
			}
		});
	}

	private JTable tableOfPackages() throws NumberFormatException {
		searchedPackageField = new JTextField();
		String[] columns = { "Nome", "Valor", "Dura????o", "Status", "Id", "A????es" };
		tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				boolean isEditable = false;
				if (column == 0 || column == 1 || column == 2 || column == 3) {
					isEditable = false;
				} else {
					isEditable = true;
				}
				return isEditable;
			}
		};
		final JTable tableOfPackages = new JTable(tableModel);
		tableOfPackages.setBackground(Color.WHITE);
		jTable = tableOfPackages;
		try {
			getSearchedPackage(searchedPackageField.getText());
		} catch (PackageException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Action showPackage = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int idPackage = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 4).toString());
				try {
					getPackageById(idPackage);
				} catch (PackageException e1) {
				}
			}
		};
		ButtonColumn buttonColumn2 = new ButtonColumn(tableOfPackages, showPackage, 4);
		searchedPackageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getSearchedPackage(searchedPackageField.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (PackageException e1) {
					e1.printStackTrace();
				}
			}
		});
		return tableOfPackages;
	}

	@Override
	public void createMasks(JFrame frame) {
		
	}

	@Override
	public void createButtons(JFrame frame) {
		
		btnPesquisar = new JButton("Listar Pacotes");
		btnPesquisar.setBounds(675, 56, 117, 29);
		add(btnPesquisar);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchedPackage = searchedPackageField.getText();
				
				boolean enteredPackage = !searchedPackage.isEmpty();
				
				if(enteredPackage == true){
					try {
						getSearchedPackage(searchedPackage);
					} catch (SQLException | PackageException e1) {
						e1.printStackTrace();
					}	
				}
				else{
					JOptionPane.showMessageDialog(null, "Digite algum texto para pesquisar por pacotes.");
					try {
						getSearchedPackage("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (PackageException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				jTable.updateUI();
			}
			
		});
		
	}
	
	
	/**
	 * Method used to pass the course value to monetary form
	 * @param value - Receives the course value on entire form
	 * @return - Return the course value on monetary form (R$)
	 */
	private String passValueToMonetaryForm(Integer value) {
		
		String decimalValue = decimalValue(value);
		String valueText= "";
		String entireValue = "";
		int lengthOfValue = 0;
		
		valueText = value.toString();
		lengthOfValue = valueText.length();
		entireValue = valueText.substring(0, (lengthOfValue - 2));
		valueText = entireValue + "," + decimalValue;
		
		return valueText;
	}

	private String decimalValue(Integer value) {
		String valueText = "";
		String decimalValue = "";
		int lengthOfValue = 0;
		valueText = value.toString();
		lengthOfValue = valueText.length();
		decimalValue = valueText.substring((lengthOfValue - 2), lengthOfValue);
		return decimalValue;
	}
	
	public void getSearchedPackage(String name) throws SQLException, PackageException{
		
		DefaultTableModel model = (DefaultTableModel) jTable.getModel();
	    model.setRowCount(0);
		
		PackageController packageController = new PackageController();
		ArrayList<model.Package> arrayListPackage = packageController.searchPackageByName(name);		
		
		if (arrayListPackage == null){
			JOptionPane.showMessageDialog(jTable, "N??o foi poss??vel encontrar resultados para a pesquisa.");
		} else {
		
			int lengthArray = 0;
			int i = 0;
		
			lengthArray = arrayListPackage.size();
		
			while (lengthArray > i) {
				String[] packages = new String[6];
				packages[0] = arrayListPackage.get(i).getName();
				packages[1] = "R$ " + passValueToMonetaryForm(arrayListPackage.get(i).getValue());
				packages[2] = arrayListPackage.get(i).getDuration().toString() + " semana(s)";
				packages[3] = showActiveOrInactive(arrayListPackage.get(i).getStatus());
				packages[4] = arrayListPackage.get(i).getId().toString();
				packages[5] = "Ver";
				tableModel.addRow(packages);
				i++;
			}
		}
	}
	
	private String showActiveOrInactive(int status){
		
		String statusToShow = null;
		
		statusToShow = getStatusObject(status).showActiveOrInactive(statusToShow);
		
		return statusToShow;
		
	}
	
	public void getPackageById(final int idPackage) throws PackageException{

		final PackageController packageController = new PackageController();
		final Package packageToShow;
		
		DefaultListModel<String> courseListModel = new DefaultListModel<String>();
		final ArrayList<String> coursesName;
		packageToShow = packageController.showPackage(idPackage);

		dispose();
		ServiceItemView showPackageFrame = new ShowPackageDecorator(new ServiceItemForm());
		showPackageFrame.buildScreen(showPackageFrame, packageToShow);
		showPackageFrame.setVisible(true);

	}

	private Status getStatusObject(int status) {
		if (status == ServiceItemView.SERVICE_ACTIVE)
			return new ServiceActive();
		return null;
	}

}
