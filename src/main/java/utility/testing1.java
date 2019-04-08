package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class testing1 {


	/*
	 * Complete the 'mergeArrays' function below.
	 *
	 * The function is expected to return an INTEGER_ARRAY.
	 * The function accepts following parameters:
	 *  1. INTEGER_ARRAY a
	 *  2. INTEGER_ARRAY b
	 */
	public static int palindrome(String s) {
		// Write your code here
		HashSet<String> set = new HashSet<>();
		for ( int i = 0; i < s.length(); i++ ) {
			for ( int j = i; j < s.length(); j++ ) {
				System.out.println( s.substring( i, j ) );
				set.add( s.substring( i, j ) );
			}
		}

		return (int) set.stream()
				.filter( e -> isPlain( s ) )
				.count();

	}

	public static boolean isPlain(String s) {
		return s.equalsIgnoreCase( new StringBuilder( s ).reverse().toString() );
	}

	public static String firstRepeatedWord(String s) {
		// Write your code here
		String[] words = s.trim().split( "\\s+" );

		Set<String> wordSet = new HashSet<>();

		for ( String word : words ) {
			if ( wordSet.contains( word ) ) {
				return word;
			}
			else {
				wordSet.add( word );
			}
		}
		return "";
	}

	public static List<Integer> mergeArrays(List<Integer> a, List<Integer> b) {
		// Write your code here
		int counterA = 0;
		int counterB = 0;

		Integer aValue;
		Integer bValue;

		List<Integer> list = new ArrayList<>();

		while (counterA < a.size() && counterB < b.size() ) {


			aValue = a.get( counterA );
				bValue = b.get( counterB );


			if ( aValue < bValue ) {
				list.add( aValue );
				counterA++;
			}
			else if ( bValue < aValue ) {
				list.add( bValue );
				counterB++;
			}
			else {
				list.add( aValue );
				list.add( bValue );
				counterA++;
				counterB++;
			}

		}

		while ( counterA < a.size() ){
			list.add( a.get( counterA++ ) );
		}


		while ( counterB < b.size() ){
			list.add( b.get( counterB++ ) );
		}

		return list;
	}

	public static void main(String[] args) {

		System.out.println( mergeArrays( Arrays.asList( 35,94 ), Arrays.asList( 2,4 ) ) );
	}


}


