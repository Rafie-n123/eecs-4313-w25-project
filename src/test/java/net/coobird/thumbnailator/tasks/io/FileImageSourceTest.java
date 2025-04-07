package net.coobird.thumbnailator.tasks.io;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import net.coobird.thumbnailator.tasks.UnsupportedFormatException;


class FileImageSourceTest {

	@Test
	public void FileImageSourceTest1() {
		
		File f0 = new File("");
		File f1 = new File("test1.jpg");	
		
		FileImageSource fis1 = new FileImageSource(f0);		
		FileImageSource fis2 = new FileImageSource(f1);
		
		assertNotNull(fis1);
		assertNotNull(fis2);
		
	}
	
	@Test
	public void FISExceptionTest() {
		
			File fnull = null;	
			Exception exception = assertThrows(NullPointerException.class, () -> {
            new FileImageSource(fnull);
		});
			
			assertEquals("File cannot be null.", exception.getMessage()); 
			
	}	
	
	@Test
	public void FileImageSourceTest2() {
			
		FileImageSource fis11 = new FileImageSource("src\\test\\java\\net\\coobird\\thumbnailator\\tasks\\io\\test1.jpg");
		
		assertNotNull(fis11);
		
	}
		
	@Test
	public void FISExceptionTest2() {
		
			String fnull = null;
			Exception exception = assertThrows(NullPointerException.class, () -> {
            new FileImageSource(fnull);
		});
			
			assertEquals("File cannot be null.", exception.getMessage()); 
			
	}	

	@Test
	public void readTest() throws IOException {
		
		Path path = Paths.get("test/resources/test1.jpg");
		File f0 = path.toFile();
		FileImageSource fsi1 = new FileImageSource(f0);
		BufferedImage testImage = fsi1.read();		
		assertNotNull(testImage);
		
	}
	
	@Test
	public void nullFileTest() throws IOException {
		
		File fNull = null;
		
		Exception exception = assertThrows(NullPointerException.class, () -> {
 		((ImageSource<File>) fNull).read();          
		});
			
			assertEquals("Cannot invoke \"net.coobird.thumbnailator.tasks.io.ImageSource.read()\" because \"<parameter1>\" is null", exception.getMessage()); 
			
	}	
	
	
	@Test
	public void nonExistentFileTest() throws IOException {
		
		File f0 = new File("C:\\Users\\joshh\\Downloads\\doesNotExist.jpg");
		FileImageSource fdne = new FileImageSource(f0);
		
		Exception exception = assertThrows(FileNotFoundException.class, () -> {
            fdne.read();
		});
			
			assertEquals("Could not find file: C:\\Users\\joshh\\Downloads\\doesNotExist.jpg", exception.getMessage()); 
			
	}	
	
	@Test
	public void unsupportedFormatTest() throws IOException {
		
		Path path = Paths.get("test/resources/unsupportedFormat.txt");
		File f0 = path.toFile();
		FileImageSource fuf = new FileImageSource(f0);
		
		Exception exception = assertThrows(UnsupportedFormatException.class, () -> {
            fuf.read();
		});
			
			assertEquals("No suitable ImageReader found for " + f0.getAbsolutePath()+".", exception.getMessage()); 
			
	}

}
