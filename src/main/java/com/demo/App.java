package com.demo;

import java.util.Scanner;

public class App {

    public static int getGradePoint(String grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be empty.");
        }

        switch (grade.trim().toUpperCase()) {
            case "S": return 10;
            case "A": return 9;
            case "B": return 8;
            case "C": return 7;
            case "D": return 6;
            case "E": return 5;
            case "F": return 0;
            default:
                throw new IllegalArgumentException(
                    "Invalid grade! Enter S, A, B, C, D, E, or F.");
        }
    }

    public static double calculateCGPA(String[] grades, int[] credits) {
        if (grades == null || credits == null || grades.length == 0
                || grades.length != credits.length) {
            throw new IllegalArgumentException(
                "Grades and credits must have the same non-zero length.");
        }

        int totalCredits = 0;
        int totalWeightedPoints = 0;

        for (int i = 0; i < grades.length; i++) {
            if (credits[i] != 2 && credits[i] != 3 && credits[i] != 4) {
                throw new IllegalArgumentException("Credits must be 2, 3, or 4.");
            }

            totalWeightedPoints += getGradePoint(grades[i]) * credits[i];
            totalCredits += credits[i];
        }

        return (double) totalWeightedPoints / totalCredits;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("==================================");
            System.out.println("       STUDENT CGPA CALCULATOR");
            System.out.println("==================================");
            System.out.print("Enter number of subjects: ");

            if (!sc.hasNextInt()) {
                System.out.println("Error: Enter a whole number of subjects.");
                return;
            }
            int n = sc.nextInt();
            if (n <= 0) {
                System.out.println("Error: Number of subjects must be positive.");
                return;
            }

            String[] grades = new String[n];
            int[] credits = new int[n];

            for (int i = 0; i < n; i++) {
                System.out.println("\nSubject " + (i + 1));
                System.out.print("Enter grade (S/A/B/C/D/E/F): ");
                grades[i] = sc.next();

                System.out.print("Enter credits (2/3/4): ");
                if (!sc.hasNextInt()) {
                    System.out.println("Error: Credits must be a whole number.");
                    return;
                }
                credits[i] = sc.nextInt();
            }

            try {
                double cgpa = calculateCGPA(grades, credits);
                System.out.println("\n========== RESULT ==========");
                System.out.printf("Your CGPA is: %.2f%n", cgpa);

                boolean failed = false;
                for (String grade : grades) {
                    if ("F".equalsIgnoreCase(grade.trim())) {
                        failed = true;
                        break;
                    }
                }
                System.out.println(failed
                    ? "Status: Failed subject present"
                    : "Status: No failed subjects");
                System.out.println("============================");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
