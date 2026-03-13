package org.saadMeddiche.models;

import java.util.Optional;

public record TxtFileExtractorResult(
        boolean success,
        long totalSum,
        Optional<String> failReason
) {}