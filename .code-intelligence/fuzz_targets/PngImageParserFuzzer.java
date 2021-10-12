package fuzz_targets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;

public class PngImageParserFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {	
		try {
			PngImageParser p = new PngImageParser();
			BufferedImage image = p.getBufferedImage(new ByteSourceArray(input), new HashMap<>());
		} catch (IOException | ImageReadException e) {
			return;
		}
	}
}
