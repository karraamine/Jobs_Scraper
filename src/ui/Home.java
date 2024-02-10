package ui;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
// import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
// import javax.swing.JComboBox;
// import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import ml.ML;
import model.Piecharts;
import model.getInfo;
import model.returnResult;
import mswing.CustomButton;
import mswing.CustomCheckbox;
import mswing.CustomComboBox;
import mswing.CustomField;
import mswing.CustomFrame;
import mswing.CustomPanel;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class Home implements MouseListener,ActionListener{
  CustomFrame frame;
  JPanel topPanel ;
  JPanel sidePanel;
  JPanel mainPanel;
  CardLayout  cardLayout;
  JLabel sidepanelLabel1;
  JLabel sidepanelLabel2;
  JLabel sidepanelLabel3;
  CustomField searchField;
  CustomButton submitButton;
  CustomButton chartsButton;
  CustomButton trainingMlButton;
  CustomButton predictClassMlButton;

  CustomComboBox<String> contrasCombo;
  CustomComboBox<String> citiesCombo;
  CustomComboBox<String> chartsCombo;

  CustomCheckbox c1;
  CustomCheckbox c2;
  CustomCheckbox c3;
  CustomCheckbox c4;
  CustomCheckbox c5;
  CustomCheckbox c6;

  JPanel card1Main ;
  JLabel mlResult;



  private static void addPlaceholder(JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY);
    // textField.setFont(new Font("arial",Font.BOLD,12));

    textField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }

        
    });
}
  public Home(){
    // BORDERS
    Border buttomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xcccccc));
    Border rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0xcccccc));
    Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 40);


    frame = new CustomFrame();
    frame.setSize(800,580);
    frame.setLocationRelativeTo(null);

    topPanel = new JPanel();
    sidePanel = new JPanel();
    mainPanel = new JPanel();

    
    // MAIN PANEL (CARD LAYOUT)
    mainPanel.setBackground(new Color(0xF1F3F5));
    cardLayout = new CardLayout();
    mainPanel.setLayout(cardLayout);
    
    // CARDS OF MAIN PANEL 
    JPanel card1 = new JPanel();
    JPanel card2 = new JPanel();
    JPanel card3 = new JPanel();

    // ADDING CARDS TO MAIN PANEL
    mainPanel.add(card1,"card 1");
    mainPanel.add(card2,"card 2");
    mainPanel.add(card3,"card 3");
    
    // DEFAULT CARD = CARD 1 = CLASSIC SEARCH
    cardLayout.show(mainPanel,"card 1");


    FlowLayout fl = new FlowLayout();
    fl.setVgap(10);
    fl.setHgap(30);
    card1.setLayout(new BorderLayout());
    
    // CLASSIC SEARCH
    // new OfferCard(card1,new Color(0x74C0FC),"UI/UX designer","Google");
    // new OfferCard(card1,new Color(0xFFE066),"Datascientest","Airbnb");
    // new OfferCard(card1,new Color(0xC0EB75),"U");
    // new OfferCard(card1,new Color(0xC0EB75),"U");

    searchField = new CustomField();
    addPlaceholder(searchField, "Secteur");
    searchField.setPreferredSize(new Dimension(380,35));
    submitButton = new CustomButton();
    submitButton.setText("Find Job");
    submitButton.setBorder(new EmptyBorder(10, 10, 10, 10));
    submitButton.setBackground(new Color(0x6A70E0));
    submitButton.setBorderRadius(16);
    submitButton.setForeground(Color.WHITE);
    submitButton.addActionListener(this);


    c1 = new CustomCheckbox();
    c2 = new CustomCheckbox();
    c3 = new CustomCheckbox();
    c4 = new CustomCheckbox();
    c5 = new CustomCheckbox();
    c6 = new CustomCheckbox();

    c1.setText("rekrute.com");
    c2.setText("m-job.ma");
    c3.setText("announce.ma");
    c4.setText("emploi.ma");
    c5.setText("marocannonces.com");
    c6.setText("talentspartners.com");



    JPanel card1HeaderTop = new JPanel();
    JPanel card1HeaderMiddle = new JPanel();
    JPanel card1HeaderBottom = new JPanel();
    JPanel card1HeaderBottom2 = new JPanel();




    // HEADER => MIDDLE
    String[] cities = {"Agadir", "Casablanca", "Fes", "Marrakech", "Meknes",
    "Nador", "Ouarzazate", "Oujda", "Rabat", "Safi",
    "Salé", "Tanger", "Tetouan", "El Jadida", "Kenitra",
    "Laayoune", "Dakhla", "Tetouan", "Mohammedia", "Marrakesh",
    "Khouribga", "Temara", "Taza", "Larache", "Tan-Tan", "Al Hoceima",
    "Azrou", "Youssoufia", "Sefrou", "Taroudant", "Berkan"};
    citiesCombo = new CustomComboBox<String>();
    citiesCombo.setFocusable(false);
    citiesCombo.setPreferredSize(new Dimension(185,40));
    citiesCombo.setLabeText("Cities");
    citiesCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(cities));
    citiesCombo.setLineColor(new Color(0x6A70E0));
    citiesCombo.setSelectedIndex(-1);
    card1HeaderMiddle.add(citiesCombo);

    String[] contras = { "CDI", "Intérim", "CDD", "Freelance",
    "Stage"};
    contrasCombo = new CustomComboBox<String>();
    contrasCombo.setFocusable(false);
    contrasCombo.setPreferredSize(new Dimension(185,40));
    contrasCombo.setLabeText("Contras");
    contrasCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(contras));
    contrasCombo.setLineColor(new Color(0x6A70E0));
    contrasCombo.setSelectedIndex(-1);
    card1HeaderMiddle.add(contrasCombo);


    JPanel card1Header = new JPanel();
    card1Header.setLayout(new BoxLayout(card1Header,BoxLayout.Y_AXIS));
    
    FlowLayout fl2 = new FlowLayout();
    card1HeaderTop.setLayout(fl2);

    card1HeaderBottom.add(c1);
    card1HeaderBottom.add(c2);
    card1HeaderBottom.add(c3);
    card1HeaderBottom.add(c4);
    card1HeaderBottom.add(c5);
    card1HeaderBottom.add(c6);



    card1HeaderTop.add(searchField); 
    card1HeaderBottom2.add(submitButton);
    
    card1Header.add(Box.createRigidArea(new  Dimension(0, 30)));
    card1Header.add(card1HeaderTop);
    card1Header.add(card1HeaderMiddle);
    card1Header.add(card1HeaderBottom);
    card1Header.add(card1HeaderBottom2);
    card1Header.add(Box.createRigidArea(new  Dimension(0, 30)));


    card1Main = new JPanel();
    card1Main.setLayout(fl);

    // JFileChooser fileChooser = new JFileChooser();
    // fileChooser.setDialogTitle("Specify a file to save");
    // int userSelection = fileChooser.showSaveDialog(card1Main);
    // if (userSelection == JFileChooser.APPROVE_OPTION) {
    //   File fileToSave = fileChooser.getSelectedFile();
    //   System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    // }


    submitButton.setFocusable(false);
    card1.add(card1Header,BorderLayout.NORTH);
    JScrollPane scroller = new JScrollPane( card1Main );
    // ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    // JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    // card1Main.setLayout();
    
    scroller.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    card1.add(scroller);
    
    // new OfferCard(card1Main,new Color(0x74C0FC),"UI/UX designer","Google");
    // new OfferCard(card1Main,new Color(0xFFE066),"Datascientest","Airbnb");
    // new OfferCard(card1Main,new Color(0xFFE066),"Datascientest","Airbnb");
    // new OfferCard(card1Main,new Color(0x74C0FC),"UI/UX designer","Google");







    // AI POWERD SEARCH
    trainingMlButton = new CustomButton();
    trainingMlButton.setText("ML MODELS");
    trainingMlButton.setBorder(new EmptyBorder(10, 10, 10, 10));
    trainingMlButton.setBackground(new Color(0x6A70E0));
    trainingMlButton.setBorderRadius(16);
    trainingMlButton.setForeground(Color.WHITE);
    trainingMlButton.addActionListener(this);
    trainingMlButton.setFocusable(false);

    predictClassMlButton = new CustomButton();
    predictClassMlButton.setText("PREDICT EXPERIENCE");
    predictClassMlButton.setBorder(new EmptyBorder(10, 10, 10, 10));
    predictClassMlButton.setBackground(new Color(0x6A70E0));
    predictClassMlButton.setBorderRadius(16);
    predictClassMlButton.setForeground(Color.WHITE);
    predictClassMlButton.addActionListener(this);
    predictClassMlButton.setFocusable(false);

    mlResult = new JLabel();
    




    // card2.setLayout();
    JPanel panel0 = new JPanel();
    panel0.add(trainingMlButton);
    panel0.add(predictClassMlButton);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    
    panel.add(Box.createRigidArea(new  Dimension(0, 15)));
    panel.add(panel0);
    panel.add(Box.createRigidArea(new  Dimension(0, 15)));
    mlResult.setAlignmentX(JPanel.CENTER_ALIGNMENT);
    mlResult.setPreferredSize(new Dimension(500,300));
    panel.add(mlResult);
    card2.add(panel);
    

    // GRAPHICS 
    // String[] charts = { "Offers by City", "Intérim", "CDD", "Freelance",
    // "Stage"};
    // chartsCombo = new CustomComboBox<String>();
    // chartsCombo.setFocusable(false);
    // chartsCombo.setPreferredSize(new Dimension(185,40));
    // chartsCombo.setLabeText("Charts");
    // chartsCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(charts));
    // chartsCombo.setLineColor(new Color(0x6A70E0));
    // chartsCombo.setSelectedIndex(-1);

    // chartsButton = new CustomButton();
    // chartsButton.setText("Show Chart");
    // chartsButton.setBorder(new EmptyBorder(10, 10, 10, 10));
    // chartsButton.setBackground(new Color(0x6A70E0));
    // chartsButton.setBorderRadius(16);
    // chartsButton.setForeground(Color.WHITE);
    // chartsButton.addActionListener(this);
    // chartsButton.setFocusable(false);

    // JPanel panel2 = new JPanel();


    // panel2.add(chartsCombo);
    // panel2.add(chartsButton);
    // card3.add(panel2);
    
    // JScrollPane scrollPane = new JScrollPane();


    Piecharts pie = new Piecharts();
    // pie.setPreferredSize(new Dimension( 2000,2000));
    JScrollPane scrollFrame = new JScrollPane( pie);
    // ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    // JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
    scrollFrame.setPreferredSize(new Dimension(750,500));
    scrollFrame.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    pie.setAutoscrolls(true);
    card3.add(scrollFrame);
    
  

    topPanel.setPreferredSize(new Dimension(0,35));
    topPanel.setBackground(Color.WHITE);
    topPanel.setBorder(buttomBorder);

    

    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
    sidePanel.setBackground(Color.WHITE);
    sidePanel.setBorder(rightBorder);

    sidepanelLabel1 = new JLabel("Classic search");
    ImageIcon lb1Icon = new ImageIcon("assets/search.png");
    sidepanelLabel1.setIcon(lb1Icon);
    sidepanelLabel1.setIconTextGap(12);
    sidepanelLabel1.setForeground(new Color(0x212121));
    sidepanelLabel1.setFont(new Font("Arial",Font.PLAIN,14));
    sidepanelLabel1.setBorder(BorderFactory.createCompoundBorder(buttomBorder, emptyBorder));
    sidepanelLabel1.addMouseListener(this);
    sidePanel.add(sidepanelLabel1);

    
    sidepanelLabel2 = new JLabel("AI Powered    ");
    ImageIcon lb2Icon = new ImageIcon("assets/sparks.png");
    sidepanelLabel2.setIcon(lb2Icon);
    sidepanelLabel2.setIconTextGap(12);
    sidepanelLabel2.setForeground(new Color(0x212121));
    sidepanelLabel2.setFont(new Font("Arial",Font.PLAIN,14));
    sidepanelLabel2.setBorder(BorderFactory.createCompoundBorder(buttomBorder, emptyBorder));
    sidepanelLabel2.addMouseListener(this);
    sidePanel.add(sidepanelLabel2);
    
    sidepanelLabel3 = new JLabel("Graphics        ");
    ImageIcon lb3Icon = new ImageIcon("assets/graph.png");
    sidepanelLabel3.setIcon(lb3Icon);
    sidepanelLabel3.setIconTextGap(12);
    sidepanelLabel3.setForeground(new Color(0x212121));
    sidepanelLabel3.setFont(new Font("Arial",Font.PLAIN,14));
    sidepanelLabel3.setBorder(BorderFactory.createCompoundBorder(buttomBorder, emptyBorder));
    sidepanelLabel3.addMouseListener(this);
    sidePanel.add(sidepanelLabel3);






    // ADDING PANLES TO FRAME ( BORDER LAYOUT ) 
    frame.add(topPanel,BorderLayout.NORTH);
    frame.add(sidePanel,BorderLayout.WEST);
    frame.add(mainPanel,BorderLayout.CENTER);


    frame.setVisible(true);


    

    


  }
  // MOUSE LISTENER METHODS
  @Override
  public void mouseClicked(MouseEvent event) {
    if (event.getSource() == sidepanelLabel1) {
      cardLayout.show(mainPanel,"card 1");
    }

    if (event.getSource() == sidepanelLabel2) {
      cardLayout.show(mainPanel,"card 2");
    }

    if(event.getSource() == sidepanelLabel3){
      cardLayout.show(mainPanel,"card 3");
    }
    


  }
  @Override
  public void mouseEntered(MouseEvent event) {
  }
  @Override
  public void mouseExited(MouseEvent event) {
  }
  @Override
  public void mousePressed(MouseEvent event) {
  }
  @Override
  public void mouseReleased(MouseEvent event) {
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    if(event.getSource() == submitButton){ 
      String sector = searchField.getText();
      searchField.setText("");
      // String[3] sites = {};
      ArrayList<String> sites = new ArrayList<String>();
      
      if(c1.isSelected()){
        sites.add("rekrute.com");
      }
      if(c2.isSelected()){
        sites.add("m-job.ma");
      }
      if(c3.isSelected()){
        sites.add( "announce.ma");
      }
      if(c4.isSelected()){
        sites.add( "Emploi.ma");
      }
      if(c5.isSelected()){
        sites.add("marocannonces.com");
      }
      if(c6.isSelected()){
        sites.add("talentspartners.com");
      }

      String selectedCity = (String) citiesCombo.getSelectedItem();
      selectedCity = selectedCity.toLowerCase();
      String selectedContras = (String) contrasCombo.getSelectedItem();
      selectedContras = selectedContras.toLowerCase();

      getInfo infoInstance = new getInfo();

      // Call the method
      List<returnResult> resultSet = null;
      try {
        System.out.println("sites" + sites);
        resultSet = infoInstance.findOffres(sector, selectedCity, selectedContras, sites);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      // frame.invalidate();
      // frame.validate();
      // frame.repaint();
      SwingUtilities.updateComponentTreeUI(card1Main);
      card1Main.removeAll();

      if(resultSet.size() == 0){
        System.out.println("no data found");
        card1Main.add(new JLabel("no data found"));
      }
      for(returnResult rs : resultSet){
          new OfferCard(card1Main,new Color(0x6A70E0),rs.Title,rs.CompanyName,rs.PublicationDate,rs.SiteName);
          System.out.println("Title : " + rs.Title);
          System.out.println("CompanyName : " + rs.CompanyName);
          System.out.println("PublicationDate : " + rs.PublicationDate);
          System.out.println("SiteName : " + rs.SiteName);
      }

    }

    if(event.getSource() == chartsButton){
      String selectedChart = (String) chartsCombo.getSelectedItem();
      int selectedChartIndex = chartsCombo.getSelectedIndex();

      System.out.println("selectedChart : "+ selectedChart);
      if(selectedChartIndex == 0){
        new Piecharts();
      }
    }

    if(event.getSource() == trainingMlButton){
      Instances data;
      Evaluation eval = null; 
      J48 classifier = null;
      try {
        data = ML.returnData();
        // Return the Classifier
        classifier =  ML.returnTheClassifier(data);

        // Return the Evualation
        eval =  ML.returnEval(data, classifier);

      } catch (Exception e) {
        e.printStackTrace();
      }

      

      // Display the Evaluation Results
      // System.out.println();
      // System.out.println(eval.toSummaryString());
      // System.out.println("=== Decision Tree Model ===");
      // System.out.println(classifier);
      String[] result = eval.toSummaryString().split("%");
      mlResult.setText("<html>"+"=== Evaluation Results ===<br/>"
      +result[0] + "<br/>"
      +result[1] + "<br/>"
      +result[2] + "<br/>"
      +result[3] + "<br/>"
      +"<br/>=== Decision Tree Model ===<br/>"
      + classifier
      +"<html/>"
      );
    }
    if(event.getSource() == predictClassMlButton){
      Instances data;
      Evaluation eval = null; 
      J48 classifier = null;
      String predictedClass = null;
      try {
        data = ML.returnData();
        // Return the Classifier
        classifier =  ML.returnTheClassifier(data);

        // Return the Evualation
        eval =  ML.returnEval(data, classifier);

        // Return the String of the Predicted Class
        int randomNum = ThreadLocalRandom.current().nextInt(1,15);
        predictedClass =  ML.returnPredictedClass(classifier,randomNum);
        System.out.println("offer "+ randomNum+ ", predicted experience = "+ predictedClass);

      } catch (Exception e) {
        e.printStackTrace();
      }
      mlResult.setText("prediction : "+predictedClass);
    }


  }
}


