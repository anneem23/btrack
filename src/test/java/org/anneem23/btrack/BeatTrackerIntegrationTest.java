package org.anneem23.btrack;

import org.anneem23.btrack.audio.Shared;
import org.anneem23.btrack.onset.ComplexSpectralDifference;
import org.anneem23.btrack.parametertests.AbstractParameterTest;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;


public class BeatTrackerIntegrationTest extends AbstractParameterTest {

    @Test
    public void test120BeatsTrackedFor1Min120BpmTrack() throws IOException, UnsupportedAudioFileException {
        givenBTrackWithComplexSpectralDifference();
        whenTrackingBeatsOf("120bpm.wav");
        thenNumberOfBeatsDetectedIs(120);
    }


    @Test
    public void test60BeatsTrackedFor30SecsMicRecordingOf120BpmTrack() throws IOException, UnsupportedAudioFileException {
        givenBTrackWithComplexSpectralDifference();
        whenTrackingBeatsOf("recording.wav");
        thenNumberOfBeatsDetectedIs(62);
    }

    @Test
    public void test140BeatsTrackedFor1Min140BpmTrack() throws IOException, UnsupportedAudioFileException {
        givenBTrackWithComplexSpectralDifference();
        whenTrackingBeatsOf("140bpm.wav");
        thenNumberOfBeatsDetectedIs(140);
    }

    private void givenBTrackWithComplexSpectralDifference() throws IOException {
        ComplexSpectralDifference csd = new ComplexSpectralDifference(Shared.FRAME_SIZE, Shared.HOP_SIZE);
        _beatTracker = new BeatTracker(Shared.HOP_SIZE, csd, Shared.SAMPLE_RATE);
    }

    private void thenNumberOfBeatsDetectedIs(int beats) {
        assertThat((double) _beatnum, is(closeTo(beats,3)));
    }


}