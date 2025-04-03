package net.coobird.thumbnailator.utils.exif;

import net.coobird.thumbnailator.util.exif.ExifUtils;
import net.coobird.thumbnailator.util.exif.Orientation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExifUtilsTest {

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>No EXIF metadata is present in the image</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>The method returns null</li>
     * </ol>
     */
    @Test
    public void testGetExifOrientationReturnsNullForNoExif() throws IOException {
        File file = new File("src/test/resources/Exif/original.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNull(result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 1</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.TOP_LEFT is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation1() throws Exception {
        File file = new File("src/test/resources/Exif/orientation_1.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);

        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext(), "No ImageReader found for the file.");

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result, "Orientation should not be null.");
        assertEquals(Orientation.TOP_LEFT, result, "Expected orientation is RIGHT_TOP (6).");
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 2</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.TOP_RIGHT is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation2() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_2.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.TOP_RIGHT, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 3</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.BOTTOM_RIGHT is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation3() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_3.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.BOTTOM_RIGHT, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 4</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.BOTTOM_LEFT is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation4() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_4.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.BOTTOM_LEFT, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 5</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.LEFT_TOP is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation5() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_5.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.LEFT_TOP, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 6</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.RIGHT_TOP is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation6() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_6.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.RIGHT_TOP, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 7</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.RIGHT_BOTTOM is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation7() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_7.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.RIGHT_BOTTOM, result);
    }

    /**
     * Test for the {@link ExifUtils#getExifOrientation(ImageReader, int)} method, where
     * <ol>
     * <li>Image contains EXIF orientation value 8</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     * <li>Orientation.LEFT_BOTTOM is returned</li>
     * </ol>
     */
    @Test
    public void testExifOrientation8() throws IOException {
        File file = new File("src/test/resources/Exif/orientation_8.jpg");
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());

        ImageReader reader = readers.next();
        reader.setInput(iis);

        Orientation result = ExifUtils.getExifOrientation(reader, 0);
        assertNotNull(result);
        assertEquals(Orientation.LEFT_BOTTOM, result);
    }
}
