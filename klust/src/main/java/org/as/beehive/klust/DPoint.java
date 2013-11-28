package org.as.beehive.klust;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class DPoint implements Clusterable {

    String d;
    double[] point;

    public DPoint(String d) {
	this.d = d;
	int l = d.length();
	this.point = new double[l];
	for (int i = 0; i < l; i++) {
	    this.point[i] = (d.charAt(i) == '0') ? 0d : 1d;
	}
    }

    @Override
    public double[] getPoint() {
	// TODO Auto-generated method stub
	return point;
    }
}
