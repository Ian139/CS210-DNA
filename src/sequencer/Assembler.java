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
		int indexOfBestOverlap = -1;
		
		for (int i = 0; i < fragments.size(); i++) {
			for (int j = i + 1; j < fragments.size(); j++) {
				Fragment fragment1 = fragments.get(i);
				Fragment fragment2 = fragments.get(j);

				int overlap = fragment1.calculateOverlap(fragment2);

				if (overlap > bestOverlap && overlap >= 1) {
					bestOverlap = overlap;
					indexOfBestOverlap = i;
				}
			}
		}

		if (indexOfBestOverlap != -1) {
			Fragment fragment1 = fragments.get(indexOfBestOverlap);
            Fragment fragment2 = fragments.get(indexOfBestOverlap + 1);

			Fragment mergedFragment = fragment2.mergedWith(fragment1);

			fragments.remove(indexOfBestOverlap);
			fragments.remove(indexOfBestOverlap);

			fragments.add(indexOfBestOverlap, mergedFragment);

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
