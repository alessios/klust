package org.as.beehive.klust;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class Point implements Clusterable {

    double d[] = new double[1];
    long value;

    public Point(long p) {
	d[0] = p;
	value = p;
    }

    @Override
    public double[] getPoint() {
	return d;
    }

    public long getValue() {
	return value;
    }
}