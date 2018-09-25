package com.fafasoft.flow;

public class Version {
	private static Version version;
	static {
		version = new Version();
	}

	public static Version getInstance() {
		return version;
	}

	private Version() {
	}

	public String getVersion() {
		return "1.8";
	}

	public float getFVersion() {
		return 1.8f;
	}

	public String getBuild() {
		return "(build130501)正式版";
	}

	public String getCopyright() {
		return "Copyright(C)2009-2014 All Rights Reserved";
	}
}
