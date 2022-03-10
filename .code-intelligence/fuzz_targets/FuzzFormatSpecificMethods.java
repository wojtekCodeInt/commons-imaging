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
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.png.transparencyfilters.*;
import org.apache.commons.imaging.formats.png.BitParser;
import org.apache.commons.imaging.formats.png.scanlinefilters.*;

import java.awt.Dimension;
import java.awt.color.ICC_Profile;

import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.IImageMetadata.IImageMetadataItem;

public class FuzzFormatSpecificMethods {
	private static boolean pngReached;
	private static boolean jpgReached;
	private static boolean xpmReached;
	private static boolean xbmReached;

	public static void fuzzerInitialize() {
		pngReached = false;
		jpgReached = false;
		xpmReached = false;
		xbmReached = false;
	}

	public static void fuzzerTestOneInput(FuzzedDataProvider data) {

		BufferedImage image;
		ImageFormat format;
		IImageMetadata metadata;
		int int1 = data.consumeInt();
		int int2 = data.consumeInt();
		int int3 = data.consumeInt();
		int int4 = data.consumeInt();
		int halfLen = data.remainingBytes()/2;
		byte[] imagebytes = data.consumeBytes(halfLen); //use this as actual image
		byte[] imagebytes2 = data.consumeBytes(halfLen);
		try {
			// image = Imaging.getBufferedImage(imagebytes);
			final ICC_Profile iccProfile = Imaging.getICCProfile(imagebytes);
			final ImageInfo imageInfo = Imaging.getImageInfo(imagebytes);
			metadata = Imaging.getMetadata(imagebytes);
			format = Imaging.guessFormat(imagebytes);

		} catch (ImageReadException | IOException e) {
			return;
		}
		// Fuzz functions specific to formats:
		try {
			if (format == ImageFormats.JPEG) {
				if (!jpgReached) {
					jpgReached = true;
					System.out.println("JPEG reached");
				}
				final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

				jpegMetadata.getEXIFThumbnailSize();
				jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_XRESOLUTION);
				// simple interface to GPS data
				final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
				if (null != exifMetadata) {
					final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
					if (null != gpsInfo) {
						final String gpsDescription = gpsInfo.toString();
						final double longitude = gpsInfo.getLongitudeAsDegreesEast();
						final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
					}
				}
				final List<IImageMetadataItem> items = jpegMetadata.getItems();
				for (int i = 0; i < items.size(); i++) {
					final IImageMetadataItem item = items.get(i);
				}
			} else if (format == ImageFormats.PNG) {
				if (!pngReached) {
					pngReached = true;
					System.out.println("PNG reached");
				}
				// Filters
				final TransparencyFilter filters[] = {
						new TransparencyFilterTrueColor(imagebytes),
						new TransparencyFilterIndexedColor(imagebytes),
						new TransparencyFilterGrayscale(imagebytes),
				};
				for (TransparencyFilter pngfilter : filters) {
					pngfilter.filter(int1, int2);
				}

				final ScanlineFilter slfilters[] = {
					new ScanlineFilterAverage(int1),
					new ScanlineFilterUp(),
					new ScanlineFilterNone(),
					new ScanlineFilterPaeth(int1),
					new ScanlineFilterSub(int1),
				};
				byte[] dst = new byte[halfLen];
				for (ScanlineFilter pngfilter : slfilters) {
					pngfilter.unfilter(imagebytes,dst,imagebytes2);
				}
				// BitParser
				BitParser pngbp = new BitParser(imagebytes,int3,int4);
				pngbp.getSample(int1, int2);
				pngbp.getSampleAsByte(int1,int2);
			} else if (format == ImageFormats.XPM) {
				if (!xpmReached) {
					xpmReached = true;
					System.out.println("XPM reached");
				}
			} else if (format == ImageFormats.XBM) {
				if (!xbmReached) {
					xbmReached = true;
					System.out.println("XBM reached");
				}
			}
		} catch (ImageReadException | IOException e) {
			return;
		}
	}
}
