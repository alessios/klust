package org.as.beehive.klust;

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;

/**
 * Unit test for simple App.
 */
public class KlustTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public KlustTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(KlustTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
	assertTrue(true);
    }

    public void testKM() {
	MyDistanceMeasure measure = new MyDistanceMeasure();
	KMeansPlusPlusClusterer<Point> kmpp = new KMeansPlusPlusClusterer<Point>(
		3, 100000, measure);

	Point[] points = new Point[] { new Point(255), new Point(1),
		new Point(5), new Point(16), new Point(17), new Point(25),
		new Point(64), new Point(67), new Point(122), new Point(1),
		new Point(5), new Point(156), new Point(17), new Point(25),
		new Point(64), new Point(67), new Point(22) };

	kmpp.setCentroidComputer(new MyCentroidComputer());

	List<CentroidCluster<Point>> clust = kmpp
		.cluster(Arrays.asList(points));
	for (CentroidCluster<Point> cc : clust) {
	    System.out.printf("Centr.\t %s\n",
		    formatAsBinary((Point) cc.getCenter()));

	    for (Point p : cc.getPoints()) {
		System.out.println("\t "
			+ formatAsBinary(p)
			+ " "
			+ measure.compute(cc.getCenter().getPoint(),
				p.getPoint()));
	    }
	    System.out.println();
	}
    }

    private String formatAsBinary(Point p) {

	return StringUtils.leftPad(Long.toBinaryString(p.getValue()), 64, '0');
    }
}
