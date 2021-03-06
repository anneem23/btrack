package org.anneem23.btrack.beat;

/**
 * Beat Prediction Algorithm
 * <p>
 *
 * @author anneem23
 */
public interface BeatPredictionAlgorithm {
    void processSample(double odfSample, float beatPeriod);

    boolean beatDetected();
}
