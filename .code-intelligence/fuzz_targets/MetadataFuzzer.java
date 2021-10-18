
package fuzz_targets;

//import org.apache.commons.imaging.examples;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import java.awt.Dimension;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;

import org.apache.commons.imaging.ImageReadException;


public class MetadataFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {

		try{
			final IImageMetadata metadata = Imaging.getMetadata(input);
			final ICC_Profile iccProfile = Imaging.getICCProfile(input);
			final ImageInfo imageInfo = Imaging.getImageInfo(input);

		} catch(ImageReadException | IOException e){}
	}
}
