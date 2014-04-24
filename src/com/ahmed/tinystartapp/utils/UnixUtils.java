package com.ahmed.tinystartapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility class provides Unix utilities.
 * 
 * 
 * @author Ahmed
 * 
 */
public final class UnixUtils {

	/**
	 * Internal constructor; not to be called as this class provides static
	 * utilities only.
	 */
	private UnixUtils() {
		throw new UnsupportedOperationException("No instances permitted");
	}

	/**
	 * Get the process id by name
	 * 
	 */
	public static final String GET_PROCESS_ID_BY_NAME = "pgrep ";

	/**
	 * substitute the user
	 * 
	 */
	public static final String GET_ROOT_PERMISSIONS = "su ";

	/**
	 * execute an executqble file
	 */
	public static final String EXECUTE_EXECUTABLE = ".";

	/**
	 * kill a process with SIGTERM mode
	 */
	public static final String KILL_PROCESS = "kill ";

	/**
	 * set a file as executable
	 */
	public static final String SET_FILE_AS_EXECUTABLE = "chmod +x ";

	/**
	 * Executes UNIX command.
	 * 
	 * @param commandLine
	 * @return exit value of the process
	 */
	public static String exec(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer outputBuffer = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				outputBuffer.append(buffer, 0, read);
			}
			reader.close();
			process.waitFor();
			return outputBuffer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param executableFilePath
	 * @return exit value of the process
	 */
	public static String executeFile(String executableFilePath) {

		return exec(GET_ROOT_PERMISSIONS + EXECUTE_EXECUTABLE
				+ executableFilePath);
	}

	/**
	 * @param executablePid
	 * @return exit value of the process
	 */
	public static String killProcess(int executablePid) {

		return exec(GET_ROOT_PERMISSIONS + KILL_PROCESS + executablePid);
	}

	/**
	 * @param processName
	 * @return process id
	 */
	public static String getPid(String processName) {

		return exec(GET_PROCESS_ID_BY_NAME + processName);
	}

	/**
	 * @param processName
	 * @return process id
	 */
	public static String setExecutable(String executableFilePath) {

		return exec(GET_PROCESS_ID_BY_NAME + executableFilePath);
	}

}
