package org.as.beehive.klust;

import java.util.Collection;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class MyCentroidComputer implements CentroidComputer<Point> {

    @Override
    public Clusterable centroidOf(Collection<Point> points, int dimension) {
	final double[] centroid = new double[64];
	for (final Point p : points) {
	    long l = p.getValue();
	    int i = 0;
	    while (l > 0) {
		if ((l & 1L) > 0) {
		    centroid[i] += 1;
		}
		i++;
		l = l >> 1;
	    }
	}

	long c = 0;
	for (int i = 0; i < centroid.length; i++) {
	    if (centroid[i] / points.size() > 0.5) {
		c = c | (1L << i);
	    }
	}
	return new Point(c);
    }

}
