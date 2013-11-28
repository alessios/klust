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

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EarthMoversDistance;

/**
 * Unit test for simple App.
 */
public class KlustTest2 extends TestCase {

    NumberFormat nf;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public KlustTest2(String testName) {
	super(testName);
	nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(KlustTest2.class);
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
		nclusters, 100000, new EarthMoversDistance());

	List<CentroidCluster<DPoint>> clust = kmpp.cluster(Arrays
		.asList(points));
	dumpCluster(clust, false, true);

    }

    private void dumpCluster(List<CentroidCluster<DPoint>> clust,
	    boolean printDetails, boolean printHistogram) {

	double[] elems = new double[clust.size()];
	long tot = 0;
	for (int i = 0; i < clust.size(); i++) {
	    elems[i] = clust.get(i).getPoints().size();
	    tot += elems[i];
	}
	for (int i = 0; i < clust.size(); i++) {
	    elems[i] = elems[i] / tot;
	}

	freqHist(elems, 0.05);
	System.out.println();

	for (CentroidCluster<DPoint> cc : clust) {

	    System.out.println("C: " + format(cc.getCenter()) + " #elems:"
		    + cc.getPoints().size());
	    if (printHistogram) {
		System.out.println();

		freqHist(cc.getCenter().getPoint(), 0.10);
	    }
	    if (printDetails) {
		System.out.println();

		for (DPoint p : cc.getPoints()) {
		    System.out.println("   " + format(p));
		}
	    }
	    System.out.println();
	    System.out.println();
	}
    }

    private void freqHist(double[] points, double scale) {
	for (long i = Math.round(Math.ceil(1 / scale)) - 1; i >= 0; i--) {
	    StringBuilder sb = new StringBuilder("   ");
	    for (double d : points) {
		if (d > scale * i) {
		    sb.append(" *  |");
		} else {
		    sb.append("    |");
		}
	    }
	    System.out.println(sb.toString());
	}

    }

    private String format(Clusterable c) {
	StringBuilder sb = new StringBuilder();
	for (double d : c.getPoint()) {
	    if (d == 0d) {
		sb.append(" 0  |");
	    } else if (d == 1.0d) {
		sb.append(" 1  |");
	    } else {
		sb.append(nf.format(d));
		sb.append('|');
	    }
	}
	return sb.toString();
    }

}
