package com.codegym.util;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public static BigDecimal percentage(BigDecimal base, BigDecimal percent) {
        return base.multiply(percent).scaleByPowerOfTen(-2);
    }
}
