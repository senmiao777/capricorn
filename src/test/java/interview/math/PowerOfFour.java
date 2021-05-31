package interview.math;

import org.junit.Test;

public class PowerOfFour {
    @Test
    public void isPowerOfFour() {
        int n = 1073741824;
        System.out.println("res=" + powerOfFour2(n));
    }

    public boolean powerOfFour(int number) {
        if (number < 0) {
            return false;
        }
        int temp;
        while ((number >= 4) && (number % 4 == 0)) {
            number = number / 4;
        }
        if (number > 4) {
            return false;
        }
        return number == 1;
    }


    public boolean powerOfFour2(int number) {
        if (number <= 0) {
            return false;
        }
        int temp =4;
        while (number % temp == 0 && number >temp) {
            temp *=4;
        }
        return (number == 1) || (number == temp);
    }
}
