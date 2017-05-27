
/**
592. Fraction Addition and Subtraction

Given a string representing an expression of fraction addition and subtraction,
you need to return the calculation result in string format. The final result
should be irreducible fraction. If your final result is an integer, say 2, you
need to change it to the format of fraction that has denominator 1. So in this
case, 2 should be converted to 2/1.

Example 1:

Input:"-1/2+1/2"
Output: "0/1"

Example 2:

Input:"-1/2+1/2+1/3"
Output: "1/3"

Example 3:

Input:"1/3-1/2"
Output: "-1/6"

Example 4:

Input:"5/3+1/3"
Output: "2/1"

Note:

1. The input string only contains '0' to '9', '/', '+' and '-'. So does the
   output.
2. Each fraction (input and output) has format ±numerator/denominator. If the
   first input fraction or the output is positive, then '+' will be omitted.
3. The input only contains valid irreducible fractions, where the numerator
   and denominator of each fraction will always be in the range [1,10]. If
   the denominator is 1, it means this fraction is actually an integer in a
   fraction format defined above.
4. The number of given fractions will be in the range [1,10].
5. The numerator and denominator of the final result are guaranteed to be
   valid and in the range of 32-bit int.
*/
    

public class Solution {
  public String fractionAddition(String expression) {
    int[] numerator = new int[10];
    int[] denominator = new int[10];
    int i = 0;
    
    while(expression.length() > 0) {
      // look for /
      //System.out.println("expression=" + expression);
      int slashIndex = expression.indexOf("/");
      if(slashIndex > 0) {
        numerator[i] = Integer.parseInt(expression.substring(0, slashIndex));
        if((slashIndex +  2 < expression.length())
            && Character.isDigit(expression.charAt(slashIndex + 2))) {
          if((slashIndex + 3 < expression.length())
              && Character.isDigit(expression.charAt(slashIndex + 3))) {
            denominator[i] = Integer.parseInt(expression.substring(slashIndex+1, slashIndex+4));
            expression = expression.substring(slashIndex+4);
          } else {
            denominator[i] = Integer.parseInt(expression.substring(slashIndex+1, slashIndex+3));
            expression = expression.substring(slashIndex+3);
          }
        } else {
          denominator[i] = Integer.parseInt(expression.substring(slashIndex+1, slashIndex+2));
          expression = expression.substring(slashIndex+2);
        }
      }
      i++;
    }
    findCommonDenominator(numerator, denominator);
    for(int j=0; j<10; j++) {
      System.out.print("numerator=" + numerator[j]);
      System.out.println(" denominator=" + denominator[j]);
    }
    // add fractions together
    int resultN = 0;
    for(int j=0; j<10; j++) {
      resultN += numerator[j];
    }
    int resultD = denominator[0];
    System.out.println("resultN=" + resultN + " resultD=" + resultD);
    // print result
    int k = Math.min(Math.abs(resultN), Math.abs(resultD));
    while(k > 0) {
      if(((resultN % k) == 0) && ((resultD % k) == 0)) {
        // found greatest common divisor
        resultN = resultN / k;
        resultD = resultD / k;
        k = 0;
      }
      k--;
    }
    // if numerator is 0, use denominator=1
    if(resultN == 0) {resultD = 1;}
    System.out.println("resultN=" + resultN + " resultD=" + resultD);
    return "" + resultN + "/" +resultD;
  }

  public void findCommonDenominator(int[] numerator, int[] denominator) {
    int curD = 0;
    int nextD = 0;
    int i = 1;
    
    if(denominator.length > 1) {
      while(i < denominator.length) {

        curD = denominator[i - 1];
        nextD = denominator[i];
        if((curD > 0) && (nextD > 0)) {
          if(nextD > curD) {
            if((nextD % curD) > 0) { // curD=2 nextD=3
              // calculate new denominator: curD * nextD
              denominator[i - 1] = denominator[i - 1] * nextD;
              numerator[i - 1] = numerator[i - 1] * nextD;
              denominator[i] = denominator[i] * curD;
              numerator[i] = numerator[i] * curD;
            } else { // curD=2 nextD=4
              denominator[i- 1] = denominator[i - 1] * (nextD/curD);
              numerator[i - 1] = numerator[i - 1] * (nextD/curD);
            }
            i = 1;
          } else if(nextD == curD) {
            i++;
          } else { // nextD < curD
            if((curD % nextD) > 0) { // curD=3 nextD=2
              // calculate new denominator: curD * nextD
              denominator[i - 1]  = denominator[i - 1] * nextD;
              numerator[i - 1] = numerator[i - 1] * nextD;
              denominator[i] = denominator[i] * curD;
              numerator[i] = numerator[i] * curD;
            } else { // curD=4 nextD=2
              denominator[i] = denominator[i] * (curD/nextD);
              numerator[i] = numerator[i] * (curD/nextD);
            }
            i = 1;
          }
        } else {
          i = denominator.length;
        }
      }
    }
    return;
  }

  public static void main(String[] args) {
    String[] tests = {"-1/2+1/2", "-1/2+1/2+1/3", "1/3-1/2", "5/3+1/3",
        "-1/4-4/5-1/4"};
    Solution s = new Solution();
    
    for(int i = 0; i < tests.length; i++) {
      System.out.println("Input:" + tests[i]);
      System.out.println("Output:" + s.fractionAddition(tests[i]));
    }
  }
}
