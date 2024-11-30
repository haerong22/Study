package org.example.trxbatch.dto;

import java.math.BigInteger;

public record MonthlyTrxSummary(Long customerId, int trxCount, BigInteger trxAmountSum) {
}