package com.github.aursu.funcntions;

import org.testng.annotations.*;

import java.util.AbstractMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class MainTest {
    Main main;
    double [] xRange;
    double [] yRange;

    @DataProvider
    public static Object[][] dataProviderF1() {
        return new Object[][]{
                {0.7, 1.0},  // 175
                {1.4, -0.32974279}, // 350
                {3.0, 0.21424295}, // 750
                {1.0, 0.0},
                {4.0, -0.01969129},
        };
    }

    @DataProvider
    public static Object[][] dataProviderTabSteps() {
        return new Object[][]{
            {0.0, 3.0, 0.004, 751}, // from task
            {1.0, 2.0, 0.5, 3},
            {1.0, 2.0, 0.3, 4},
            {-1.0, 1.0, 0.4, 6},
            {1.0, -1.0, -0.4, 6}
        };
    }

    @DataProvider
    public static Object[][] dataProviderGetXRange() {
        return new Object[][]{
                {175, 0.7},
                {350, 1.4},
                {750, 3.0}
        };
    }

    @DataProvider
    public static Object[][] dataProviderTabF1() {
        return new Object[][]{
            {175, 1.0},
            {350, -0.32974279},
            {750, 0.21424295},
        };
    }

//    @DataProvider
//    public static Object[][] dataProviderTabF1() {
//        Map.Entry<Double, Double> [] table = new AbstractMap.SimpleEntry[] {
//                new AbstractMap.SimpleEntry<>(0.5, 1.0),
//                new AbstractMap.SimpleEntry<>(1.0, 0.0),
//                new AbstractMap.SimpleEntry<>(1.5, -0.46763934),
//                new AbstractMap.SimpleEntry<>(2.0, -0.24046205),
//        };
//        return new Object[][]{
//            {0.5, 2.0, 0.5, table}
//        };
//    }

    @BeforeClass
    public void setUp() {
        main = new Main();
    }

    @AfterClass
    public void tearDown() {
        main = null;
    }

    @BeforeMethod
    public void setUpRange() {
        xRange = main.getXRange(0.0,  3.0,  0.004);
        yRange  = main.tabF1(xRange);
    }

    @Test(dataProvider = "dataProviderF1")
    public void testF1(double x, double expect) {
        double actual = main.f1(x);
        assertEquals(actual, expect, 1e-8);
    }

    @Test(dataProvider = "dataProviderTabSteps")
    public void testTabSteps(double start, double end, double step, int expect) {
        int actual = main.tabSteps(start, end, step);
        assertEquals(actual, expect);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*less than end.*positive step.*")
    public void testTabStepsExceptionPos() {
        main.tabSteps(1.0, -1.0, 0.2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*greater than end.*negative step.*")
    public void testTabStepsExceptionNeg() {
        main.tabSteps(-1.0, 1.0, -0.2);
    }

    @Test(dataProvider = "dataProviderGetXRange")
    public void testGetXRange(int i, double expect) {
        double actual[] = xRange;
        assertEquals(actual[i], expect, 1e-8);
    }

    @Test(dataProvider = "dataProviderTabF1", groups = {"f1"})
    public void testTabF1(int i, double expect) {
        double actual[] = yRange;

        assertEquals(actual[i], expect, 1e-8);
    }

    @Test
    public void testMaxY() {
        int actual = main.maxY(yRange);

        assertEquals(actual, 0);
    }

    @Test
    public void testMinY() {
        int actual = main.minY(yRange);

        assertEquals(actual, 362);
    }

    @Test
    public void testSumY() {
        double actual = main.sumY(yRange);

        assertEquals(actual, 118.943654638, 1e-8);
    }

    @Test
    public void testAvgY() {
        double actual = main.avgY(yRange);

        assertEquals(actual, 0.15838037, 1e-8);
    }

//    @Test(dataProvider = "dataProviderTabF1")
//    public void testTabF1(double start, double end, double step, Map.Entry<Double, Double> [] expect) {
//        Map.Entry<Double, Double>[] actual = main.tabF1(start, end, step);
//        assertEquals(actual.length, expect.length, "Arrays length mismatch");
//        for (int i = 0; i < actual.length; i++) {
//            assertEquals(actual[i].getKey(), expect[i].getKey());
//            assertEquals(actual[i].getValue(), expect[i].getValue(),1e-8);
//        }
//    }

//    @Test(dataProvider = "dataProviderTabSteps")
//    public void testTabF1Size(double start, double end, double step, int expectLength)  {
//        Map.Entry<Double, Double>[] actual = main.tabF1(start, end, step);
//        assertEquals(actual.length, expectLength, "Arrays length mismatch");
//    }
}