//  -------------------OFFER CARD CLASS------------------------

class OfferCard extends CustomPanel {
  public OfferCard(JPanel parent,Color color,String jobTitle,String company,String date,String site){

    CustomPanel offerCard = new CustomPanel();
    offerCard.setBackground(Color.WHITE);
    offerCard.setPreferredSize(new Dimension(350,100));
    offerCard.setRoundAll(16);
    offerCard.setLayout(new BorderLayout());

    CustomPanel offerCardHeader = new CustomPanel();
    offerCardHeader.setBackground(color);
    offerCardHeader.setPreferredSize(new Dimension(60,0));
    offerCardHeader.setRoundAll(16, 0, 16, 0);

    CustomPanel logoContainer = new CustomPanel();
    logoContainer.setBackground(Color.WHITE);
    logoContainer.setPreferredSize(new Dimension(30,30));
    logoContainer.setRoundAll(50);

    JLabel logoLabel = new JLabel(String.valueOf(company.charAt(0)).toUpperCase());
    logoLabel.setForeground(color);
    logoContainer.setLayout(new BorderLayout());
    logoLabel.setVerticalAlignment(JLabel.CENTER);
    logoLabel.setHorizontalAlignment(JLabel.CENTER);
    logoLabel.setFont(new Font("arial",Font.PLAIN,14));
    logoContainer.add(logoLabel);

    offerCardHeader.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    offerCardHeader.add(logoContainer,gbc);
    


    CustomPanel offerCardBody = new CustomPanel();
    offerCardBody.setBackground(Color.WHITE);
    offerCardBody.setRoundAll(0, 16, 0, 16);
    offerCard.add(offerCardHeader,BorderLayout.WEST);
    offerCard.add(offerCardBody,BorderLayout.CENTER);
    offerCardBody.setLayout(new BoxLayout(offerCardBody, BoxLayout.Y_AXIS));

    JLabel postName = new JLabel(jobTitle);
    postName.setFont(new Font("arial",Font.BOLD,13));
    postName.setForeground(new Color(0x212121));
    postName.setAlignmentX(LEFT_ALIGNMENT);
  
    JLabel companyName = new JLabel(company);
    companyName.setFont(new Font("arial",Font.PLAIN,10));
    companyName.setForeground(new Color(0x212121));
    companyName.setAlignmentX(LEFT_ALIGNMENT);

    JPanel datesiteContainer = new JPanel();
    datesiteContainer.setLayout(new BoxLayout(datesiteContainer,BoxLayout.X_AXIS));
    datesiteContainer.setBackground(Color.WHITE);
    datesiteContainer.setAlignmentX(LEFT_ALIGNMENT);

    JLabel datePost = new JLabel("poseted on "+date);
    datePost.setFont(new Font("arial",Font.PLAIN,10));
    datePost.setForeground(new Color(0xbbbbbb));
    
    JLabel sitePost = new JLabel("via "+site);
    sitePost.setFont(new Font("arial",Font.PLAIN,10));
    sitePost.setForeground(new Color(0xbbbbbb));

    

    offerCardBody.add(postName);
    offerCardBody.add(Box.createVerticalGlue());
    offerCardBody.add(companyName);
    offerCardBody.add(datePost);
    offerCardBody.add(sitePost);
    offerCardBody.add(datesiteContainer);
    datesiteContainer.add(datePost);
    datesiteContainer.add(Box.createRigidArea(new  Dimension(20, 0)));
    datesiteContainer.add(sitePost);

    postName.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
    companyName.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
    datesiteContainer.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));



    // companyName.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));



  



    parent.add(offerCard);
  }
}