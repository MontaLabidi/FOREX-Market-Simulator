package com.fms;

import org.json.JSONObject;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Scanner;


public class TradingBot {


    public static void main(String[] args) throws Exception {

        //load training dataset
        DataSource source = new DataSource("data/dataset.csv");
        Instances data = source.getDataSet();
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndicesArray(new int[]{2, 3, 4, 5, 6});
        removeFilter.setInvertSelection(true);
        removeFilter.setInputFormat(data);
        Instances dataset = Filter.useFilter(data, removeFilter);
        //set class index to the last attribute
        dataset.setClassIndex(dataset.numAttributes() - 1);
        //get number of classes
        int numClasses = dataset.numClasses();
        //print out class values in the training dataset
        for (int i = 0; i < numClasses; i++) {
            //get class string value using the class index
            String classValue = dataset.classAttribute().value(i);
            System.out.println("Class Value " + i + " is " + classValue);
        }
        //create and build the classifier
        RandomForest forest = new RandomForest();
        forest.setNumIterations(10);
        forest.buildClassifier(dataset);

        //load new dataset
        DataSource source1 = new DataSource("data/test.csv");
        Instances testDataset = source1.getDataSet();
        testDataset = Filter.useFilter(testDataset, removeFilter);
        //set class index to the last attribute
        testDataset.setClassIndex(testDataset.numAttributes() - 1);
        //loop through the new dataset and make predictions
		/*System.out.println("===================");
		System.out.println("Actual Class, NB Predicted");
		for (int i = 0; i < testDataset.numInstances(); i++) {
			//get class double value for current instance
			double actualClass = testDataset.instance(i).classValue();
			//get class string value using the class index using the class's int value
			String actual = testDataset.classAttribute().value((int) actualClass);
			//get Instance object of current instance
			Instance newInst = testDataset.instance(i);
			//call classifyInstance, which returns a double value for the class
			double predNB2 = forest.classifyInstance(newInst);
			//use this value to get string value of the predicted class
			String predString2 = testDataset.classAttribute().value((int) predNB2);
			System.out.println(actual+", "+predString2);
		}*/
        Evaluation eval = new Evaluation(dataset);
        Random rand = new Random(1);
        int folds = 10;
        eval.crossValidateModel(forest, dataset, folds, rand);
        System.out.println(eval.toSummaryString("Evaluation results:\n", false));

        System.out.println("Correct % = " + eval.pctCorrect());
        System.out.println("Incorrect % = " + eval.pctIncorrect());
        System.out.println("AUC = " + eval.areaUnderROC(1));
        System.out.println("kappa = " + eval.kappa());
        System.out.println("MAE = " + eval.meanAbsoluteError());
        System.out.println("RMSE = " + eval.rootMeanSquaredError());
        System.out.println("RAE = " + eval.relativeAbsoluteError());
        System.out.println("RRSE = " + eval.rootRelativeSquaredError());
        System.out.println("Precision = " + eval.precision(1));
        System.out.println("Recall = " + eval.recall(1));
        System.out.println("fMeasure = " + eval.fMeasure(1));
        System.out.println("Error Rate = " + eval.errorRate());
        //the confusion matrix
        System.out.println(eval.toMatrixString("=== Overall Confusion Matrix ===\n"));


        String baseURL = "http://127.0.0.1:8080/api";
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String param = "1";
        String query = String.format("/getcurrency=%s",
                URLEncoder.encode(param, charset));

        URLConnection connection = new URL(baseURL + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);

        InputStream response = connection.getInputStream();
        // InputStream response = new URL(url).openStream();
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            JSONObject myResponse = new JSONObject(responseBody);
            System.out.println(myResponse);

            Instance inst = new DenseInstance(5);
            inst.setDataset(dataset);
            inst.setValue(0, (double) myResponse.get("open"));
            inst.setValue(1, (double) myResponse.get("high"));
            inst.setValue(2, (double) myResponse.get("low"));
            inst.setValue(3, (double) myResponse.get("close"));
            double pred = forest.classifyInstance(inst);

            //use this value to get string value of the predicted class
            String predString = dataset.classAttribute().value((int) pred);
            if (predString.equals("up")) {


                query = String.format("/user=21/currency=%s/trade",
                        URLEncoder.encode(param, charset));


                connection = new URL(baseURL + query).openConnection();
                connection.setRequestProperty("Accept-Charset", charset);

                response = connection.getInputStream();
            } else //predString.equals("down")
            {
//                query = String.format("/user=3/currency=%s/trade",
//                        URLEncoder.encode(param, charset));
//
//                URL url = new URL(baseURL + query);
//                URLConnection con = url.openConnection();
//                HttpURLConnection http = (HttpURLConnection) con;
//                http.setRequestMethod("POST"); // PUT is another valid option
//                http.setDoOutput(true);
//
//                response = http.getInputStream();
            }
            System.out.println(predString);

        }


    }
}
