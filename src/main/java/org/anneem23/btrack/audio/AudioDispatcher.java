package org.anneem23.btrack.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AudioDispatcher feeds listeners of type {@link AudioSampleListener} with audio samples.
 * <p>
 *
 * @author anneem23
 */
public class AudioDispatcher implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioDispatcher.class);

    private final CopyOnWriteArrayList<AudioSampleListener> listeners = new CopyOnWriteArrayList<>();
    private final AudioProcessor audioProcessor;
    private long startTime;

    public AudioDispatcher(AudioProcessor audioProcessor) {
        this.audioProcessor = audioProcessor;
    }


    public void register(AudioSampleListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {
        final AudioSampleConverter sampleConverter = new AudioSampleConverter(audioProcessor.getFormat());

        try {
            startTime = System.currentTimeMillis();
            audioProcessor.openStream();
            while (audioProcessor.bytesAvailable()) {
                byte[] audioData = audioProcessor.readBytes(Shared.FRAME_SIZE);

                dispatch(sampleConverter.convert(audioData));
            }
        } catch (Exception e) {
            LOGGER.error("Failed to read audio stream.", e);
        }
    }

    private void dispatch(double[] audioSamples) {
        for (AudioSampleListener listener : listeners)
            listener.updateSamples(audioSamples, (System.currentTimeMillis() - startTime));
    }
}
