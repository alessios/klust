package org.as.beehive.klust;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy;
import org.apache.commons.math3.ml.distance.EarthMoversDistance;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.random.JDKRandomGenerator;

/**
 * Unit test for simple App.
 */
public class KlustTest3 extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public KlustTest3(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(KlustTest3.class);
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
		    KlustTest3.class.getResourceAsStream("sample2.txt")));
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

    public void _testKMPP() {
	int nclusters = 15;
	DPoint[] points = getDataset2();

	// System.out.println("Generating " + nclusters + " clusters ");
	// KMeansPlusPlusClusterer<DPoint> kmpp = new
	// KMeansPlusPlusClusterer<DPoint>(
	// nclusters, 1000000, new OlisticDistanceMeasure(),
	// new JDKRandomGenerator(), EmptyClusterStrategy.LARGEST_VARIANCE);

	KMeansPlusPlusClusterer<DPoint> kmpp = new KMeansPlusPlusClusterer<DPoint>(
		nclusters, 1000000, new OlisticDistanceMeasure(),
		new JDKRandomGenerator(), EmptyClusterStrategy.LARGEST_VARIANCE);

	List<CentroidCluster<DPoint>> clust = kmpp.cluster(Arrays
		.asList(points));
	dumpCluster(clust);

    }

    public void testKMPP2() {
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumFractionDigits(2);
	nf.setMaximumFractionDigits(2);
	int nclusters = 15;
	DPoint[] points = getDataset2();
	OlisticDistanceMeasure od = new OlisticDistanceMeasure();
	OlisticDistanceMeasure2 od2 = new OlisticDistanceMeasure2(
		new OlisticDistanceMeasure2.MultiplierSequence() {

		    @Override
		    public double getMultiplier(int index) {
			// TODO Auto-generated method stub
			return Math.pow(index, -2);
		    }
		});
	EuclideanDistance ed = new EuclideanDistance();
	EarthMoversDistance emd = new EarthMoversDistance();
	for (int i = 0; i < points.length; i++) {
	    System.out.println();
	    System.out.println("\t" + format(points[i]));
	    for (int j = 0; j < points.length; j++) {

		System.out.println("\t"
			+ format(points[j])
			+ "\t"
			+ nf.format(ed.compute(points[i].getPoint(),
				points[j].getPoint()))
			+ "\t"
			+ nf.format(emd.compute(points[i].getPoint(),
				points[j].getPoint()))
			+ "\t"
			+ nf.format(od.compute(points[i].getPoint(),
				points[j].getPoint()))
			+ "\t"
			+ nf.format(od2.compute(points[i].getPoint(),
				points[j].getPoint())));

	    }
	}
    }

    private void dumpCluster(List<CentroidCluster<DPoint>> clust) {
	for (CentroidCluster<DPoint> cc : clust) {

	    System.out.println("Centr. " + format(cc.getCenter()) + " "
		    + cc.getPoints().size());

	    // for (DPoint p : cc.getPoints()) {
	    // System.out.println("\t " + format(p));
	    // }
	    // System.out.println();
	    // System.out.println();
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
