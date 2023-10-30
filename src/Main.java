  import javax.swing.*;
  import java.awt.*;
  import java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;
  
  public class Main extends JFrame {
      private Asset portfolio = null;
      private JTextField symbolField;
      private JTextField assetTypeField;
      private JTextField purchasePriceField;
      private JTextField quantityField;
      private JTextArea portfolioTextArea;
  
      public Main() {
          setTitle("Portfolio Manager");
          setSize(600, 400);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          getContentPane().setBackground(Color.YELLOW); // Yellow background
  
          JPanel panel = new JPanel();
          panel.setLayout(new GridLayout(6, 2));
          panel.setBackground(Color.YELLOW); // Yellow background
  
          symbolField = createTextField();
          assetTypeField = createTextField();
          purchasePriceField = createTextField();
          quantityField = createTextField();
  
          JButton addAssetButton = createButton("Add Asset", Color.BLUE, Color.WHITE);
          JButton displayPortfolioButton = createButton("Display Portfolio", Color.RED, Color.WHITE);
  
          portfolioTextArea = new JTextArea(10, 30);
          portfolioTextArea.setEditable(false);
          portfolioTextArea.setBackground(Color.YELLOW); // Yellow background
          portfolioTextArea.setForeground(Color.DARK_GRAY);
  
          JLabel symbolLabel = createLabel("Symbol:", Color.DARK_GRAY);
          JLabel assetTypeLabel = createLabel("Asset Type (1:Indian/0:Foreign/-1:Crypto):", Color.DARK_GRAY);
          JLabel purchasePriceLabel = createLabel("Purchase Price:", Color.DARK_GRAY);
          JLabel quantityLabel = createLabel("Quantity:", Color.DARK_GRAY);
  
          panel.add(symbolLabel);
          panel.add(symbolField);
          panel.add(assetTypeLabel);
          panel.add(assetTypeField);
          panel.add(purchasePriceLabel);
          panel.add(purchasePriceField);
          panel.add(quantityLabel);
          panel.add(quantityField);
          panel.add(addAssetButton);
          panel.add(displayPortfolioButton);
  
          add(panel, BorderLayout.NORTH);
          add(new JScrollPane(portfolioTextArea), BorderLayout.CENTER);
  
          addAssetButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  String symbol = symbolField.getText();
                  int assetType = Integer.parseInt(assetTypeField.getText());
                  double purchasePrice = Double.parseDouble(purchasePriceField.getText());
                  int quantity = Integer.parseInt(quantityField.getText());
  
                  portfolio = addAsset(portfolio, symbol, purchasePrice, quantity, assetType);
  
                  clearTextFields();
              }
          });
  
          displayPortfolioButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  displayPortfolio(portfolio);
              }
          });
      }
  
      public JTextField createTextField() {
          JTextField textField = new JTextField();
          textField.setBackground(Color.WHITE);
          textField.setForeground(Color.DARK_GRAY);
          return textField;
      }
  
      public JButton createButton(String text, Color background, Color textColor) {
          JButton button = new JButton(text);
          button.setBackground(background);
          button.setForeground(textColor);
          return button;
      }
  
      public JLabel createLabel(String text, Color textColor) {
          JLabel label = new JLabel(text);
          label.setForeground(textColor);
          return label;
      }
  
      public void clearTextFields() {
          symbolField.setText("");
          assetTypeField.setText("");
          purchasePriceField.setText("");
          quantityField.setText("");
      }
  
      public Asset createAsset(String symbol, double purchasePrice, int quantity, int assetType) {
          return new Asset(symbol, purchasePrice, quantity, assetType);
      }
  
      public Asset addAsset(Asset portfolio, String symbol, double purchasePrice, int quantity, int assetType) {
          Asset newAsset = createAsset(symbol, purchasePrice, quantity, assetType);
          newAsset.next = portfolio;
          return newAsset;
      }
  
      public void displayPortfolio(Asset portfolio) {
          portfolioTextArea.setText("Portfolio Summary:\n");
          portfolioTextArea.append(String.format("%-10s %-20s %-10s %-10s\n", "Symbol", "Purchase Price", "Quantity", "Type"));
          portfolioTextArea.append("--------------------------------------------------------------\n");
  
          Asset current = portfolio;
          double totalInvestmentINR = 0.0;
          double totalInvestmentUSD = 0.0;
          double totalInvestmentCryptosUSD = 0.0;
  
          while (current != null) {
              double purchasePriceINR;
              if (current.assetType == 1) {
                  purchasePriceINR = current.purchasePrice;
                  totalInvestmentINR += purchasePriceINR * current.quantity;
                  portfolioTextArea.append(String.format("%-10s ₹%-17.2f %-10d Indian\n", current.symbol, purchasePriceINR, current.quantity));
              } else if (current.assetType == 0) {
                  purchasePriceINR = current.purchasePrice * 75.0;
                  totalInvestmentUSD += current.purchasePrice * current.quantity;
                  portfolioTextArea.append(String.format("%-10s $%-17.2f %-10d Foreign\n", current.symbol, current.purchasePrice, current.quantity));
              } else {
                  totalInvestmentCryptosUSD += current.purchasePrice * current.quantity;
                  portfolioTextArea.append(String.format("%-10s $%-17.2f %-10d Cryptocurrency\n", current.symbol, current.purchasePrice, current.quantity));
              }
  
              current = current.next;
          }
  
          portfolioTextArea.append(String.format("Total Investment in INR for Stocks: ₹%.2f\n", totalInvestmentINR));
          portfolioTextArea.append(String.format("Total Investment in USD for Foreign Stocks: $%.2f\n", totalInvestmentUSD));
          portfolioTextArea.append(String.format("Total Investment in USD for Cryptocurrencies: $%.2f\n", totalInvestmentCryptosUSD));
      }
  
      public static void main(String[] args) {
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  Main frame = new Main();
                  frame.setVisible(true);
              }
          });
      }
  }
  

