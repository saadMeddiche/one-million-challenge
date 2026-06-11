package org.saadMeddiche.models;

public record TimerGeneratorResult(
        long time,
        TxtFileGeneratorResult result
) {}