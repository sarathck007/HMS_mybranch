package com.group3.hotelsystem.util;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class PriceUtil {
	private static final int DECIMAL_PLACES = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public static BigDecimal calculateTotalPrice(BigDecimal pricePerNight, long numberOfNights) {
        return pricePerNight
                .multiply(BigDecimal.valueOf(numberOfNights))
                .setScale(DECIMAL_PLACES, ROUNDING_MODE);
    }

    public static BigDecimal applyDiscount(BigDecimal price, BigDecimal discountPercentage) {
        BigDecimal discountFactor = BigDecimal.ONE.subtract(
            discountPercentage.divide(BigDecimal.valueOf(100), 4, ROUNDING_MODE)
        );
        return price.multiply(discountFactor).setScale(DECIMAL_PLACES, ROUNDING_MODE);
    }

    public static BigDecimal calculateTax(BigDecimal amount, BigDecimal taxRate) {
        return amount
                .multiply(taxRate)
                .divide(BigDecimal.valueOf(100), DECIMAL_PLACES, ROUNDING_MODE);
    }
}
