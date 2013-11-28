package org.as.beehive.klust;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.util.FastMath;

public class OlisticDistanceMeasure2 implements DistanceMeasure {
    private static final long serialVersionUID = 1L;

    double exp = -1;

    public interface MultiplierSequence {
	double getMultiplier(int index);
    }

    protected class DefaultMultiplierSequence implements MultiplierSequence {

	double[] multipliers = new double[64];

	public DefaultMultiplierSequence() {
	    for (int i = 0; i < multipliers.length; i++) {
		multipliers[i] = FastMath.pow(i + 1, -3);
	    }
	}

	@Override
	public double getMultiplier(int index) {
	    return (index < multipliers.length) ? multipliers[index] : 0d;
	}
    }

    MultiplierSequence multiplierSequence;

    public OlisticDistanceMeasure2() {
	this.multiplierSequence = new DefaultMultiplierSequence();
    }

    public OlisticDistanceMeasure2(MultiplierSequence multiplierSequence) {
	this.multiplierSequence = multiplierSequence;
    }

    EuclideanDistance ed = new EuclideanDistance();

    @Override
    public double compute(double[] x, double[] y) {
	assert x.length == y.length;
	int n = x.length;
	double dist = ed.compute(x, y);
	double simil = 0d;
	for (int i = 0; i < n; i++) {
	    simil = 0d;
	    for (int j = 1; j <= n / 2; j++) {
		int jf = (i + j) % n;
		int jb = (i - j + n) % n;
		double m = multiplierSequence.getMultiplier(j);
		simil = simil + m * FastMath.abs(x[i] - y[jf]);
		if (jf != jb) {
		    simil = simil + m * FastMath.abs(x[i] - y[jb]);
		}
	    }
	    if (dist > simil / 24) {
		dist = simil / 24;
	    }
	}

	return dist;
    }
}
