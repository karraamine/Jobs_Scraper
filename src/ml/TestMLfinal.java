package ml;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import javax.swing.*;

// import org.apache.logging.log4j.CloseableThreadContext.Instance;

import java.awt.*;

public class TestMLfinal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI(275);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void createAndShowGUI(int offerIdToPredict) throws Exception {
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
        String[] categoricalColumns = {"ContractType", "EducationLevel", "ExperienceLevel", "RemoteWork", "Sectors", "PersonalityTraits"};

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

        // Example: Predict the sector for a specific offer_id
        //int offerIdToPredict; // Replace with the actual offer_id
        String predictedSector = returnPredictedClass(j48, offerIdToPredict);
        System.out.println("Predicted Sector for Offer " + offerIdToPredict + ": " + predictedSector);
    }

    public static String returnPredictedClass(J48 classifier, int offerId) throws Exception {
        // Load data from SQL into Weka Instances for the specified offer_id
        Instances data = retrieveDataForOffer(offerId);

        if (data.numInstances() == 0) {
            throw new RuntimeException("No instances found for offer_id: " + offerId);
        }

        // Columns to be encoded
        String[] categoricalColumns = {"ContractType", "EducationLevel", "ExperienceLevel", "RemoteWork", "Sectors", "PersonalityTraits"};

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

        // Check if there are instances in the data
        if (data.numInstances() > 0) {
            // Select the first instance (you might need to adjust this based on your use case)
            Instance testInstance = data.instance(0);

            // Use the trained classifier to predict the class of the test instance
            double predictedClass = classifier.classifyInstance(testInstance);

            // Return the predicted class
            return data.attribute(data.classIndex()).value((int) predictedClass);
        } else {
            throw new RuntimeException("No instances available for prediction after label encoding.");
        }
    }


    private static Instances retrieveDataForOffer(int offerId) throws Exception {
        // Query to select data from your table for the specified offer_id
        String query = "SELECT ContractType, EducationLevel, ExperienceLevel, RemoteWork, PersonalityTraits, Sectors FROM Offre WHERE id_offre=" + offerId + " AND SiteName='rekrute.com' ";

        // Load data from SQL into Weka Instances
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setDatabaseURL("jdbc:mysql://localhost:3306/web-scraper");
        instanceQuery.setUsername("root");
        instanceQuery.setPassword("");
        instanceQuery.setQuery(query);

        return instanceQuery.retrieveInstances();
    }
}
