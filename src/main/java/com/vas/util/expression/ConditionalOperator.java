package com.vas.util.expression;

import de.congrace.exp4j.CustomOperator;
import org.apache.log4j.Logger;

public class ConditionalOperator {

    private static Logger logger = Logger.getLogger(ConditionalOperator.class);

    private static final CustomOperator LTOperator;
    private static final CustomOperator LEOperator;
    private static final CustomOperator GTOperator;
    private static final CustomOperator GEOperator;
    private static final CustomOperator EQOperator;

    static {

        LTOperator = new CustomOperator("<", true, 0, 2) {
            protected double applyOperation(double[] values) {
                if (values[0] < values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        LEOperator = new CustomOperator("<=", true, 0, 2) {
            protected double applyOperation(double[] values) {
                if (values[0] <= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        GEOperator = new CustomOperator(">=", true, 0, 2) {
            protected double applyOperation(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        GTOperator = new CustomOperator(">", true, 0, 2) {
            protected double applyOperation(double[] values) {
                if (values[0] > values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        EQOperator = new CustomOperator("=", true, 0, 2) {
            protected double applyOperation(double[] values) {
                if (values[0] == values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

    }


    private static CustomOperator getGEOperator__() {

        return new CustomOperator(">=", true, 0, 2) {

            protected double applyOperation(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
//                    return values[0];
                } else {
                    return 0d;
//                    return values[1];
                }
            }
        };

    }

    public static CustomOperator getLTOperator() {
        return LTOperator;
    }

    public static CustomOperator getLEOperator() {
        return LEOperator;
    }

    public static CustomOperator getGEOperator() {
        return GEOperator;
    }

    public static CustomOperator getGTOperator() {
        return GTOperator;
    }

    public static CustomOperator getEQOperator() {
        return EQOperator;
    }
}
