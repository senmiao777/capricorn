package com.frank.model;

import lombok.Data;

@Data
public class AssignMessage {
    private Long userId;
    private Long loanId;
    private Long reviewId;
    private String productName;
    private int creditClass;
}
