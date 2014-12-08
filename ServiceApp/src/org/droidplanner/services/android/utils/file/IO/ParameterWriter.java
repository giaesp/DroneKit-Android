package org.droidplanner.services.android.utils.file.IO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.droidplanner.services.android.utils.file.FileList;
import org.droidplanner.services.android.utils.file.FileManager;
import org.droidplanner.services.android.utils.file.FileStream;
import org.droidplanner.core.parameters.Parameter;

public class ParameterWriter {
	private List<Parameter> parameterList;

	public ParameterWriter(List<Parameter> param) {
		this.parameterList = param;
	}

	public boolean saveParametersToFile(String filename) {
		try {
			if (!FileManager.isExternalStorageAvailable()) {
				return false;
			}

            if(!filename.endsWith(FileList.PARAM_FILENAME_EXT)){
                filename += FileList.PARAM_FILENAME_EXT;
            }

			FileOutputStream out = FileStream.getParameterFileStream(filename);

			writeFirstLine(out);

			writeWaypointsLines(out);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void writeFirstLine(FileOutputStream out) throws IOException {
		out.write((("#NOTE: " + FileManager.getTimeStamp() + "\n").getBytes()));
	}

	private void writeWaypointsLines(FileOutputStream out) throws IOException {
		for (Parameter param : parameterList) {
			out.write(String.format(Locale.ENGLISH, "%s , %f\n", param.name, param.value)
					.getBytes());
		}
	}
}