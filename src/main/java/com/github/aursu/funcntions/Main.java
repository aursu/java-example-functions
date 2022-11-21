package com.github.aursu.funcntions;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final double DELTA = 1e-15;
    public static final double[] argA = {
        0,
        -0.5, // f1
        1.5,  // f2
        2.7,  // f3
        1.65, // f4
        2.3   // f5
    };

    public static final double[] argB = {
        0,
        2.0,  // f1
        0,    // f2
        -0.3, // f3
        0,    // f4
        0     // f5
    };

    public static final double[] argC = {
        0,
        0,   // f1
        0,   // f2
        4.0, // f3
        0,   // f4
        0    // f5
    };

    public double f1(double x) {
        double a = argA[1], b = argB[1];

        if (x <= 0.7 + DELTA) return 1.0;
        if (x > 1.4 + DELTA) return Math.pow(Math.E, a * x) * Math.cos(b * x);
        return a * x * x * Math.log(x);
    }

    public double f2(double x) {
        double a = argA[2];

        if (x < 1.7) return Math.PI * x * x  - 7.0 / (x * x);
        if (x > 1.7) return Math.log10(x + 7.0 * Math.sqrt(x));
        return a * x * x * x + 7.0 * Math.sqrt(x);
    }

    public double f3(double x) {
        double a = argA[3], b = argB[3], c = argC[3];

        if (x < 1.4) return a * x * x  + b * x + c;
        if (x > 1.4) return (a + b * x) / Math.sqrt(x * x + 1);
        return a / x + Math.sqrt(x * x + 1);
    }

    public double f4(double x) {
        double a = argA[4];

        if (x < 1.3) return Math.PI * x * x  - 7.0 / (x * x);
        if (x > 1.3) return Math.log(x + 7.0 * Math.sqrt(Math.abs(x + a)));
        return a * x * x * x + 7.0 * Math.sqrt(x);
    }

    public double f5(double x) {
        double a = argA[5];

        if (x <= 0.3) return 1.5 * a * Math.pow(Math.cos(x), 2);
        if (x > 2.3) return 3.0 * a * Math.tan(x);
        return Math.pow(x - 2.0, 2) + 6.0 * a;
    }

    public int tabSteps(double start, double end, double step) throws IllegalArgumentException {
        if (start > end && step > 0) throw new IllegalArgumentException("Start position must be less than end with positive step.");
        if (start < end && step < 0) throw new IllegalArgumentException("Start position must be greater than end with negative step.");
        Double steps = (end - start) / step;
        return steps.intValue() + 1; // plus start point itself
    }

//    public Map.Entry<Double, Double>[] tabF1(double start, double end, double step) {
//        int steps = tabSteps(start, end, step);
//        Map.Entry<Double, Double> [] table = new AbstractMap.SimpleEntry[steps];
//        // tabulate it
//        for(int i = 0; i < steps; i++) {
//            double x = start + step * i, y = f1(x);
//            table[i] = new AbstractMap.SimpleEntry<Double, Double>(x, y);
//        }
//        return table;
//    }

    public double[] getXRange(double start, double end, double step) {
        int steps = tabSteps(start, end, step);
        double xRange[] = new double[steps];

        for (int i = 0; i < steps; i++) {
            xRange[i] = start + step * i;
        }

        return xRange;
    }

//    public Double[] tabF1(double start, double end, double step) {
//        List<Double> yRange = new ArrayList<>();
//
//        for (double x: getXRange(start, end, step)) {
//            yRange.add(f1(x));
//        }
//
//        return yRange.toArray(new Double[0]);
//    }

    public double[] tabF1(double xRange[]) {
        double yRange[] = new double[xRange.length];

        for (int i = 0; i < xRange.length; i++) {

            yRange[i] = f1(xRange[i]);
        }

        return yRange;
    }

    public int maxY(double yRange[]) {
        int maxI = 0;
        for (int i = 1; i < yRange.length; i++)
            if (yRange[i] > yRange[maxI]) maxI = i;

        return maxI;
    }

    public int minY(double yRange[]) {
        int minI = 0;
        for (int i = 1; i < yRange.length; i++)
            if (yRange[i] < yRange[minI]) minI = i;

        return minI;
    }

    public double sumY(double yRange[]) {
        double sum = 0;
        for (int i = 0; i < yRange.length; i++)
            sum += yRange[i];

        return sum;
    }

    public double avgY(double yRange[]) {
        return sumY(yRange) / yRange.length;
    }

    public void printStatsF1(double start, double end, double step) {
        double xRange[] = getXRange(start,  end, step),
                yRange[] = tabF1(xRange);
        int maxI = maxY(yRange), minI = minY(yRange);

        System.out.printf("Maximum value: i: %d; x: %f; y: %f\n", maxI, xRange[maxI], yRange[maxI]);
        System.out.printf("Minimal value: i: %d; x: %f; y: %f\n", minI, xRange[minI], yRange[minI]);
    }

    private void run() {
        printStatsF1(0.0,  3.0,  0.004);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}