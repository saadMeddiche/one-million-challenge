package org.saadMeddiche.utils;

import org.saadMeddiche.App;
import org.saadMeddiche.models.TimerExtractorResult;
import org.saadMeddiche.models.TimerGeneratorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;
import org.saadMeddiche.processes.generators.TxtFileGenerator;

public class Timer {

    public static TimerGeneratorResult stopwatch(TxtFileGenerator fileGenerator) {

        long start = System.nanoTime();

        var result = fileGenerator.generate(App.FILE_NAME_TO_WRITE_INTO , App.LINES_TO_GENERATE);

        long end = System.nanoTime();

        return new TimerGeneratorResult((end-start)/1_000_000, result);

    }

    public static TimerExtractorResult stopwatch(TxtFileExtractor fileExtractor) {

        long start = System.nanoTime();

        var result = fileExtractor.extract(App.FILE_NAME_TO_READ_FROM);

        long end = System.nanoTime();

        return new TimerExtractorResult((end-start)/1_000_000, result);

    }

}