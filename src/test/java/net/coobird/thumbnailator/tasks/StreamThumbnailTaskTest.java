package net.coobird.thumbnailator.tasks;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.resizers.Resizers;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StreamThumbnailTaskTest {

    /**
     * Test for the {@link StreamThumbnailTask#StreamThumbnailTask(ThumbnailParameter, InputStream, OutputStream)} constructor, where
     * <ol>
     *     <li>A valid ThumbnailParameter is provided</li>
     *     <li>A non-null InputStream and OutputStream are provided</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     *     <li>StreamThumbnailTask is successfully created</li>
     * </ol>
     */
    @Test
    public void testValidConstructor() {
        ThumbnailParameter param = createValidThumbnailParameter();
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[0]);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        assertDoesNotThrow(() -> new StreamThumbnailTask(param, is, os));
    }

    /**
     * Test for the {@link StreamThumbnailTask#StreamThumbnailTask(ThumbnailParameter, InputStream, OutputStream)} constructor, where
     * <ol>
     *     <li>A null ThumbnailParameter is provided</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     *     <li>NullPointerException is thrown</li>
     * </ol>
     */
    @Test
    public void testConstructorThrowsWhenParamIsNull() {
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[0]);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        assertThrows(NullPointerException.class, () -> new StreamThumbnailTask(null, is, os));
    }

    /**
     * Test for the {@link StreamThumbnailTask#StreamThumbnailTask(ThumbnailParameter, InputStream, OutputStream)} constructor, where
     * <ol>
     *     <li>A null InputStream is provided</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     *     <li>NullPointerException is thrown</li>
     * </ol>
     */
    @Test
    public void testConstructorThrowsWhenInputStreamIsNull() {
        ThumbnailParameter param = createValidThumbnailParameter();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        assertThrows(NullPointerException.class, () -> new StreamThumbnailTask(param, null, os));
    }

    /**
     * Test for the {@link StreamThumbnailTask#StreamThumbnailTask(ThumbnailParameter, InputStream, OutputStream)} constructor, where
     * <ol>
     *     <li>A null OutputStream is provided</li>
     * </ol>
     * and the expected outcome is,
     * <ol>
     *     <li>NullPointerException is thrown</li>
     * </ol>
     */
    @Test
    public void testConstructorThrowsWhenOutputStreamIsNull() {
        ThumbnailParameter param = createValidThumbnailParameter();
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[0]);

        assertThrows(NullPointerException.class, () -> new StreamThumbnailTask(param, is, null));
    }

    /**
     * Test to ensure that the input stream used in the constructor can be read without exception,
     * simulating that the task stores the stream correctly.
     */
    @Test
    public void testInputStreamIntegrityAfterConstructor() throws IOException {
        ThumbnailParameter param = createValidThumbnailParameter();
        byte[] imageBytes = new byte[]{1, 2, 3, 4, 5};
        ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        StreamThumbnailTask task = new StreamThumbnailTask(param, is, os);

        // Read manually from the same stream for simplicity
        int read = is.read();
        assertEquals(1, read);
    }

    /**
     * Utility method to create a valid ThumbnailParameter instance for testing.
     */
    private ThumbnailParameter createValidThumbnailParameter() {
        return new ThumbnailParameter(
                new Dimension(100, 100),
                null,
                true,
                ThumbnailParameter.ORIGINAL_FORMAT,
                ThumbnailParameter.DEFAULT_FORMAT_TYPE,
                ThumbnailParameter.DEFAULT_QUALITY,
                BufferedImage.TYPE_INT_RGB,
                Collections.emptyList(),
                Resizers.BILINEAR,
                false,
                false
        );
    }
}