import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class DiffieHellman {

	private static boolean isPrimitiveRoot(BigInteger a, BigInteger p) {

		ArrayList<BigInteger> array1 = new ArrayList<>();
		ArrayList<BigInteger> array2 = new ArrayList<>();

		System.out.println("\n ...PLEASE WAIT... \n");
		// an array to get all numbers between 1 and n-1
		for (BigInteger i = BigInteger.ONE; i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) 
			array1.add(i);
		
		if(a.compareTo(BigInteger.ZERO)<0) {
			System.out.println("Enter a postive Number only");
			return false;
		}
		// is r in the range of array1
		else if (!array1.contains(a)) {
			System.out.println("The Number Entered is larger than the Prime Modulo. \nEnter Again");
			return false;
		}
		
		else
			for (BigInteger i = BigInteger.ONE; i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) 
				array2.add(a.modPow(i, p));
			
		for (int i=0; i<array1.size();++i) 
			if(!array2.contains(array1.get(i))){
				System.out.println(a+ " is not a Primitive Root of "+p+"\nEnter Again");
			
				return false;
			}
		return true;
	}


	private static BigInteger getPow(BigInteger A, BigInteger p, BigInteger a){

		BigInteger falsePow = BigInteger.valueOf(-1);
		BigInteger i = BigInteger.ONE;

		while(i.compareTo(p) < 1){

			if(a.modPow(i,p).equals(A)) 
				return i;

			i = i.add(BigInteger.ONE);
		}
		
		return falsePow;
	}

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		BigInteger p = null, a = null;
		boolean flag = false;
		boolean primeFlag = false;
		do {
			System.out.println("\nEnter Modulo Prime Number p: ");
			p = new BigInteger(kb.next());
			if(p.compareTo(BigInteger.ZERO)<0) {
				System.out.println("Enter a positive number only.");
				flag = false;
			}
				
			primeFlag = p.isProbablePrime(5);
			if(!primeFlag)
				System.out.println(p+" is not a Prime Number \n\tEnter again");
		} while (!flag && !primeFlag);

		do {
			System.out.println("\nEnter Primitive Root of p: ");
			a = new BigInteger(kb.next());

		} while (!isPrimitiveRoot(a, p));

		int k1, k2;
		k1 = (int) (Math.random() * 100);
		k2 = (int) (Math.random() * 100);

		System.out.println("Random Private Number of A: " + k1 + "\nRandom Private Number of B: " + k2);

		
		String k1String = String.valueOf(k1);
		String k2String = String.valueOf(k2);

		
		BigInteger kk1 = new BigInteger(k1String);
		BigInteger kk2 = new BigInteger(k2String);

		BigInteger public_1 = a.modPow(kk1, p);
		BigInteger public_2 = a.modPow(kk2, p);
		
		System.out.println("Public Key of A: " + public_1 + "\nPublic key of B: " + public_2);

		BigInteger secret1 = public_2.modPow(kk1, p);
		BigInteger secret2 = public_1.modPow(kk2, p);


		if (secret1.intValue() == secret2.intValue()) {
			System.out.println("\nShared secret number of A and B: " + secret2);
		}

		else {
			System.out.println("something is wrong");
		}

		// bonus
		BigInteger sharedKey = public_2.modPow(getPow(public_1, p, a), p);
		System.out.println("\n(BONUS) Find Shared Secret With Only Public Keys: " + sharedKey);
		System.out.println("{CHECK WORKING IN CODE}");
	}
}