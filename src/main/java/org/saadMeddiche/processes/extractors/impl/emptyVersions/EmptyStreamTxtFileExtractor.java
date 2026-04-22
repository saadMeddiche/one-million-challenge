package org.saadMeddiche.processes.extractors.impl.emptyVersions;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;

public class EmptyStreamTxtFileExtractor extends TxtFileExtractor {

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try(Stream<String> lines = Files.lines(file.toPath())) {

            lines.forEach(_ -> {

            });

            return new TxtFileExtractorResult(true, 0, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}