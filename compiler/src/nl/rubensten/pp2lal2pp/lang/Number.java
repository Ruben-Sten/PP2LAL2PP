package nl.rubensten.pp2lal2pp.lang;

/**
 * @author Ruben Schellekens
 */
public class Number extends Value implements Element {

    /**
     * -1
     */
    public static final Number MINUS_ONE = new Number(-1);

    /**
     * 0
     */
    public static final Number ZERO = new Number(0);

    /**
     * 1
     */
    public static final Number ONE = new Number(1);

    /**
     * A binary number of max size with only 1s.
     */
    public static final Number ALL_1S = new Number(-1);

    /**
     * The numerical value of the Number.
     */
    private int intValue;

    public Number(int integer) {
        super(integer);

        this.intValue = integer;
    }

    /**
     * Applies the given operator and another number to this one and returns a new number with the
     * new value.
     *
     * @param other
     *         The other number.
     * @param operator
     *         The operator to apply.
     * @return A new number when <code>this [operator] other</code> is calculated.
     */
    public Number calculate(Number other, Operator operator) {
        return operator.calculateNumbers(this, other);
    }

    /**
     * Applies the given (unary) operator to this value.
     */
    public Number calculate(Operator operator) {
        return operator.calculateNumber(this);
    }

    /**
     * @return The integer value of the number.
     */
    public int getIntValue() {
        return intValue;
    }

    @Override
    public String stringRepresentation() {
        return Integer.toString(intValue);
    }

    @Override
    public Value getValue() {
        return this;
    }

}
