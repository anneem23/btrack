package org.anneem23.btrack.audio;

/**
 * Implementors of AudioSampleListener can register to receive
 * audio sample updates from {@link AudioDispatcher}
 * <p>
 *
 * @author anneem23
 */
public interface AudioSampleListener {

    void updateSamples(double[] audioSamples, long time);

}
