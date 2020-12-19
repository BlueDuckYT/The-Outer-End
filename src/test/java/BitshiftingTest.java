public class BitshiftingTest {
	public static void main(String[] args) {
		int numWrong = 0;
		for (int i=0;i<=16;i++) {
			System.out.println("normal:"+(-i >> 2));
			System.out.println("float:"+(int)Math.floor(floatBitShift(-i+1,2)));
			numWrong += (-i >> 2 == (int)Math.floor(floatBitShift(-i+1,2)))?0:1;
		}
		System.out.println("wrong:"+numWrong);
	}
	
	public static float floatBitShift(int input, int amt) {
		boolean isNegative = amt < 0;
		float out = Math.abs(input) / (float)(amt+1);
		return isNegative?out:-out;
	}
}
