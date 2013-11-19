package org.as.beehive.klust;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

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

    public class Point implements Clusterable {

	double d[] = new double[1];

	public Point(long p) {
	    d[0] = p;
	}

	@Override
	public double[] getPoint() {
	    return d;
	}
    }

    public void testKM() {
	KMeansPlusPlusClusterer<Point> kmpp = new KMeansPlusPlusClusterer<Point>(
		3, 10000, new DistanceMeasure() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public double compute(double[] arg0, double[] arg1) {
			long l1 = (long) arg0[0];
			long l2 = (long) arg1[0];
			return Long.bitCount(l1 ^ l2);
		    }
		});
	Point[] points = new Point[] { new Point(1), new Point(5),
		new Point(16), new Point(17), new Point(25), new Point(64),
		new Point(67), new Point(22) };

	kmpp.setCentroidComputer(new CentroidComputer<KlustTest.Point>() {

	    @Override
	    public Clusterable centroidOf(Collection<Point> points,
		    int dimension) {
		final double[] centroid = new double[48];
		for (final Point p : points) {
		    final double[] point = p.getPoint();
		    long l = (long) point[0];
		    int i = 0;
		    while (l > 0) {
			if ((l & 1L) > 0) {
			    centroid[i] += 1;
			}
			i++;
			l = l >> 1;
		    }
		}

		long c = 0;
		for (int i = 0; i < centroid.length; i++) {
		    if (centroid[i] / points.size() > 0.5) {
			c = c | (1L << i);
		    }
		}
		return new Point(c);
	    }

	});

	List<CentroidCluster<Point>> clust = kmpp
		.cluster(Arrays.asList(points));
	for (CentroidCluster<Point> cc : clust) {
	    System.out.printf("Centr.\t %s\n", formatAsBinary(cc.getCenter()));

	    for (Point p : cc.getPoints()) {
		System.out.printf("\t %s\n", formatAsBinary(p));
	    }
	    System.out.println();
	}
    }

    private String formatAsBinary(Clusterable p) {

	return StringUtils.leftPad(
		Long.toBinaryString(((long) p.getPoint()[0])), 64, '0');
    }
}
