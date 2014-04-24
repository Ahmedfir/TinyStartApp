package com.ahmed.tinystartapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ahmed.tinystartapp.utils.DataUtil;
import com.ahmed.tinystartapp.utils.FileUtil;
import com.ahmed.tinystartapp.utils.UnixUtils;

/**
 * The first activity lunched
 * <P>
 * I suppose that I am getting from the executable "START" as starting event and
 * "END" as stopping event
 * </P>
 * 
 * @author Ahmed
 */
public class MainActivity extends Activity {

	/**
	 * Simple name of the class for logging purpose.
	 */
	private static final String TAG = MainActivity.class.getSimpleName();

	/**
	 * name of the executable
	 */
	private static final String EXECUTABLE_NAME = "SourceyLiteTest";

	/**
	 * extension of the executable
	 */
	private static final String EXECUTABLE_EXTENSION = ".exe";

	/**
	 * Fake starting event
	 */
	private static final String FAKE_PROCESS_START_EVENT = "START";

	/**
	 * Fake stopping event
	 */
	private static final String FAKE_PROCESS_STOP_EVENT = "END";

	/**
	 * the executable file path
	 */
	private String executableFilePath;

	/**
	 * id of the process of the executable
	 */
	private int executablePid;

	/**
	 * the button to start or stop the service
	 */
	private Button startStopExecButton;

	/**
	 * the click listener to start the service
	 */
	private OnClickListener startExecListener = new OnClickListener() {
		public void onClick(View v) {
			if (DataUtil.isNotEmpty(executableFilePath)) {
				startStopExecButton
						.setText(R.string.TSA_exec_luncher_starting_button);
				startStopExecButton.setOnClickListener(stopExecListener);
				handleExecReturn(UnixUtils.executeFile(executableFilePath));
			}
		}
	};

	/**
	 * the click listener to stop the service
	 */
	private OnClickListener stopExecListener = new OnClickListener() {
		public void onClick(View v) {
			startStopExecButton
					.setText(R.string.TSA_exec_luncher_stopping_button);
			startStopExecButton.setOnClickListener(startExecListener);
			String pid = UnixUtils.getPid(EXECUTABLE_NAME);
			if (DataUtil.isNotEmpty(pid)) {
				executablePid = Integer.parseInt(pid);
				handleExecReturn(UnixUtils.killProcess(executablePid));
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the button
		startStopExecButton = (Button) findViewById(R.id.TSA_start_stop_exec_button);
		// set the listener
		startStopExecButton.setOnClickListener(startExecListener);

		String filename = EXECUTABLE_NAME + EXECUTABLE_EXTENSION;

		String appFileDirectory = getFilesDir().getPath();

		executableFilePath = appFileDirectory + File.separator + filename;

		copyAssets(filename, appFileDirectory);

		// set the file as executable
		UnixUtils.setExecutable(executableFilePath);
	}

	/**
	 * Update the button text
	 * 
	 * @param Return
	 *            from the executable process
	 */
	private void handleExecReturn(String execResponse) {
		if (execResponse.contains(FAKE_PROCESS_START_EVENT)) {
			startStopExecButton.setText(R.string.TSA_exec_luncher_stop_button);
		} else if (execResponse.contains(FAKE_PROCESS_STOP_EVENT)) {
			startStopExecButton.setText(R.string.TSA_exec_luncher_start_button);
		}
	}

	/**
	 * copy the file from assets to the directory
	 * 
	 * 
	 * @param filename
	 *            of the file to copy from the assets
	 * @param directory
	 *            of destination
	 */
	private void copyAssets(String filename, String directory) {

		AssetManager assetManager = getAssets();

		InputStream in = null;
		OutputStream out = null;
		Log.d(TAG, "Attempting to copy this file: " + filename);
		File outFile = null;
		try {
			in = assetManager.open(filename);
			outFile = new File(directory, filename);
			out = new FileOutputStream(outFile);
			FileUtil.copy(in, out);
			Log.d(TAG, "outDir: " + directory);
		} catch (IOException e) {
			Log.e(TAG, "Failed to copy asset file: " + filename + " to : "
					+ outFile.getAbsolutePath(), e);
		}
	}
}
