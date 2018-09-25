package com.fafasoft.flow.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface UploadPr {

	void uplod(OutputStream out, InputStream in,long nFileLength) throws IOException;
}
