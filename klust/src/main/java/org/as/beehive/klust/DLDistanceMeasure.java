package org.as.beehive.klust;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

import blogspot.software_and_algorithms.stern_library.string.DamerauLevenshteinAlgorithm;

public class DLDistanceMeasure implements DistanceMeasure {
    DamerauLevenshteinAlgorithm dl;

    public DLDistanceMeasure() {
	dl = new DamerauLevenshteinAlgorithm(1, 1, 1, 1);
    }

    private static final String format(double[] points) {
	StringBuilder sb = new StringBuilder();
	for (double d : points) {
	    sb.append(((d > 0.5d) ? '1' : '0'));
	}
	return sb.toString();
    }

    @Override
    public double compute(double[] arg0, double[] arg1) {
	return dl.execute(format(arg0), format(arg1));
    }
}
