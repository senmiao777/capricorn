package com.frank.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AssignMessage {
    private Long userId;
    @NonNull
    private Long loanId;
    private Long reviewId;
    private String productName;
    private int creditClass;
}
