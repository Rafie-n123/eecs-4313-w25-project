package net.coobird.thumbnailator.tasks;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.tasks.io.ImageSink;
import net.coobird.thumbnailator.tasks.io.ImageSource;
import net.coobird.thumbnailator.resizers.FixedResizerFactory;
import net.coobird.thumbnailator.resizers.ProgressiveBilinearResizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.*;
import java.awt.image.BufferedImage;

class SourceSinkThumbnailTaskTest {

    private ThumbnailParameter param;
    private ImageSource<String> mockSource;
    private ImageSink<String> mockSink;

    @BeforeEach
    void setUp() {
        param = new ThumbnailParameter(
            new Dimension(100, 100),
            null,
            true,
            ThumbnailParameter.DETERMINE_FORMAT,
            ThumbnailParameter.DEFAULT_FORMAT_TYPE,
            ThumbnailParameter.DEFAULT_QUALITY,
            ThumbnailParameter.ORIGINAL_IMAGE_TYPE,
            null,
            new FixedResizerFactory(new ProgressiveBilinearResizer()),
            true,
            false
        );

        mockSource = mock(ImageSource.class);
        mockSink = mock(ImageSink.class);
    }

    @Test
    void testConstructorWithValidArgs() {
        assertDoesNotThrow(() -> new SourceSinkThumbnailTask<>(param, mockSource, mockSink));
        verify(mockSource).setThumbnailParameter(param);
        verify(mockSink).setThumbnailParameter(param);
    }

    @Test
    void testConstructorWithNullSourceThrows() {
        assertThrows(NullPointerException.class, () -> new SourceSinkThumbnailTask<>(param, null, mockSink));
    }

    @Test
    void testConstructorWithNullSinkThrows() {
        assertThrows(NullPointerException.class, () -> new SourceSinkThumbnailTask<>(param, mockSource, null));
    }

    @Test
    void testReadReturnsBufferedImageAndSetsInputFormat() throws Exception {
        BufferedImage fakeImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(mockSource.read()).thenReturn(fakeImage);
        when(mockSource.getInputFormatName()).thenReturn("jpeg");

        SourceSinkThumbnailTask<String, String> task = new SourceSinkThumbnailTask<>(param, mockSource, mockSink);
        BufferedImage result = task.read();

        assertNotNull(result);
        verify(mockSource).getInputFormatName();
        verify(mockSource).read();
    }

    @Test
    void testWriteWithDetermineFormatUsesPreferredSinkFormat() throws Exception {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(mockSink.preferredOutputFormatName()).thenReturn("png");

        SourceSinkThumbnailTask<String, String> task = new SourceSinkThumbnailTask<>(param, mockSource, mockSink);
        task.write(img);

        verify(mockSink).setOutputFormatName("png");
        verify(mockSink).write(img);
    }

    @Test
    void testWriteWithOriginalFormatUsesInputFormatName() throws Exception {
        ThumbnailParameter originalFormatParam = new ThumbnailParameter(
            new Dimension(100, 100),
            null,
            true,
            ThumbnailParameter.ORIGINAL_FORMAT,
            ThumbnailParameter.DEFAULT_FORMAT_TYPE,
            ThumbnailParameter.DEFAULT_QUALITY,
            ThumbnailParameter.ORIGINAL_IMAGE_TYPE,
            null,
            new FixedResizerFactory(new ProgressiveBilinearResizer()),
            true,
            false
        );

        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(mockSource.read()).thenReturn(img);
        when(mockSource.getInputFormatName()).thenReturn("jpeg");

        SourceSinkThumbnailTask<String, String> task = new SourceSinkThumbnailTask<>(originalFormatParam, mockSource, mockSink);
        task.read(); // required to set inputFormatName internally
        task.write(img);

        verify(mockSink).setOutputFormatName("jpeg");
        verify(mockSink).write(img);
    }

    @Test
    void testGetSourceReturnsActualSource() {
        when(mockSource.getSource()).thenReturn("source-file.jpg");

        SourceSinkThumbnailTask<String, String> task = new SourceSinkThumbnailTask<>(param, mockSource, mockSink);
        assertEquals("source-file.jpg", task.getSource());
    }

    @Test
    void testGetDestinationReturnsActualSink() {
        when(mockSink.getSink()).thenReturn("thumb.jpg");

        SourceSinkThumbnailTask<String, String> task = new SourceSinkThumbnailTask<>(param, mockSource, mockSink);
        assertEquals("thumb.jpg", task.getDestination());
    }
}
