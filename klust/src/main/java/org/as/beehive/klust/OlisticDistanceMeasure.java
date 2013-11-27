package org.as.beehive.klust;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.util.FastMath;

public class OlisticDistanceMeasure implements DistanceMeasure {
    private static final long serialVersionUID = 1L;

    double exp = -1;

    public OlisticDistanceMeasure() {

    }

    @Override
    public double compute(double[] x, double[] y) {
	assert x.length == y.length;
	int n = x.length;
	double simil = 0d;
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j <= n / 2; j++) {
		int jf = (i + j) % n;
		int jb = (i - j + n) % n;
		double m = FastMath.pow(j + 1, -2);
		simil = simil + m * FastMath.abs(x[i] - y[jf]);
		if (jf != jb) {
		    simil = simil + m * FastMath.abs(x[i] - y[jb]);
		}
	    }
	}

	return simil;
    }
}
