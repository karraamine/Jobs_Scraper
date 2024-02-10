package ml;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import javax.swing.*;
import java.awt.*;

public class TestML {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void createAndShowGUI() throws Exception {
        JFrame frame = new JFrame("Job Offer Classifier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Query to select data from your table
        String query = "SELECT ContractType, EducationLevel, ExperienceLevel, RemoteWork, PersonalityTraits, Sectors FROM Offre WHERE SiteName='rekrute.com' ";

        // Load data from SQL into Weka Instances
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setDatabaseURL("jdbc:mysql://localhost:3306/web-scraper");
        instanceQuery.setUsername("root");
        instanceQuery.setPassword("");
        instanceQuery.setQuery(query);

        Instances data = instanceQuery.retrieveInstances();

        // Columns to be encoded
        String[] categoricalColumns = {"ContractType", "EducationLevel", "ExperienceLevel", "RemoteWork", "Sectors","PersonalityTraits"};

        // Apply Label Encoding
        for (String column : categoricalColumns) {
            StringToNominal stringToNominalFilter = new StringToNominal();
            stringToNominalFilter.setAttributeRange(Integer.toString(data.attribute(column).index() + 1)); // Apply to all attributes

            // Set input format
            stringToNominalFilter.setInputFormat(data);

            int columnIndex = data.attribute(column).index();
            data = Filter.useFilter(data, stringToNominalFilter);
        }

        // Set class index
        data.setClassIndex(data.attribute("Sectors").index());

        // Split the data into training and testing sets
        Instances train = data.trainCV(10, 0);
        Instances test = data.testCV(10, 0);

        // Train the model (J48 classifier)
        J48 j48 = new J48();
        j48.buildClassifier(train);

        // Evaluate the model
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(j48, test);

        // Display evaluation metrics using Swing
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Accuracy: " + eval.pctCorrect() + "%", SwingConstants.CENTER));

        JTextArea summaryTextArea = new JTextArea(eval.toSummaryString(), 10, 50);
        summaryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        panel.add(scrollPane);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

