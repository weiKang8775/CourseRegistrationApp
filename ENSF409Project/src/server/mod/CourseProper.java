package server.mod;

import java.util.ArrayList;

/**
 * Provides information about a course with basic information as well as pre-req
 * and course offering information
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class CourseProper extends CourseInfo {
	/**
	 * The list of pre-requisites of the course
	 */
	private ArrayList<CourseInfo> preReq;
	/**
	 * The list of course offerings of the course
	 */
	private ArrayList<CourseOffering> courseOffering;

	/**
	 * Constructs a new course with the given arguments
	 * 
	 * @param name        The name of the course
	 * @param number      The number of the course
	 * @param description The description of the course
	 * @param unit        The number of units of the course
	 */
	public CourseProper(String name, String number, String description, Integer unit) {
		super(name, number, description, unit);
		preReq = new ArrayList<>();
		courseOffering = new ArrayList<>();
	}

	/**
	 * Adds an offering to the course
	 * 
	 * @param co The course offering
	 */
	public void addOffering(CourseOffering co) {
		courseOffering.add(co);
	}

	/**
	 * Adds a pre-requisite to the course
	 * 
	 * @param ci The pre-requisite
	 */
	public void addPreReq(CourseInfo ci) {
		preReq.add(ci);
	}

	/**
	 * Returns the pre-requisite of the course
	 * 
	 * @return preReq
	 */
	public ArrayList<String> getPreReq() {
		ArrayList<String> result = new ArrayList<>();
		for (CourseInfo ci : preReq) {
			result.add(String.valueOf(ci));
		}
		return result;
	}

	/**
	 * Returns the section of the course
	 * 
	 * @return section
	 */
	public ArrayList<String> getSection() {
		ArrayList<String> result = new ArrayList<>();
		for (CourseOffering co : courseOffering) {
			result.add(String.valueOf(co) + "\n");
		}
		return result;
	}

	/**
	 * Checks if a transcript meets the pre-requisite of this course
	 * 
	 * @param transcript The transcript to be checked
	 * @return True if the transcript meets the pre-req requirements or false
	 *         otherwise
	 */
	public boolean checkPreReq(ArrayList<TranscriptItem> transcript) {
		boolean check = false;

		if (preReq.size() == 0) {
			return true;
		}

		for (CourseInfo ci : preReq) {
			check = false;
			for (TranscriptItem ti : transcript) {
				if (ti.getCourse().compareTo(ci) == 0) {
					check = true;
					break;
				}
			}
		}
		return check;
	}

	/**
	 * Returns the course offering of a specified section
	 * 
	 * @param section The section of the course offering
	 * @return The course offering or null if the section does not exist
	 */
	public CourseOffering getOffering(int section) {
		try {
			return courseOffering.get(section);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

	}

	@Override
	/**
	 * Provides a description of the course
	 */
	public String toString() {
		String s = "Pre-Requisite: \n";
		for (CourseInfo ci : preReq) {
			s += "\n" + String.valueOf(ci);
		}

		s += "\n\nOffering: \n";

		for (CourseOffering co : courseOffering) {
			s += "\n" + String.valueOf(co);
		}

		return s;
	}
}
