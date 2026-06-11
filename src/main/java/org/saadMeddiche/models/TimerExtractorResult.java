package org.saadMeddiche.models;

public record TimerExtractorResult(
        long time,
        TxtFileExtractorResult result
) {}