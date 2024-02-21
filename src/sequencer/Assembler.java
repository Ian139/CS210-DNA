/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.List;

public class Assembler {
	private List<Fragment> fragments;
	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		this.fragments = new ArrayList<>(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return fragments; 
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {
		boolean assembly = false;

        int bestOverlap = -1;
		int indexOfBestOverlap1 = -1;
		int indexOfBestOverlap2 = -1;
		int tieBreaker = 2147483647; //max int
		
		for (int i = 0; i < fragments.size(); i++) {
			for (int j = 0; j < fragments.size(); j++) {
				if (i != j) {
					Fragment fragment1 = fragments.get(i);
					Fragment fragment2 = fragments.get(j);

					int overlap = fragment1.calculateOverlap(fragment2);

					if (overlap > bestOverlap || (overlap == bestOverlap) && fragments.get(j).length() < tieBreaker) {
						bestOverlap = overlap;
						indexOfBestOverlap1 = i;
						indexOfBestOverlap2 = j;
						tieBreaker = fragments.get(j).length();
					}
				}
			}
		}

		if (bestOverlap < 1) {
			return false;
		} else {
			Fragment fragment1 = fragments.get(indexOfBestOverlap1);
            Fragment fragment2 = fragments.get(indexOfBestOverlap2);

			Fragment mergedFragment = fragment1.mergedWith(fragment2);

			if (!fragments.isEmpty()) {
				if (indexOfBestOverlap1 > indexOfBestOverlap2) {
					fragments.remove(indexOfBestOverlap1);
					fragments.remove(indexOfBestOverlap2);
				} else {
					fragments.remove(indexOfBestOverlap2);
					fragments.remove(indexOfBestOverlap1);
				}
			} else {
				// Handle the case when fragments is empty
				fragments.add(mergedFragment);
			}

			assembly = true;
		}
		return assembly; 
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		// while (fragments.size() > 1) {
		// 	assembleOnce();
		// }
	}
}
