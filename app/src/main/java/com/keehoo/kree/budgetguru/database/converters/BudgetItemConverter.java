package com.keehoo.kree.budgetguru.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.math.BigDecimal;

/**
 * Created by krzysztof on 03.12.2017.
 */

public class BudgetItemConverter {

    @TypeConverter
    public static BigDecimal fromString(String value) {
        return value == null ? null : new BigDecimal(value);
    }

    @TypeConverter
    public static String toString(BigDecimal value) {
        return value == null ? null : String.valueOf(value.doubleValue());
    }
}
