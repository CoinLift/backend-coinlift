package com.coinlift.backend.exceptions;

import java.util.Date;

public record ErrorDetails(
        Date timestamp,

        String message,

        String[] details
) {
}
