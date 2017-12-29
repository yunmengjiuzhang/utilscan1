package com.zbar.lib;

/**
 * zbar管理器
 *  */
public class ZbarManager {

	static {
		System.loadLibrary("iconv");
		System.loadLibrary("zbar");
	}

	public native String decode(byte[] data, int width, int height, boolean isCrop, int x, int y, int cwidth, int cheight);
}
