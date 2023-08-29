
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConverter {
    public static String convertToJpg(String inputImagePath) {
        try {
            File inputFile = new File(inputImagePath);
            java.awt.image.BufferedImage inputImage = ImageIO.read(inputFile);

            java.awt.image.BufferedImage outputImage = new java.awt.image.BufferedImage(
                    inputImage.getWidth(), inputImage.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);

            outputImage.createGraphics().drawImage(inputImage, 0, 0, java.awt.Color.WHITE, null);

            String outputImagePath = inputImagePath.replaceFirst("[.][^.]+$", "") + "_Jpg.jpg";
            File outputFile = new File(outputImagePath);
            ImageIO.write(outputImage, "jpg", outputFile);

            System.out.println("Image converted to JPG successfully.");
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Error occurred during image conversion: " + e.getMessage());
            return null;
        }
    }
}
