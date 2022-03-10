package fuzz_targets;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import java.awt.Dimension;
import java.awt.color.ICC_Profile;

import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.IImageMetadata.IImageMetadataItem;

public class AllImageParserGuessFormat {
	public static void fuzzerTestOneInput(FuzzedDataProvider data) {

		BufferedImage image;
		ImageFormat format;
		IImageMetadata metadata;
		byte[] imagebytes = data.consumeRemainingAsBytes();
		try {
			image = Imaging.getBufferedImage(imagebytes);
			final ICC_Profile iccProfile = Imaging.getICCProfile(imagebytes);
			final ImageInfo imageInfo = Imaging.getImageInfo(imagebytes);
			metadata = Imaging.getMetadata(imagebytes);
			format = Imaging.guessFormat(imagebytes);

		} catch (ImageReadException | IOException e) {
			return;
		}
		final Map<String, Object> params = new HashMap<>();

		try {
			Imaging.writeImageToBytes(image, format, params);
		} catch (ImageWriteException | IOException e) {
			return;
		}
	}
}
