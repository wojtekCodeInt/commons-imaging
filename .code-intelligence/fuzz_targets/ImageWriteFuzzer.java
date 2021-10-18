
package fuzz_targets;

//import org.apache.commons.imaging.examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;

public class ImageWriteFuzzer {
	public static void fuzzerTestOneInput(byte[] input) {

		try{
			final BufferedImage image = Imaging.getBufferedImage(input);

        	final ImageFormat format = ImageFormats.TIFF;
        	final Map<String,Object> params = new HashMap<String,Object>();

        	// set optional parameters if you like
        	params.put(ImagingConstants.PARAM_KEY_COMPRESSION, new Integer(
				TiffConstants.TIFF_COMPRESSION_UNCOMPRESSED));

        	final byte bytes[] = Imaging.writeImageToBytes(image, format, params);
		} catch(ImageReadException | ImageWriteException | IOException e){}
	}
}
