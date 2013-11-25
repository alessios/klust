package org.as.beehive.klust;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class MyDistanceMeasure2 implements DistanceMeasure {
    private static final long serialVersionUID = 1L;

    private static MyDistanceMeasure myDistanceMeasure = new MyDistanceMeasure();

    public long rotateLeft(long l) {
	// long lset = 0xFFFFFFFFFFFFFFFFl;
	long lmask = 0x8000000000000000l;
	return l << 1 | ((l & lmask) != 0 ? 1 : 0);
    }

    public long rotateRight(long l) {
	// long lset = 0xFFFFFFFFFFFFFFFFl;
	long lmask = 0x8000000000000000l;
	return l >> 1 | ((l & 1) > 0 ? lmask : 0);
    }

    public double simile(long l1, long l2) {
	return Long.bitCount(l1 & l2);
    }

    @Override
    public double compute(double[] arg0, double[] arg1) {
	long l1 = (long) arg0[0];
	long l2 = (long) arg1[0];

	return Long.bitCount(l1 ^ l2) - 0.1
		* (simile(rotateLeft(l1), l2) + simile(rotateRight(l1), l2));
    }
}
