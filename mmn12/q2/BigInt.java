import java.util.ArrayList;

public class BigInt implements Comparable<BigInt> {
    private final ArrayList<Integer> digits;
    private boolean negative;

    public BigInt(String number)
    {
        this.digits = new ArrayList<>();
        char[] charArray = number.toCharArray();
        String NUMBER_REGEX = "^[+-]?\\d+$";
        if (!number.matches(NUMBER_REGEX)) {
            throw new IllegalArgumentException();
        }
        for (int i = number.length() - 1; i >= (Character.isDigit(charArray[0]) ? 0 : 1); i--) { // little endian
            this.digits.add(Integer.parseInt(Character.toString(charArray[i])));
        }
        negative = charArray[0] == '-';
        trimZeros();
    }

    // utils
    private BigInt()
    {
        this.digits = new ArrayList<>();
        digits.add(0);
        this.negative = false;
    }
    private BigInt(BigInt other) {
        this.digits = new ArrayList<>(other.digits);
        this.negative = other.negative;
    }
    private static BigInt cloneNegative(BigInt other) {
        BigInt result = new BigInt(other);
        result.negative = !result.negative;
        return result;
    }
    private BigInt cloneAbs()
    {
        BigInt result = new BigInt(this);
        for (int i = 0; i < result.digits.size(); i++) {
            result.digits.set(i, Math.abs(result.digits.get(i)));
        }
        result.negative = false;
        return result;
    }
    private void fillZeros(int bound)
    {
        int size = this.digits.size();
        for (int i = 0; i < bound - size; i++) {
            this.digits.add(0);
        }
    }
    private void trimZeros()
    {
        for (int i = this.digits.size() - 1; i > 0 && this.digits.get(i) == 0; i--) {
            this.digits.remove(i);
        }
    }
    private boolean isZero() {
        return this.digits.size() == 1 && this.digits.get(0) == 0;
    }

    private void carry()
    {
        int bound = this.digits.size();
        for (int i = 0; i < this.digits.size(); i++) {
            int digit = this.digits.get(i);
            if (digit > 9) {
                if (i + 1 >= this.digits.size()) {
                    bound++;
                    this.fillZeros(bound);
                }
                this.digits.set(i + 1, this.digits.get(i + 1) + digit / 10);
                this.digits.set(i, digit % 10);
            } else if (digit < 0 && i + 1 < bound) {
                this.digits.set(i + 1, this.digits.get(i + 1) - 1);
                this.digits.set(i, 10 + this.digits.get(i));
            }
        }
    }

    // public functions
    @Override
    public int compareTo(BigInt other) {
        if ( this.isZero() &&  other.isZero())   return 0;
        if ( this.isZero() && !other.isZero())   return other.negative ? 1 : -1;
        if (!this.isZero() &&  other.isZero())   return !this.negative ? 1 : -1;
        if ( this.negative && !other.negative)   return -1;
        if (!this.negative &&  other.negative)   return  1;
        if (this.digits.size() > other.digits.size()) return !this.negative ? 1 : -1;
        if (this.digits.size() < other.digits.size()) return  this.negative ? 1 : -1;

        for (int i = this.digits.size() - 1; i >= 0; i--) {
            if (!this.digits.get(i).equals(other.digits.get(i))) {
                return this.negative ? ((this.digits.get(i) > other.digits.get(i)) ? -1 :  1)
                                     : ((this.digits.get(i) > other.digits.get(i)) ?  1 : -1);
            }
        }

        return 0;
    }

    public BigInt plus(BigInt other)
    {
        if (!this.negative && other.negative) {
            return this.minus(cloneNegative(other));
        }
        if (this.negative && !other.negative) {
            return other.minus(cloneNegative(this));
        }

        BigInt result = new BigInt(this);
        BigInt otherCopy = new BigInt(other);

        int bound = Math.max(result.digits.size(), otherCopy.digits.size());
        result.fillZeros(bound);
        otherCopy.fillZeros(bound);

        for (int i = 0; i < bound; i++) {
            result.digits.set(i, result.digits.get(i) + otherCopy.digits.get(i));
        }

        result.carry();
        result.trimZeros();
        result.negative = this.negative && other.negative; // actually the other is redundant, but it's clearer.

        return result;
    }

    public BigInt minus(BigInt other)
    {
        if (!this.negative && other.negative) {
            return this.plus(cloneNegative(other));
        }
        if (this.negative && !other.negative) {
            return cloneNegative(cloneNegative(this).plus(other));
        }

        BigInt result = new BigInt(this);
        BigInt otherCopy = new BigInt(other);

        int bound = Math.max(result.digits.size(), otherCopy.digits.size());
        result.fillZeros(bound);
        otherCopy.fillZeros(bound);

        boolean flip = this.cloneAbs().compareTo(other.cloneAbs()) < 0;

        for (int i = 0; i < bound; i++) {
            result.digits.set(i, (result.digits.get(i) - otherCopy.digits.get(i)) * (flip ? -1 : 1));
        }

        result.carry();
        result.trimZeros();
        result.negative = other.compareTo(this) > 0;

        return result;
    }

    public BigInt multiply(BigInt other)
    {
        BigInt result = new BigInt();
        int bound = Math.max(this.digits.size(), other.digits.size());
        result.fillZeros(bound);

        for (int i = 0, spaceIndex = 0; i < this.digits.size(); i++, spaceIndex++) {
            for (int n = 0; n < other.digits.size(); n++) {
                if (result.digits.size() >= spaceIndex + n + 1) {
                    bound++;
                    result.fillZeros(bound);
                }
                int product = this.digits.get(i) * other.digits.get(n);
                int digit = result.digits.get(spaceIndex + n) + product;
                result.digits.set(spaceIndex + n, digit);
            }
        }

        result.carry();
        result.trimZeros();
        result.negative = this.negative ^ other.negative;
        
        return result;
    }

    public BigInt divide(BigInt other) {
        if (other.isZero()) {
            throw new ArithmeticException();
        }

        BigInt result = new BigInt();
        BigInt thisAbs = this.cloneAbs();
        BigInt otherAbs = other.cloneAbs();
        final BigInt incrementValue = other.cloneAbs();

        while (otherAbs.compareTo(thisAbs) <= 0) {
            result = result.plus(new BigInt("1"));
            otherAbs = otherAbs.plus(incrementValue);
        }

        result.carry();
        result.trimZeros();
        result.negative = this.negative ^ other.negative;

        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder(((negative && !this.isZero()) ? "-" : ""));
        for (int i = this.digits.size() - 1; i >= 0; i--) {
            string.append(Math.abs(digits.get(i)));
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BigInt && this.toString().equals(other.toString());
    }
}