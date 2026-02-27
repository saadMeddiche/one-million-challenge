package org.saadMeddiche.models;

import java.util.Optional;

public record TxtFileGeneratorResult(
        boolean success,
        Optional<String> failReason
) {}