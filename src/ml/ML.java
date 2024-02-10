package ml;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.Attribute;
import weka.core.Instance;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ML {
    public static void main(String[] args) throws Exception {

        // Return the data
        Instances data = returnData();

        // Return the Classifier
        J48 classifier = returnTheClassifier(data);

        // Return the Evualation
        Evaluation eval = returnEval(data, classifier);

        // Return the String of the Predicted Class
        String predictedClass = returnPredictedClass(classifier,5);


        // Display the Evaluation Results
        System.out.println("=== Evaluation Results ===");
        System.out.println(eval.toSummaryString());

        // Display Decision Tree Model
        System.out.println("=== Decision Tree Model ===");
        System.out.println(classifier);

        // Display Predicted Class
        System.out.println("Predicted Class: " +  predictedClass);
    }

    public static String returnPredictedClass(J48 classifier,int random) throws Exception{
        // Load the new CSV file
        CSVLoader newLoader = new CSVLoader();
        newLoader.setSource(new File("TestingData.csv")); // id_offre % 5 == 0
        Instances newData = newLoader.getDataSet();

        // Make sure the new data has the same structure as the training data
        newData.setClassIndex(newData.numAttributes() - 1); // Set the class index

        // Select a specific line from the new data (replace 0 with the index of the line you want to test)
        Instance testInstance = newData.instance(random); // Random - User Choice

        // Step 8: Use the trained classifier to predict the class of the test instance
        double predictedClass = classifier.classifyInstance(testInstance);

        // Step 9: Return the predicted
        String returnedPrediction = newData.attribute(newData.classIndex()).value((int) predictedClass);

        return returnedPrediction;
    }

    public static Instances returnData() throws IOException{
        
            // Step 1: Load CSV data into Weka Instances
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("TrainingData.csv")); // id_offre % 2 == 0
            Instances data = loader.getDataSet();

            // Step 2: Clean data by removing rows with any empty values
            data = removeRowsWithEmptyValues(data);

            // Check if any data remains after cleaning
            if (data.numInstances() == 0) {
                System.out.println("No instances remaining after data cleaning. Exiting.");
                return null;
            }
            return data;
    }

    public static J48  returnTheClassifier(Instances data) throws Exception{

            // Step 3: Identify the class attribute (replace "ClassAttributeName" with your actual class attribute name)
            Attribute classAttribute = data.attribute("class"); // class = ExperienceLevel

            // Check if the class attribute is found
            if (classAttribute == null) {
                System.out.println("Class attribute not found. Exiting.");
                return null;
            }

            // Set the class index
            data.setClass(classAttribute);

            // Step 4: Choose a classifier (J48 decision tree) and build the model
            J48 classifier = new J48();
            classifier.buildClassifier(data);

            return classifier;
    }

    public static Evaluation returnEval(Instances data, J48 classifier) throws Exception{

         // Step 5: Evaluate the model using cross-validation
         Evaluation eval = new Evaluation(data);
         eval.crossValidateModel(classifier, data, 10, new Random(1));

         return eval;
    }

    private static Instances removeRowsWithEmptyValues(Instances data) {
        Instances cleanedData = new Instances(data);

        // Iterate through each instance
        for (int i = cleanedData.numInstances() - 1; i >= 0; i--) {
            // Check if any attribute in the instance has missing values
            if (cleanedData.instance(i).hasMissingValue()) {
                // Remove the instance if it has missing values
                cleanedData.delete(i);
            }
        }

        return cleanedData;
    }
}
