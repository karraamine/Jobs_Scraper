package ml;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RF {
    public static void main(String[] args) throws Exception {

        // Return the data
        Instances data = returnData();

        // Return Testing Data
        // Instances Testingdata = returnTestingData();

        // Return the Classifier
        RF classifier = returnTheClassifier(data);

        // Return the Evaluation
        Evaluation eval = returnEval(data, classifier);

        // Return the String of the Predicted Class
        String predictedClass = returnPredictedClass(classifier, data);



        // Display the Evaluation Results
        System.out.println("=== Evaluation Results ===");
        System.out.println(eval.toSummaryString());

        // Display Random Forest Model
        System.out.println("=== Random Forest Model ===");
        System.out.println(classifier);

        // Display Predicted Class
        System.out.println("Predicted Class: " + predictedClass);
    }

    public static String returnPredictedClass(RF classifier, Instances data) throws Exception {
        // Set the class index for the new data
        data.setClassIndex(data.numAttributes() - 1);
    
        // Create a new instance for prediction
        Instance testInstance = new DenseInstance(data.numAttributes());
    
        // Associate the dataset with the new instance
        testInstance.setDataset(data);
    
        // Copy attribute values from the instance you want to predict
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            testInstance.setValue(data.attribute(i), data.instance(7).value(i));
        }
    
        // Use evaluateModelOnceAndRecordPrediction to get the prediction
        double[] predictions = classifier.distributionForInstance(testInstance);
    
        // Get the predicted class index
        int predictedClassIndex = (int) weka.core.Utils.maxIndex(predictions);
    
        // Get the actual class attribute
        Attribute classAttribute = data.classAttribute();
    
        // Get the actual class values
        String[] classValues = new String[classAttribute.numValues()];
        for (int i = 0; i < classValues.length; i++) {
            classValues[i] = classAttribute.value(i);
        }
    
        // Return the predicted class
        String returnedPrediction = classValues[predictedClassIndex];
        return returnedPrediction;
    }
    

    
    

    private double[] distributionForInstance(Instance testInstance) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'distributionForInstance'");
    }

    public static Instances returnData() throws IOException {

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

    public static Instances returnTestingData() throws IOException {

        // Step 1: Load CSV data into Weka Instances
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("TestingData.csv")); // id_offre % 2 == 0
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

    public static RF returnTheClassifier(Instances data) throws Exception {

        // Step 3: Identify the class attribute (replace "ClassAttributeName" with your actual class attribute name)
        Attribute classAttribute = data.attribute("class"); // class = ExperienceLevel

        // Check if the class attribute is found
        if (classAttribute == null) {
            System.out.println("Class attribute not found. Exiting.");
            return null;
        }

        // Set the class index
        data.setClass(classAttribute);

        // Step 4: Choose a classifier (RandomForest decision tree) and build the model
        RF classifier = new RF();
        classifier.returnTheClassifier(data);

        return classifier;
    }

    public static Evaluation returnEval(Instances data, RF classifier) throws Exception {

        // Step 5: Evaluate the model using cross-validation
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel((Classifier) classifier, data, 10, new Random(1));

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
