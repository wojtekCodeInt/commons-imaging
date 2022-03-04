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


import java.awt.Dimension;
import java.awt.color.ICC_Profile;

import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.IImageMetadata.IImageMetadataItem;

class JpegReachedException extends Exception  
{  
    public JpegReachedException (String str)  
    {  
        // calling the constructor of parent Exception  
        super(str);  
    }  
}  
public class AllImageParserGuessFormat {
	
	public static void fuzzerTestOneInput(FuzzedDataProvider data) 
	throws JpegReachedException{

		final BufferedImage image;
		final ImageFormat format;
		final IImageMetadata metadata;
		try{
			byte[] imagebytes = data.consumeRemainingAsBytes();
			image = Imaging.getBufferedImage(imagebytes);
			final ICC_Profile iccProfile = Imaging.getICCProfile(imagebytes);
			final ImageInfo imageInfo = Imaging.getImageInfo(imagebytes);
			metadata = Imaging.getMetadata(imagebytes);
			format = Imaging.guessFormat(imagebytes);

		} catch(ImageReadException | IOException e){
			return;
		}
		// Fuzz functions specific to formats:
		if (format == ImageFormats.JPEG) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			try {
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
			} catch (ImageReadException | IOException e) {
				return;
			}
		}
		final Map<String, Object> params = new HashMap<>();

		try{
        	Imaging.writeImageToBytes(image, format, params);
		} catch(ImageWriteException | IOException e){
			return;
		}
	}
}
