package fuzz_targets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.gif.GifImageParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;

public class GifImageParserFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {	
		try {
			GifImageParser p = new GifImageParser();
			BufferedImage image = p.getBufferedImage(new ByteSourceArray(input), new HashMap<>());
		} catch (IOException | ImageReadException e) {
			return;
		}
	}
}
