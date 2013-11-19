package org.as.beehive.klust;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class MyDistanceMeasure implements DistanceMeasure {
    private static final long serialVersionUID = 1L;

    @Override
    public double compute(double[] arg0, double[] arg1) {
	long l1 = (long) arg0[0];
	long l2 = (long) arg1[0];
	return Long.bitCount(l1 ^ l2);
    }
}
