package fuzz_targets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffImageParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;

public class TiffImageParserFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {	
		try {
			TiffImageParser p = new TiffImageParser();
			BufferedImage image = p.getBufferedImage(new ByteSourceArray(input), new HashMap<>());
		} catch (IOException | ImageReadException e) {
			return;
		}
	}
}
