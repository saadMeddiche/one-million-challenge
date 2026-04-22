package org.saadMeddiche.processes.extractors.impl;

import org.saadMeddiche.models.TxtFileExtractorResult;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class NativeByteArrayOperation extends SeekableByteChannelTxtFileExtractor {

    protected TxtFileExtractorResult mainProcesses(File file, String columnSeparator) {

        try (SeekableByteChannel ch = Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {

            ByteBuffer bf = ByteBuffer.allocate(ALLOCATION_SIZE);
            byte[] bytes = bf.array();

            long numbersSum = 0;

            int lineCount = 0;

            int numberOfDigits = 1;

            int numberThreshHold = 10;

            while(ch.read(bf) > 0) {

                bf.flip();

                int last_index = bf.limit() - 1;
                int position = bf.position();

                int completedLinesPosition = bf.limit();

                if(bytes[last_index] != '\n') {

                    int unCompleteLineLength = 0;

                    for(int i = last_index ; i >= 0 ; i--) {

                        if(bytes[i] =='\n') break;

                        unCompleteLineLength++;

                    }

                    completedLinesPosition = bf.limit() - unCompleteLineLength;

                }

                while(position < completedLinesPosition) {

                    if(lineCount == numberThreshHold) {
                        numberOfDigits++;
                        numberThreshHold *= 10;
                    }

                    int thirdColumnPosition = position + numberOfDigits + 36 + 2;

                    byte numberNature = numberNature(bytes[thirdColumnPosition]);

                    position = thirdColumnPosition + (numberNature & 0b0000_0001);

                    byte b = bytes[position++];

                    int number = 0;

                    do {
                        number = number * 10 + (b - '0');
                        b = bytes[position++];
                    }
                    while (b != '\r' && b != '\n');

                    if(b != '\n') position++;

                    number *= (numberNature & 0b0000_0010) == 0b0000_0010 ? 1 : -1;

                    numbersSum += number;

                    lineCount++;

                }

                bf.position(completedLinesPosition);
                bf.compact();


            }

            return new TxtFileExtractorResult(true, numbersSum, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}