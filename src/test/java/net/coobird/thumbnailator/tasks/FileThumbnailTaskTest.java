package net.coobird.thumbnailator.tasks;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.resizers.FixedResizerFactory;
import net.coobird.thumbnailator.resizers.ProgressiveBilinearResizer;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

class FileThumbnailTaskTest {

    private File sourceFile;
    private File destinationFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a simple test image and save to sourceFile
        BufferedImage testImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = testImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 200, 200);
        g2d.dispose();

        sourceFile = File.createTempFile("source", ".jpg");
        ImageIO.write(testImage, "jpg", sourceFile);
        sourceFile.deleteOnExit();

        destinationFile = File.createTempFile("destination", ".jpg");
        destinationFile.deleteOnExit();
    }

    private ThumbnailParameter createDefaultThumbnailParam() {
        return new ThumbnailParameter(
            new Dimension(100, 100),              // Target size
            null,                                 // Source region
            true,                                 // Keep aspect ratio
            ThumbnailParameter.ORIGINAL_FORMAT,   // Output format
            ThumbnailParameter.DEFAULT_FORMAT_TYPE,
            ThumbnailParameter.DEFAULT_QUALITY,
            ThumbnailParameter.ORIGINAL_IMAGE_TYPE,
            null,                                 // No filters
            new FixedResizerFactory(new ProgressiveBilinearResizer()),
            true,                                 // Fit within dimensions
            false                                 // Don't use EXIF orientation
        );
    }

    @Test
    void testConstructorWithValidParamsDoesNotThrow() {
        ThumbnailParameter param = createDefaultThumbnailParam();
        assertDoesNotThrow(() -> new FileThumbnailTask(param, sourceFile, destinationFile));
    }

    @Test
    void testConstructorWithNullParamThrows() {
        assertThrows(NullPointerException.class, () -> new FileThumbnailTask(null, sourceFile, destinationFile));
    }

    @Test
    void testReadReturnsBufferedImage() throws IOException {
        ThumbnailParameter param = createDefaultThumbnailParam();
        FileThumbnailTask task = new FileThumbnailTask(param, sourceFile, destinationFile);

        BufferedImage result = task.read();
        assertNotNull(result);
        assertEquals(200, result.getWidth());
        assertEquals(200, result.getHeight());
    }

    @Test
    void testWriteSavesImageToDestination() throws IOException {
        ThumbnailParameter param = createDefaultThumbnailParam();
        FileThumbnailTask task = new FileThumbnailTask(param, sourceFile, destinationFile);

        BufferedImage img = task.read();
        task.write(img);

        assertTrue(destinationFile.exists());
        BufferedImage written = ImageIO.read(destinationFile);
        assertNotNull(written);
    }

    @Test
    void testGetParamReturnsCorrectParam() {
        ThumbnailParameter param = createDefaultThumbnailParam();
        FileThumbnailTask task = new FileThumbnailTask(param, sourceFile, destinationFile);
        assertEquals(param, task.getParam());
    }

    @Test
    void testGetSourceReturnsCorrectSourceFile() {
        ThumbnailParameter param = createDefaultThumbnailParam();
        FileThumbnailTask task = new FileThumbnailTask(param, sourceFile, destinationFile);
        assertEquals(sourceFile, task.getSource());
    }

    @Test
    void testGetDestinationReturnsCorrectDestinationFile() {
        ThumbnailParameter param = createDefaultThumbnailParam();
        FileThumbnailTask task = new FileThumbnailTask(param, sourceFile, destinationFile);
        assertEquals(destinationFile, task.getDestination());
    }

    @AfterEach
    void tearDown() {
        if (sourceFile != null && sourceFile.exists()) {
            sourceFile.delete();
        }
        if (destinationFile != null && destinationFile.exists()) {
            destinationFile.delete();
        }
    }
}
