package org.as.beehive.klust;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.distance.CanberraDistance;
import org.apache.commons.math3.ml.distance.ChebyshevDistance;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EarthMoversDistance;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

/**
 * Unit test for simple App.
 */
public class KlustTest3 extends TestCase {

    Map<String, DistanceMeasure> measures = new HashMap<String, DistanceMeasure>();

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public KlustTest3(String testName) {
	super(testName);
	measures.put("EUC", new EuclideanDistance());
	measures.put("EMD", new EarthMoversDistance());
	measures.put("LDM", new DLDistanceMeasure());
	measures.put("OLI", new OlisticDistanceMeasure());
	measures.put("CHE", new ChebyshevDistance());
	measures.put("CAN", new CanberraDistance());
	measures.put("MAN", new ManhattanDistance());
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

    public void testDistances() {
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumFractionDigits(2);
	nf.setMaximumFractionDigits(2);

	DPoint[] points = getDataset2();
	String[] measuresKeys = measures.keySet().toArray(new String[] {});

	for (int i = 0; i < points.length; i++) {
	    System.out.println();
	    System.out.print("src\t'" + format(points[i].getPoint()) + "'");
	    for (String measure : measuresKeys) {
		System.out.print("\t" + measure);
	    }
	    System.out.println();

	    for (int j = 0; j < points.length; j++) {

		System.out.print("\t'" + format(points[j].getPoint()) + "'");
		for (String measure : measuresKeys) {
		    System.out
			    .print("\t"
				    + nf.format(measures.get(measure).compute(
					    points[i].getPoint(),
					    points[j].getPoint())));
		}
		System.out.println();
	    }
	}
    }

    private void dumpCluster(List<CentroidCluster<DPoint>> clust) {
	for (CentroidCluster<DPoint> cc : clust) {

	    System.out.println("Centr. " + format(cc.getCenter().getPoint())
		    + " " + cc.getPoints().size());

	    // for (DPoint p : cc.getPoints()) {
	    // System.out.println("\t " + format(p));
	    // }
	    // System.out.println();
	    // System.out.println();
	}
    }

    public static String format(double[] points) {
	StringBuilder sb = new StringBuilder();
	for (double d : points) {
	    sb.append(((d > 0.5d) ? '1' : '0'));
	}
	return sb.toString();
    }

    private String formatAsBinary(Point p) {

	return StringUtils.leftPad(Long.toBinaryString(p.getValue()), 24, '0');
    }
}
