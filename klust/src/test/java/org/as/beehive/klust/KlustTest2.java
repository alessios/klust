package org.as.beehive.klust;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
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
public class KlustTest2 extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public KlustTest2(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(KlustTest2.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
	assertTrue(true);
    }

    protected List<String> loadData() {
	try {
	    LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
		    KlustTest2.class.getResourceAsStream("sample.txt")));
	    String line;
	    List<String> ret = new ArrayList<String>();
	    while ((line = lnr.readLine()) != null) {
		ret.add(line.substring(0, 24));
	    }
	    return ret;
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

    public Point[] getDataset() {
	List<String> data = loadData();
	Point[] points = new Point[data.size()];
	for (int i = 0; i < points.length; i++) {
	    points[i] = new Point(Long.parseLong(data.get(i), 2));
	}
	return points;
    }

    public DPoint[] getDataset2() {
	List<String> data = loadData();
	DPoint[] points = new DPoint[data.size()];
	for (int i = 0; i < points.length; i++) {
	    points[i] = new DPoint(data.get(i));
	}
	return points;
    }

    public void testKMPP() {
	int nclusters = 10;
	DPoint[] points = getDataset2();

	System.out.println("Generating " + nclusters + " clusters ");
	KMeansPlusPlusClusterer<DPoint> kmpp = new KMeansPlusPlusClusterer<DPoint>(
		nclusters, 100000);

	List<CentroidCluster<DPoint>> clust = kmpp.cluster(Arrays
		.asList(points));
	dumpCluster(clust);

    }

    public void _testKM() {
	MyDistanceMeasure measure = new MyDistanceMeasure();
	MyDistanceMeasure2 measure2 = new MyDistanceMeasure2();
	int nclusters = 10;
	Point[] points = getDataset();

	System.out.println("Generating " + nclusters + " clusters ");
	KMeansPlusPlusClusterer<Point> kmpp = new KMeansPlusPlusClusterer<Point>(
		nclusters, 1000, measure);
	kmpp.setCentroidComputer(new MyCentroidComputer());

	KMeansPlusPlusClusterer<Point> kmpp2 = new KMeansPlusPlusClusterer<Point>(
		nclusters, 1000, measure2);
	kmpp.setCentroidComputer(new MyCentroidComputer());

	List<CentroidCluster<Point>> clust = kmpp
		.cluster(Arrays.asList(points));
	dumpCluster(measure, clust);

	System.out
		.println("------------------------------------------------------");

	List<CentroidCluster<Point>> clust2 = kmpp2.cluster(Arrays
		.asList(points));
	dumpCluster(measure2, clust2);
    }

    private void dumpCluster(DistanceMeasure measure,
	    List<CentroidCluster<Point>> clust) {
	for (CentroidCluster<Point> cc : clust) {
	    if (cc.getCenter() instanceof Point) {
		System.out.printf("Centr.\t %s\n",
			formatAsBinary((Point) cc.getCenter()));
	    } else {
		System.out.println("Centr. " + cc.getCenter().getPoint());
	    }
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

    private void dumpCluster(List<CentroidCluster<DPoint>> clust) {
	for (CentroidCluster<DPoint> cc : clust) {

	    System.out.println("Centr. " + format(cc.getCenter()));

	    for (DPoint p : cc.getPoints()) {
		System.out.println("\t " + format(p));
	    }
	    System.out.println();
	    System.out.println();
	}
    }

    private String format(Clusterable c) {
	StringBuilder sb = new StringBuilder();
	for (double d : c.getPoint()) {
	    sb.append(((d > 0.5d) ? '1' : '0'));
	}
	return sb.toString();
    }

    private String formatAsBinary(Point p) {

	return StringUtils.leftPad(Long.toBinaryString(p.getValue()), 24, '0');
    }
}
