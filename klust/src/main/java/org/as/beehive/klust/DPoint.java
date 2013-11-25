package org.as.beehive.klust;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class DPoint implements Clusterable {

    String d;
    double[] point;

    public DPoint(String d) {
	this.d = d;
	this.point = new double[24];
	for (int i = 0; i < 24; i++) {
	    this.point[i] = (d.charAt(i) == '0') ? 0d : 1d;
	}
    }

    @Override
    public double[] getPoint() {
	// TODO Auto-generated method stub
	return point;
    }
}
