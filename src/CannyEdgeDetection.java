
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CannyEdgeDetection {
    public static void main(String[] args) {
        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Provide the input image path
        String inputImagePath = "C:\\Users\\Asus\\Downloads\\PIC\\4-Figure11-1 (1)_Jpg_Grayscale.jpg";

        try {
            // Perform Canny edge detection and get the output image path
            String outputImagePath = makeCannyEdgeDetection(inputImagePath);
            System.out.println("Canny Detection done successfully. Result saved at: " + outputImagePath);
        } catch (Exception e) {
            System.out.println("Error during Canny edge detection: " + e.getMessage());
        }
    }

    public static String makeCannyEdgeDetection(String imagePath) {
        try {
            // Read input image
            Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);

            // Apply Gaussian blur to reduce noise
            Mat blurredImage = new Mat();
            Imgproc.GaussianBlur(image, blurredImage, new Size(5, 5), 0);

            // Perform Canny edge detection
            Mat edges = new Mat();
            double threshold1 = 100;  // lower threshold for hysteresis procedure
            double threshold2 = 200;  // higher threshold for hysteresis procedure
            Imgproc.Canny(blurredImage, edges, threshold1, threshold2);

            // Build the output image path
            String outputImagePath = imagePath.replaceFirst("[.][^.]+$", "") + "_Canny.jpg";

            // Save the result
            Imgcodecs.imwrite(outputImagePath, edges);

            return outputImagePath;
        } catch (Exception e) {
            System.out.println("Error during Canny edge detection: " + e.getMessage());
            return null;
        }
    }
}

