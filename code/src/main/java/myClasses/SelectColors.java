package myClasses;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectColors {

    public List<ImageComparisonResult> selectColors(String[] folderPaths, int[] inputColors) {

        KMeans kMeans = new KMeans();
        ColorHistogramViewer histogram = new ColorHistogramViewer();

        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String imagePath : targetImagePaths) {

            //BufferedImage targetColors = kMeans.process(imagePath, 256);
            BufferedImage targetColors = IOFils.loadImage(imagePath);
            assert imagePath != null;
            boolean targetHistogram = histogram.colorHistogram(targetColors, inputColors);

            if (targetHistogram) {
                similarities.add(new ImageComparisonResult(imagePath, 0.0));
            }
        }

        // Step 10: Sort and display the similar images
        similarities.sort(Comparator.comparing(ImageComparisonResult::getSimilarity).reversed());
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath());
        }
        return similarities;
    }
}
