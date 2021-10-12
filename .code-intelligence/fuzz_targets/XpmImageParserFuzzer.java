package fuzz_targets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.xpm.XpmImageParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;

public class XpmImageParserFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {	
		try {
			XpmImageParser p = new XpmImageParser();
			BufferedImage image = p.getBufferedImage(new ByteSourceArray(input), new HashMap<>());
		} catch (IOException | ImageReadException e) {
			return;
		}
	}
}
