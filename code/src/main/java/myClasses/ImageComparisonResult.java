package myClasses;

public class ImageComparisonResult implements Comparable<ImageComparisonResult> {
    private String imagePath;
    private double similarity;

    public ImageComparisonResult(String imagePath, double similarity) {
        this.imagePath = imagePath;
        this.similarity = similarity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getSimilarity() {
        return similarity;
    }

    @Override
    public int compareTo(ImageComparisonResult other) {
        // قارن بين قيم الشبهية
        return Double.compare(this.getSimilarity(), other.getSimilarity());
    }

}
