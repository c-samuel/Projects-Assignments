/**
    Student ID: 20194900
    Name: Chan Chi Hin
*/

import java.util.*;
import java.io.*;

class Student {
    public String studentID = "";
    public String studentName = "";
    public double[] asgMarks;
    public double overallMark;
    public int rank;

    public Student(int numberOfAsg){
            asgMarks = new double[numberOfAsg];
    }
}

public class StudentRecord {
    public final static int NAME_SPACE = 20;
    public final static int ID_SPACE = 8;
    public final static int MAX_RECORD = 50;
    public static int numberOfRecords;
    public static int numberOfAsg;

    public static void main(String[] args) throws FileNotFoundException {

        // TODO: can ask user for filename
        File f = new File("sample_input_1.txt");
        Scanner s = new Scanner(f).useDelimiter(",");

        String line = s.nextLine();
        String[] dataField = line.split(",");

        // length, exclude "ID" and "Name" / 2
        numberOfAsg = (dataField.length - 2) / 2;

        int[] asgProportion = new int[numberOfAsg];
        int dataCounter = 3;
        for (int i=0; i<numberOfAsg; i++, dataCounter += 2) {
                asgProportion[i] = Integer.parseInt(dataField[dataCounter]);
        }

        printFirstLine(dataField);


        Student[] studentList = new Student[MAX_RECORD];
        int counter = 0;

/* Input student records */
        // add ID, name, and assignment marks and calculate overall into Student
        while (s.hasNextLine() && counter <= MAX_RECORD) {
                studentList[counter] = new Student(numberOfAsg);

                line = s.nextLine();
                String[] marksField = line.split(",");

                studentList[counter].studentID = marksField[0];
                studentList[counter].studentName = marksField[1];
                double sumOfAsg = 0;
                for (int i=0; i<marksField.length-2; i++) {
                        studentList[counter].asgMarks[i] = Double.parseDouble(marksField[i+2]);
                        sumOfAsg += studentList[counter].asgMarks[i] * asgProportion[i] / 100;
                }
                studentList[counter].overallMark = sumOfAsg;
                counter++;
        }

        numberOfRecords = counter;

        // calculate students' rank
        calculateRank(studentList);


/* Output the records */

        // Print student record
        for (int i=0; i<counter; i++) {
                System.out.printf("%-9s%-21s", studentList[i].studentID, studentList[i].studentName);
                for (int j=0; j<numberOfAsg; j++)
                        System.out.printf("%-7.2f", studentList[i].asgMarks[j]);
                System.out.printf("%-8.2f", studentList[i].overallMark);
                System.out.printf("%-4d", studentList[i].rank);

                System.out.println();
        }
        // Print average
        System.out.printf("%30s", "Average: ");

        // calculate and print average of assignments
        double avg = 0;
        for (int i=0; i<numberOfAsg; i++) {
                avg = findAverage(i, studentList);
                System.out.printf("%-7.2f", avg);
        }
        // calculate and print average of overal
        avg = findAverage(studentList);
        System.out.printf("%-8.2f\n", avg);
    }

    /* Print the first row */
    public static void printFirstLine(String[] data){
        // Print first 2 fields
        System.out.printf("%-9s%-21s", "ID", "Name");

        for (int i=2; i<data.length; i += 2) {
                System.out.printf("%-7s", data[i]);
        }

        System.out.printf("%-8s%-4s\n", "Overall", "Rank");
    }

    /* calculate the rank of students according to the overall marks */
    public static void calculateRank(Student[] studentList){
        int rank = 1;
        for (int i=0; i<numberOfRecords; i++) {
                for (int j=0; j<numberOfRecords; j++) {
                        if (i == j) continue;
                        if (studentList[j].overallMark > studentList[i].overallMark) {
                                rank++;
                        }
                }
                studentList[i].rank = rank;
                rank = 1;
        }
    }

    /* find average marks of index-th assignments */
    public static double findAverage(int index, Student[] studentList){
        double sumOfAsg = studentList[0].asgMarks[index];
        for (int i=1; i<numberOfRecords; i++) {
                sumOfAsg += studentList[i].asgMarks[index];
        }
        return sumOfAsg / numberOfRecords;
    }

    /* find average of overall marks */
    public static double findAverage(Student[] studentList){
            double sumOfAsg = studentList[0].overallMark;
            for (int i=1; i<numberOfRecords; i++) {
                    sumOfAsg += studentList[i].overallMark;
            }
            return sumOfAsg / numberOfRecords;
    }


}
