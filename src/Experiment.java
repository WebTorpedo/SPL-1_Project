import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Experiment {
    public static void main(String[] args) {
        String inputImagePath = "C:\\Users\\Asus\\Downloads\\PIC\\Binary.jpg";

        performTesseract(inputImagePath);
    }

    public static void performTesseract(String inputImagePath) {
        String outputTextFilePath = "C:\\Users\\Asus\\Downloads\\PIC\\Output.txt"; // Set the output path here
        String language = "ben";

        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\Users\\Asus\\eclipse-workspace\\Nnew\\tessdata");
            tesseract.setLanguage(language);

            File imageFile = new File(inputImagePath);

            String extractedText = tesseract.doOCR(imageFile);

            try (FileWriter writer = new FileWriter(outputTextFilePath)) {
                writer.write(extractedText);
            }
            
            System.out.println("Text extracted and stored in file: " + outputTextFilePath);
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
        }
    }
}
