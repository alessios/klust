package org.as.beehive.klust;

import java.util.Collection;

import org.apache.commons.math3.ml.clustering.Clusterable;

public interface CentroidComputer<T> {
    Clusterable centroidOf(final Collection<T> points, final int dimension);
}
