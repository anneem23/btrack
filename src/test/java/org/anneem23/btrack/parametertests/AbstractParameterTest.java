package org.anneem23.btrack.parametertests;


import org.anneem23.btrack.BeatTracker;
import org.anneem23.btrack.BeatTrackerIntegrationTest;
import org.anneem23.btrack.audio.AudioInputStreamProcessor;
import org.anneem23.btrack.audio.AudioSampleConverter;
import org.anneem23.btrack.audio.Shared;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

public class AbstractParameterTest {

    protected BeatTracker _beatTracker;
    protected int _beatnum;

    protected void whenTrackingBeatsOf(String fileName) throws IOException, UnsupportedAudioFileException {
        double[] buffer = new double[Shared.HOP_SIZE];	// buffer to hold one Shared.HOP_SIZE worth of audio samples

        double[] audioData = getData(fileName);
        // get number of audio frames, given the hop size and signal length
        double numframes = (int) Math.floor(((double) audioData.length) / ((double) Shared.HOP_SIZE));

        double[] beats = new double[5000];
        int beatnum = 0;

        ///////////////////////////////////////////
        //////// Begin Processing Loop ////////////

        for (int i=0;i < numframes;i++) {
            // add new samples to frame
            System.arraycopy(audioData, (i * Shared.HOP_SIZE), buffer, 0, Shared.HOP_SIZE);

            _beatTracker.processAudioFrame(buffer);
            if (_beatTracker.isBeatDueInFrame()) {
                beats[beatnum] = _beatTracker.getBeatTimeInSeconds(i,Shared.HOP_SIZE,44100);
                System.out.println (beatnum + ". beat at " + beats[beatnum] + " secs");
                beatnum = beatnum + 1;
            }
        }
        _beatnum = beatnum;
    }

    private double[] getData(String resourceName) throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = BeatTrackerIntegrationTest.class.getResourceAsStream("/" + resourceName);
        AudioInputStreamProcessor ais = new AudioInputStreamProcessor(inputStream);
        AudioSampleConverter audioSampleConverter = new AudioSampleConverter(ais.getFormat());
        return audioSampleConverter.convert(ais.readBytes());
    }
}
