package org.saadMeddiche.processes.extractors.impl;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class StreamTxtFileExtractor extends TxtFileExtractor {

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try(Stream<String> lines = Files.lines(file.toPath())) {

            AtomicLong sum = new AtomicLong();

            lines.forEach(line -> {

                String[] fields = line.split(columnSeparator);

                String lastField = fields[fields.length - 1];

                int number = Integer.parseInt(lastField);

                sum.addAndGet(number);

            });

            return new TxtFileExtractorResult(true, sum.get(), Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}