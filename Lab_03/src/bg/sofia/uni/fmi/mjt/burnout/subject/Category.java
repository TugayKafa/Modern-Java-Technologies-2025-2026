package bg.sofia.uni.fmi.mjt.burnout.subject;

public enum Category {

    MATH,
    PROGRAMMING,
    THEORY,
    PRACTICAL;

    public static final double MATH_COEFFICIENT = 0.2;
    public static final double PROGRAMMING_COEFFICIENT = 0.1;
    public static final double THEORY_COEFFICIENT = 0.15;
    public static final double PRACTICAL_COEFFICIENT = 0.05;

    public static double getCoefficient(Category category) {
        switch (category) {
            case MATH -> {
                return MATH_COEFFICIENT;
            }
            case PROGRAMMING -> {
                return PROGRAMMING_COEFFICIENT;
            }
            case THEORY -> {
                return THEORY_COEFFICIENT;
            }
            case PRACTICAL -> {
                return PRACTICAL_COEFFICIENT;
            }
        }
        return 0;
    }
}
