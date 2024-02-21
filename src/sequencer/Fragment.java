/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

public class Fragment {

	private String nucleotides;


	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
        for (int i = 0; i < nucleotides.length(); i++) {
            char c = nucleotides.charAt(i);
            if (c != 'G' && c != 'C' && c != 'A' && c != 'T') {
                throw new IllegalArgumentException("Invalid character in nucleotides: " + c);
            }
        }
        this.nucleotides = nucleotides;
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
    	return nucleotides.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		 return nucleotides;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		// if (!(o instanceof Fragment)) {
		// 	return false;
		// }

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Fragment f = (Fragment) o;

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f 
		// are semantically equal
		return nucleotides.equals(f.nucleotides);
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		int maxOverlap = 0;
		int minLength = Math.min(this.length(), f.length());
		for (int i = 1; i <= minLength; i++) {
			String endThis = this.nucleotides.substring(this.length() - i);
			String startF = f.nucleotides.substring(0, i);
			
			if (endThis.equals(startF)) {
				maxOverlap = i;
			}
		}
		return maxOverlap;
	}


	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
    int maxOverlap = this.calculateOverlap(f);
    String mergedFragments;

    // Merge fragments based on the maximum overlap
    if (maxOverlap > 0) {
        if (this.toString().endsWith(f.toString().substring(0, maxOverlap))) {
            mergedFragments = this.toString() + f.toString().substring(maxOverlap);
        } else {
            mergedFragments = f.toString() + this.toString().substring(maxOverlap);
        }
    } else {
        // If there's no overlap, just concatenate the fragments
        mergedFragments = this.toString() + f.toString();
    }

    return new Fragment(mergedFragments);
}

}
