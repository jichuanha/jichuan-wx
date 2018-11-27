package com.hzkans.crm.common.utils;

import java.math.BigDecimal;

/**
 *
 * @author wsh
 * @date 2018/11/27
 */
public class PriceUtil {

        private static final int SCALE = 2;
        private static final Integer TRANSFORM_AMOUNT = Integer.valueOf(100);

        public PriceUtil() {
        }

        public static Double parseFen2Yuan(Long amount) {
            if(amount == null) {
                return null;
            } else {
                BigDecimal bd = new BigDecimal(amount.longValue());
                Double rtnAmount = Double.valueOf(bd.divide(new BigDecimal(TRANSFORM_AMOUNT.intValue()), 2, 4).doubleValue());
                return rtnAmount;
            }
        }

        public static String parseFen2YuanStr(Long amount) {
            if(amount == null) {
                return null;
            } else {
                BigDecimal bd = new BigDecimal(amount.longValue());
                String rtnAmount = bd.divide(new BigDecimal(TRANSFORM_AMOUNT.intValue()), 2, 4).toString();
                return rtnAmount;
            }
        }

        public static Long parseYuan2Fen(Double amount) {
            if(amount == null) {
                return null;
            } else {
                BigDecimal bd = new BigDecimal(amount.doubleValue());
                Long rtnAmount = Long.valueOf(bd.multiply(new BigDecimal(TRANSFORM_AMOUNT.intValue())).setScale(2, 4).longValue());
                return rtnAmount;
            }
        }
    }

