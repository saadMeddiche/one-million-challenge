package org.saadMeddiche.processes.extractors.impl.emptyVersions;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

public class EmptyBufferReaderTxtFileExtractor extends TxtFileExtractor {

    private final static int ALLOCATION_SIZE = 5_000_000;

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

            CharBuffer cf = CharBuffer.allocate(ALLOCATION_SIZE);

            while(br.read(cf) > 0) {
                cf.flip();
                cf.clear();
            }

            return new TxtFileExtractorResult(true , 0,  Optional.empty());

        }
        catch (IOException e) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}
