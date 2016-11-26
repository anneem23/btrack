package org.anneem23.btrack.parametertests;


import org.anneem23.btrack.BeatTracker;
import org.anneem23.btrack.audio.Shared;
import org.anneem23.btrack.onset.ComplexSpectralDifference;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class SampleRateParameterTest extends AbstractParameterTest {


    @Test
    public void testWithNormalSampleRate() throws IOException, UnsupportedAudioFileException {
        givenBTrackWithComplexSpectralDifference();
        whenTrackingBeatsOf("recording.wav");
        // 30 secs track , so no of beats is half of bpm
        thenNumberOfBeatsDetectedIs(60);

    }


    private void givenBTrackWithComplexSpectralDifference() throws IOException {
        ComplexSpectralDifference csd = new ComplexSpectralDifference(Shared.FRAME_SIZE, Shared.HOP_SIZE);
        _beatTracker = new BeatTracker(Shared.HOP_SIZE, csd, Shared.SAMPLE_RATE);
    }

    private void thenNumberOfBeatsDetectedIs(int beats) {
        assertThat((double) _beatnum, is(closeTo(beats, 3)));
    }
}
