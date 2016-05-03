

	import java.awt.FlowLayout;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import java.util.List;

	import javax.swing.BorderFactory;
	import javax.swing.Box;
	import javax.swing.BoxLayout;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JTextField;

	public class ProductDisplay extends JFrame{
		// navigation panel:>>>>>
		private JPanel navigationPanel;
		private JTextField indexTextField;
		private JButton previousButton;
		private JLabel ofLabel;
		private JTextField maxTextField;
		private JButton nextButton;
		// product panel
		private JPanel productPanel;
		private JTextField productID;
		private JTextField name;
		private JTextField price;
		private JTextField description;
		private JTextField qty;
		private JLabel pID;
		private JLabel pname;
		private JLabel p;
		private JLabel des;
		private JLabel q;

		// search panel
		private JPanel searchPanel;
		private JTextField searchTextField;
		private JLabel searchTitle;
		private JButton searchButton;
		private JLabel searchLabel;
		// last two buttons

		private JButton browseButton;
		private JButton insertButton;

		// db related stuff
		ProductQuery products;
		Product currentProduct;
		private List<Product> results;
		private int numOfEntries;
		private int currentEntryIndex;

		public ProductDisplay() {
			super("Product List");
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));// 10 and 10 are
																	// the
																	// horizontal
																	// and vertical
																	// gap. centre
																	// is the
																	// allignment
			setSize(400, 300);
			setResizable(false);
			setVisible(true);

			products = new ProductQuery();// Connect to db and creat 3 prepared
											// statements
			// navigation panel initialization
			navigationPanel = new JPanel();
			previousButton = new JButton("Previous");
			indexTextField = new JTextField(2);
			ofLabel = new JLabel("Of");
			maxTextField = new JTextField(2);
			nextButton = new JButton("Next");
			nextButton.setEnabled(true);
			previousButton.setEnabled(true);
			indexTextField.setHorizontalAlignment(JTextField.CENTER);
			maxTextField.setHorizontalAlignment(JTextField.CENTER);
			maxTextField.setEditable(false);// read only text

			navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));// box
																						// goes
																						// to
																						// the
																						// x
																						// direction(horizontal)
			// add items to the navigation panel
			navigationPanel.add(previousButton);
			navigationPanel.add(Box.createHorizontalStrut(10));// gap
			navigationPanel.add(indexTextField);
			navigationPanel.add(Box.createHorizontalStrut(10));
			navigationPanel.add(ofLabel);
			navigationPanel.add(Box.createHorizontalStrut(10));
			navigationPanel.add(maxTextField);
			navigationPanel.add(Box.createHorizontalStrut(10));
			navigationPanel.add(nextButton);

			add(navigationPanel);// add navigation panel to the frame

			// initialize the object for the detail panel

			productPanel = new JPanel();
			// 5rows,col,hgap,vgap4
			productPanel.setLayout(new GridLayout(5, 2, 4, 4));
			productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.X_AXIS));
			productPanel.setBorder(BorderFactory.createTitledBorder("Products"));

			pID = new JLabel("ProductID");
			productID = new JTextField(10);

			pname = new JLabel("Product Name");
			name = new JTextField(10);

			p = new JLabel("Price");
			price = new JTextField(10);

			des = new JLabel("Decription");
			description = new JTextField(10);

			q = new JLabel("Quantity");
			qty = new JTextField(10);

			productPanel.add(pID);
			productPanel.add(productID);
			productPanel.add(pname);
			productPanel.add(name);
			productPanel.add(p);
			productPanel.add(price);
			productPanel.add(des);
			productPanel.add(description);
			productPanel.add(q);
			productPanel.add(qty);

			add(productPanel);

			searchPanel = new JPanel();
			searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
			searchPanel.setBorder(BorderFactory.createTitledBorder("Search for a product Name"));

			searchLabel = new JLabel("Enter name");
			searchTextField = new JTextField(10);
			searchButton = new JButton("SEARCH");

			searchPanel.add(Box.createHorizontalStrut(5));
			searchPanel.add(searchLabel);
			searchPanel.add(Box.createHorizontalStrut(10));
			searchPanel.add(searchTextField);
			searchPanel.add(Box.createHorizontalStrut(10));
			searchPanel.add(searchButton);
			searchPanel.add(Box.createHorizontalStrut(5));

			// add the search to the Jframe
			add(searchPanel);

			browseButton = new JButton("BROWSE ALL");
			insertButton = new JButton("INSERT BUTTON");

			add(browseButton);
			add(insertButton);

			// add the actionListeners to button etc
			previousButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// code to run if someone presses the previous button

					previousButtonPressed(e);
				}

			});
			nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					nextButtonPressed(e);

				}

			});

			indexTextField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					indexTextChanged(e);

				}

			});

			searchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					searchButtonPressed(e);

				}

			});

			browseButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					browseButtonPressed(e);

				}

			});

			insertButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					insertButtonPressed(e);

				}

			});

			addWindowListener(// adaptor classes
					new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							
							products.close();
							// exit program normally - no erros
							System.exit(0);
						}
					});
			showRecordDefault();
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		}

		public void browseButtonPressed(ActionEvent e) {
			try {
				
				results = products.getAllProduct();
				numOfEntries = results.size();

				if (numOfEntries != 0) {

					// load the first product into the GUI
					// get the first product in the array list results
					currentProduct = results.get(currentEntryIndex);
					productID.setText(currentProduct.getProductId() + "");
					name.setText(currentProduct.getProductName());
					price.setText(currentProduct.getPrice() + "");
					description.setText(currentProduct.getDescription());
					qty.setText(currentProduct.getQty() + "");
					maxTextField.setText(numOfEntries + "");
					indexTextField.setText((currentEntryIndex + 1) + "");
					previousButton.setEnabled(true);
					nextButton.setEnabled(true);
					productID.setEditable(false);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void insertButtonPressed(ActionEvent e) {

			int result = products.addProduct(name.getText(), Double.parseDouble(price.getText()), description.getText(),
					Integer.parseInt(qty.getText()));
			if (result == 1)
				JOptionPane.showMessageDialog(this, "Product added ", "Perfect", JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "Product not added", "Erro", JOptionPane.PLAIN_MESSAGE);

			

		}

		public void indexTextChanged(ActionEvent e) {
			try {
				currentEntryIndex = (Integer.parseInt(indexTextField.getText()) - 1);
				numOfEntries = results.size();

				if ( currentEntryIndex <= numOfEntries)

					// load the first product into the GUI
					// get the first product in the array list results
					currentProduct = results.get(currentEntryIndex);
				productID.setText(currentProduct.getProductId() + "");
				name.setText(currentProduct.getProductName());
				price.setText(currentProduct.getPrice() + "");
				description.setText(currentProduct.getDescription());
				qty.setText(currentProduct.getQty() + "");
				maxTextField.setText(numOfEntries + "");
				indexTextField.setText((currentEntryIndex + 1) + "");

				previousButton.setEnabled(true);
				nextButton.setEnabled(true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		public void previousButtonPressed(ActionEvent e) {

			currentEntryIndex--;
			// if you go father than first record
			// loop to the last record

			if (currentEntryIndex < 0) {
				currentEntryIndex = 0;
			}

			indexTextField.setText((currentEntryIndex + 1) + "");
			indexTextChanged(e);

		}

		public void nextButtonPressed(ActionEvent e) {
			currentEntryIndex++;
			// if you go father than first record
			// loop to the last record

			if (currentEntryIndex >=numOfEntries) {
				currentEntryIndex = numOfEntries -1 ;

			}
			indexTextField.setText((currentEntryIndex + 1) + "");
			indexTextChanged(e);
		}

		public void searchButtonPressed(ActionEvent e) {
			try {
				results = products.searchProduct(searchTextField.getText());
				numOfEntries = results.size();

				if (numOfEntries != 0) {

					// load the first product into the GUI
					// get the first product in the array list results
					currentEntryIndex = 0;
					currentProduct = results.get(currentEntryIndex);
					productID.setText(currentProduct.getProductId() + "");
					name.setText(currentProduct.getProductName());
					price.setText(currentProduct.getPrice() + "");
					description.setText(currentProduct.getDescription());
					qty.setText(currentProduct.getQty() + "");
					maxTextField.setText(numOfEntries + "");
					indexTextField.setText((currentEntryIndex + 1) + "");
					previousButton.setEnabled(true);
					nextButton.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(this, "No Macthing records found - now dispaly all records");
					browseButtonPressed(e);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	public void showRecordDefault(){
		results = products.getAllProduct();
		numOfEntries = results.size();

		if (numOfEntries != 0) {

			// load the first product into the GUI
			// get the first product in the array list results
			currentProduct = results.get(currentEntryIndex);
			productID.setText(currentProduct.getProductId() + "");
			name.setText(currentProduct.getProductName());
			price.setText(currentProduct.getPrice() + "");
			description.setText(currentProduct.getDescription());
			qty.setText(currentProduct.getQty() + "");
			maxTextField.setText(numOfEntries + "");
			indexTextField.setText((currentEntryIndex + 1) + "");
			previousButton.setEnabled(true);
			nextButton.setEnabled(true);
			productID.setEditable(false);
		}
	}
		public static void main(String[] arg) {
			new ProductDisplay();
			
			
		}

	}

