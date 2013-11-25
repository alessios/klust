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

    public void testMD2() {
	MyDistanceMeasure2 md2 = new MyDistanceMeasure2();
	long l = 1287586574l;
	assertTrue(l == md2.rotateLeft(md2.rotateRight(l)));
	assertTrue(md2.rotateRight(1l) == 0x8000000000000000l);
	System.out.println(md2.rotateLeft(0x8000000000000000l));
	assertTrue(md2.rotateLeft(0x8000000000000000l) == 1l);
    }

    public void testKM() {
	MyDistanceMeasure measure = new MyDistanceMeasure();

	int sample_size = 128;
	Point[] points = new Point[sample_size];
	for (int i = 0; i < points.length; i++) {
	    points[i] = new Point(Math.round(Math.random()
		    * (Long.MAX_VALUE >> 2) + Math.random() * 16000));
	}

	for (int i = 8; i < 9; i++) {
	    System.out.println("Generating " + i + " clusters ");
	    KMeansPlusPlusClusterer<Point> kmpp = new KMeansPlusPlusClusterer<Point>(
		    i, 100000, measure);
	    kmpp.setCentroidComputer(new MyCentroidComputer());

	    List<CentroidCluster<Point>> clust = kmpp.cluster(Arrays
		    .asList(points));
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
		System.out.println();
	    }
	}
    }

    private String formatAsBinary(Point p) {

	return StringUtils.leftPad(Long.toBinaryString(p.getValue()), 64, '0');
    }
}
