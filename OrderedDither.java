/*
 * Author: Keketso Tolo
 * Student Number: 202100092
 * CS5430 Multimedia Systems Practical Exercise
 */

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.text.SimpleDateFormat; // For generating a timestamp
import java.util.Date; // For getting the current date and time

public class OrderedDither {

    // This method applies ordered dithering to an image
    public static void orderedDither(String imagePath, int matrixSize) throws IOException {
        // Load the input image from the specified file path
        BufferedImage img = ImageIO.read(new File(imagePath));

        // Convert the image to grayscale for easier processing
        BufferedImage grayscaleImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                // Extract RGB values and calculate the grayscale value using weighted average
                int rgb = img.getRGB(i, j);
                int gray = (int) ((0.3 * ((rgb >> 16) & 0xff)) + (0.59 * ((rgb >> 8) & 0xff)) + (0.11 * (rgb & 0xff)));
                int grayRGB = (gray << 16) | (gray << 8) | gray;
                grayscaleImg.setRGB(i, j, grayRGB); // Set the grayscale value
            }
        }

        // Create a 4x4 dither matrix if the size is 4, otherwise create one dynamically
        int[][] ditherMatrix;
        if (matrixSize == 4) {
            ditherMatrix = new int[][] {
                { 0, 8, 2, 10 },
                { 12, 4, 14, 6 },
                { 3, 11, 1, 9 },
                { 15, 7, 13, 5 }
            };
        } else {
            // If matrix size is different, create an NxN matrix dynamically
            ditherMatrix = createDitherMatrix(matrixSize);
        }

        // Normalize the dither matrix so that values are between 0 and 1
        int n = matrixSize;
        double[][] normalizedDitherMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                normalizedDitherMatrix[i][j] = (double) ditherMatrix[i][j] / (n * n);
            }
        }

        // Now apply dithering to the grayscale image
        BufferedImage ditheredImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < grayscaleImg.getWidth(); i++) {
            for (int j = 0; j < grayscaleImg.getHeight(); j++) {
                // Get grayscale value of the current pixel
                int grayValue = (grayscaleImg.getRGB(i, j) & 0xff);
                // Compare against the threshold from the dither matrix
                double threshold = normalizedDitherMatrix[i % n][j % n] * 255;
                if (grayValue > threshold) {
                    ditheredImg.setRGB(i, j, 0xFFFFFF); // Set pixel to white if above threshold
                } else {
                    ditheredImg.setRGB(i, j, 0x000000); // Otherwise, set pixel to black
                }
            }
        }

        // Generate a unique timestamp for the filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Save the dithered image to the output file with a unique filename
        File outputfile = new File("dithered_image_" + matrixSize + "x" + matrixSize + "_" + timestamp + ".png");
        ImageIO.write(ditheredImg, "png", outputfile); // Write the dithered image to the file

        // Inform the user that the image was saved
        System.out.println("Dithered image saved as: " + outputfile.getName());
    }

    // Helper method to dynamically create a dither matrix of any size (n x n)
    public static int[][] createDitherMatrix(int n) {
        // Base matrix for generating larger matrices
        int[][] baseMatrix = { { 0, 2 }, { 3, 1 } };
        while (baseMatrix.length < n) {
            int size = baseMatrix.length;
            int[][] newMatrix = new int[size * 2][size * 2];
            // Expand the base matrix to a larger size
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newMatrix[i][j] = 4 * baseMatrix[i][j];
                    newMatrix[i][j + size] = 4 * baseMatrix[i][j] + 2;
                    newMatrix[i + size][j] = 4 * baseMatrix[i][j] + 3;
                    newMatrix[i + size][j + size] = 4 * baseMatrix[i][j] + 1;
                }
            }
            baseMatrix = newMatrix; // Update to new matrix size
        }

        // Truncate or adjust matrix to the required size n x n
        int[][] resultMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                resultMatrix[i][j] = baseMatrix[i][j];
            }
        }
        return resultMatrix; // Return the generated matrix
    }

    public static void main(String[] args) {
        try {
            // Test the function with a 4x4 dither matrix
            String imagePath = "C:/Users/Dell/Desktop/Gawx.jpg"; // Make sure the path to your image is correct
            orderedDither(imagePath, 4); // 4x4 matrix
        } catch (IOException e) {
            // Handle exceptions when there's an error in reading or writing the image
            System.out.println("Error processing the image: " + e.getMessage());
        }
    }
}
