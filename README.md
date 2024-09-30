#Ordered Dithering in Java#

Overview

This repository contains an implementation of Ordered Dithering in Java. Dithering is a technique used in computer graphics to simulate a greater range of colors in images with limited color palettes. The ordered dithering algorithm specifically arranges a set of threshold values (a dithering matrix) to diffuse colors in a structured, grid-like pattern.

Features

	•	Converts an image to grayscale.
	•	Applies ordered dithering using a customizable dithering matrix.
	•	Supports multiple dithering matrix sizes (e.g., 2x2, 4x4).
	•	Outputs dithered images for visual comparison.

Getting Started

Prerequisites

	•	Java Development Kit (JDK): Ensure you have Java 8 or higher installed. You can check your version using:

java -version


	•	IDE or text editor: You can use any Java-compatible IDE like IntelliJ IDEA, Eclipse, or VSCode with Java extensions.

Installation

	1.	Clone the repository:

git clone https://github.com/<your-username>/ordered-dithering-java.git
cd ordered-dithering-java


	2.	Compile the Java code:

javac OrderedDithering.java


	3.	Run the program:

java OrderedDithering <input_image> <output_image>

	•	input_image: Path to the image file to be dithered (e.g., example.png).
	•	output_image: Path to save the output dithered image (e.g., dithered_example.png).

Example Usage

To run the ordered dithering on an image:

java OrderedDithering input_image.png output_image.png

This will apply the dithering process to input_image.png and generate a dithered version saved as output_image.png.

Dithering Matrix

The program uses a Bayer matrix for dithering, which can be modified or replaced with other dithering matrices. The default matrix is the 4x4 Bayer matrix:

1  9  3 11
13 5 15  7
4 12  2 10
16 8 14  6

This matrix can be changed by modifying the ditheringMatrix variable in the code.

Project Structure

	•	OrderedDithering.java: The main class that handles the dithering process.
	•	/images: Directory for storing sample images for testing purposes.
	•	/output: Directory where the dithered images will be saved.

Contributing

Feel free to submit issues or pull requests if you have suggestions or improvements for the code!

License
