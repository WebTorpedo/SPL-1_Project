
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ContourDetection {
    public static void main(String[] args) {
        // Load OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the input image
        String inputImagePath = "C:\\Users\\Asus\\Downloads\\PIC\\4-Figure11-1 (1)_Jpg_Grayscale_Canny.jpg";
        String originalImagePath = "C:\\Users\\Asus\\Downloads\\PIC\\4-Figure11-1 (1) - Copy.png"; // Path to the original image
        Mat image = Imgcodecs.imread(inputImagePath);

        if (image.empty()) {
            System.out.println("Image not found or could not be loaded: " + inputImagePath);
            return;
        }

        contourDetection(inputImagePath, originalImagePath);
    }

    public static String contourDetection(String imagePath, String originalImagePath) {
    	String originalCroppedImagePath = null;
    	
    	try {
            // Load the input image
            Mat image = Imgcodecs.imread(imagePath);

            if (image.empty()) {
                System.out.println("Image not found or could not be loaded: " + imagePath);
                return originalCroppedImagePath;
            }

            Mat edges = new Mat();
            Imgproc.Canny(gray, edges, 30, 200);

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            // Sort contours by area
            contours.sort(new Comparator<MatOfPoint>() {
                @Override
                public int compare(MatOfPoint c1, MatOfPoint c2) {
                    return Double.compare(Imgproc.contourArea(c2), Imgproc.contourArea(c1));
                }
            });

            MatOfPoint licensePlateContour = null;

            // Find the license plate contour
            for (MatOfPoint contour : contours) {
                MatOfPoint2f approxCurve = new MatOfPoint2f();
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                double epsilon = 0.1 * Imgproc.arcLength(contour2f, true);
                Imgproc.approxPolyDP(contour2f, approxCurve, epsilon, true);

                if (approxCurve.total() == 4) {
                    licensePlateContour = contour;
                    break;
                }
            }

            // Verify if the license plate contour is found
            if (licensePlateContour == null) {
                System.out.println("License plate location not detected.");
                return originalCroppedImagePath;
            }


            // Draw the license plate contour on the image
            Imgproc.drawContours(image, Arrays.asList(licensePlateContour), -1, new Scalar(0, 255, 0), 2);

            // Save the image with the contour drawn on it
            String contourImagePath = imagePath.replace(".jpg", "_Contour.jpg");
            Imgcodecs.imwrite(contourImagePath, image);

            // Get the bounding rectangle of the license plate contour
            Rect boundingRect = Imgproc.boundingRect(licensePlateContour);

            // Crop the region inside the contour
            Mat croppedImage = image.submat(boundingRect);

            // Save the cropped image
            String croppedImagePath = imagePath.replace(".jpg", "_Crop.jpg");
            Imgcodecs.imwrite(croppedImagePath, croppedImage);

            // Load the original image
            Mat originalImage = Imgcodecs.imread(originalImagePath);

            if (originalImage.empty()) {
                System.out.println("Original image not found or could not be loaded: " + originalImagePath);
                return originalCroppedImagePath;
            }

            // Crop the region inside the contour in the original image
            Mat originalCroppedImage = originalImage.submat(boundingRect);

            // Save the cropped original image
            originalCroppedImagePath = originalImagePath.replace(".jpg", "_OriginalCrop.jpg");
            Imgcodecs.imwrite(originalCroppedImagePath, originalCroppedImage);

            System.out.println("Contour detection result saved as: " + contourImagePath);
            System.out.println("Processed cropped image saved as: " + croppedImagePath);
            System.out.println("Original cropped image saved as: " + originalCroppedImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return originalCroppedImagePath;
    }
}
