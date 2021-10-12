package fuzz_targets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.pcx.PcxImageParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;

public class PcxImageParserFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {	
		try {
			PcxImageParser p = new PcxImageParser();
			BufferedImage image = p.getBufferedImage(new ByteSourceArray(input), new HashMap<>());
		} catch (IOException | ImageReadException e) {
			return;
		}
	}
}
