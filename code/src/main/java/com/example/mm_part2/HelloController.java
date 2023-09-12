package com.example.mm_part2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import myClasses.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static myClasses.IOFils.loadImage;


public class HelloController implements Initializable {

    @FXML
    public ColorPicker colorPicker1;
    @FXML
    public ColorPicker colorPicker2;
    @FXML
    public ColorPicker colorPicker3;
    @FXML
    public MenuItem saveCroppedImage;
    @FXML
    public MenuItem selectImage;
    @FXML
    public Button selectColor;
    SearchBySize searchSize;
    SearchByDate searchDate;
    SearchByColor searchColor;
    SelectColors selectColors;
    @FXML
    public HBox results;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public ImageView originalImage;
    @FXML
    public Button searchByColor;
    @FXML
    public Button searchBySize;
    @FXML
    public Button searchByDate;
    private String selectedImagePath;
    private BufferedImage croppedImage;

    //////Choose image to search//////
    public void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        Stage stage = (Stage) selectImage.getParentPopup().getOwnerWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedImagePath = file.getPath();
            BufferedImage image = loadImage(selectedImagePath);

            // تحويل BufferedImage إلى Image
            Image fxImage = new Image(file.toURI().toString());

            // عرض الصورة في ImageView
            originalImage.setImage(fxImage);

            // تعيين المعالم الأولية للتحديد
            Rectangle selectionRect = new Rectangle();
            selectionRect.setFill(Color.TRANSPARENT);
            selectionRect.setStroke(Color.RED);
            selectionRect.setStrokeWidth(2);

            // تعيين الأحداث لتحديد المستطيل
            originalImage.setOnMousePressed(event -> {
                selectionRect.setWidth(0);
                selectionRect.setHeight(0);
                selectionRect.setX(event.getX());
                selectionRect.setY(event.getY());
            });

            originalImage.setOnMouseDragged(event -> {
                double width = event.getX() - selectionRect.getX();
                double height = event.getY() - selectionRect.getY();
                selectionRect.setWidth(width);
                selectionRect.setHeight(height);
            });

            originalImage.setOnMouseReleased(event -> {
                // قص الصورة وتحديث المتغير المقصوص
                croppedImage = cropImage(image, (int) selectionRect.getX(), (int) selectionRect.getY(), (int) selectionRect.getWidth(), (int) selectionRect.getHeight());
            });

            // إضافة المستطيل للعرض
            ((Pane) originalImage.getParent()).getChildren().add(selectionRect);
            originalImage.setOnMouseReleased(event -> {
                // قص الصورة وتحديث المتغير المقصوص
                assert image != null;
                croppedImage = cropImage(image, (int) selectionRect.getX(), (int) selectionRect.getY(), (int) selectionRect.getWidth(), (int) selectionRect.getHeight());
                selectionRect.setVisible(false); // إخفاء المستطيل بعد القص
            });

            // ((Pane) originalImage.getParent()).getChildren().remove(selectionRect);
        }
    }

    ///////Save cropped image//////
    @FXML
    public void handleSaveCroppedImage() {
        if (croppedImage != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Cropped Image");
            Stage stage = (Stage) saveCroppedImage.getParentPopup().getOwnerWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    ImageIO.write(croppedImage, "png", file);
                    System.out.println("Cropped image saved successfully.");
                } catch (IOException e) {
                    System.out.println("Failed to save cropped image.");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No cropped image available.");
        }
    }

    ////////////Search by size/////////////
    @FXML
    public void handleSearchBySize() throws IOException {
        String targetImage = selectedImagePath; // قم بتعيين الصورة المستهدفة
        String[] folderPaths = {"C:/testImages/RGB/", "C:/testImages/nature/", "C:/testImages/fruits/"}; // قم بتعيين مسارات المجلدات


        results.getChildren().clear();
        List<ImageComparisonResult> similarities =  searchSize.searchBySize(targetImage, folderPaths);
        for (ImageComparisonResult similarity : similarities) {
            String imagePath = similarity.getImagePath();

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new File(imagePath).toURI().toString()));
            imageView.setFitHeight(results.getHeight()); // ضبط ارتفاع الـ ImageView كمثال
            imageView.setPreserveRatio(true);

            results.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                originalImage.setImage(imageView.getImage());
            });

        }
    }

    ////////////Search by color/////////////
    @FXML
    public void handleSearchByColor() {
        String targetImage = selectedImagePath; // قم بتعيين الصورة المستهدفة
        //String[] folderPaths = {"C:/testImages/RGB/", "C:/testImages/nature/", "C:/testImages/fruits/"}; // قم بتعيين مسارات المجلدات
        String[] folderPaths = handleSelectFolders();
        double threshold = 0.02;

        results.getChildren().clear();
        List<ImageComparisonResult> similarities =  searchColor.searchByColor(targetImage, folderPaths, threshold);
        for (ImageComparisonResult similarity : similarities) {
            String imagePath = similarity.getImagePath();

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new File(imagePath).toURI().toString()));
            imageView.setFitHeight(results.getHeight()); // ضبط ارتفاع الـ ImageView كمثال
            imageView.setPreserveRatio(true);

            results.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                originalImage.setImage(imageView.getImage());
                System.out.println("Mouse Clicked");
            });
        }
    }

    ////////////Search by Date/////////////
    @FXML
    public void handleSearchByDate() {
        String targetImage = selectedImagePath; // قم بتعيين الصورة المستهدفة
        String[] folderPaths = {"C:/testImages/RGB/", "C:/testImages/nature/", "C:/testImages/fruits/"}; // قم بتعيين مسارات المجلدات

        results.getChildren().clear();
        List<ImageComparisonResult> similarities =  searchDate.searchByDate(targetImage, folderPaths);
        for (ImageComparisonResult similarity : similarities) {
            String imagePath = similarity.getImagePath();

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new File(imagePath).toURI().toString()));
            imageView.setFitHeight(results.getHeight()); // ضبط ارتفاع الـ ImageView كمثال
            imageView.setPreserveRatio(true);

            results.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                originalImage.setImage(imageView.getImage());
            });
        }
    }

    ///////////Search by selected colors///////////
    @FXML
    public void handleSelectColors() {
       // String targetImage = selectedImagePath; // قم بتعيين الصورة المستهدفة
       // String[] folderPaths = {"C:/testImages/RGB/", "C:/testImages/nature/", "C:/testImages/fruits/"}; // قم بتعيين مسارات المجلدات
        String[] folderPaths = handleSelectFolders();

        Color selectedColor1 = colorPicker1.getValue(); // الحصول على قيمة اللون المحددة من الـ ColorPicker
        Color selectedColor2 = colorPicker2.getValue();
        Color selectedColor3 = colorPicker3.getValue();

        // Convert JavaFX color to AWT color
        java.awt.Color awtColor1 = new java.awt.Color(
                (float) selectedColor1.getRed(),
                (float) selectedColor1.getGreen(),
                (float) selectedColor1.getBlue()
        );
        java.awt.Color awtColor2 = new java.awt.Color(
                (float) selectedColor2.getRed(),
                (float) selectedColor2.getGreen(),
                (float) selectedColor2.getBlue()
        );
        java.awt.Color awtColor3 = new java.awt.Color(
                (float) selectedColor3.getRed(),
                (float) selectedColor3.getGreen(),
                (float) selectedColor3.getBlue()
        );

        // Convert AWT color to RGB int value
        int targetColorRGB1 = awtColor1.getRGB();
        int targetColorRGB2 = awtColor2.getRGB();
        int targetColorRGB3 = awtColor3.getRGB();

        // Set the target color
        int[] targetColors = new int[]{targetColorRGB1, targetColorRGB2, targetColorRGB3};

        results.getChildren().clear();
        List<ImageComparisonResult> similarities =  selectColors.selectColors(folderPaths, targetColors);
        for (ImageComparisonResult similarity : similarities) {
            String imagePath = similarity.getImagePath();

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new File(imagePath).toURI().toString()));
            imageView.setFitHeight(results.getHeight()); // ضبط ارتفاع الـ ImageView كمثال
            imageView.setPreserveRatio(true);

            results.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                originalImage.setImage(imageView.getImage());
            });
        }
    }

    //////////////Select Folder/////////////
    public String[] handleSelectFolders() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        Stage stage = new Stage();
        File selectedFolder = directoryChooser.showDialog(stage);

        List<File> allSubFolders = new ArrayList<>();
        if (selectedFolder != null) {
            // استدعاء الدالة الخاصة بجمع جميع المجلدات الفرعية
            collectSubFolders(selectedFolder, allSubFolders);
        }

        String[] pathsArray = new String[allSubFolders.size()];

        for (int i = 0; i < allSubFolders.size(); i++) {
            pathsArray[i] = allSubFolders.get(i).getPath();
        }

        return pathsArray;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         searchSize = new SearchBySize();
         searchDate = new SearchByDate();
         searchColor = new SearchByColor();
         selectColors = new SelectColors();
    }

    private BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height) {
        BufferedImage croppedImage = image.getSubimage(x, y, width, height);
        return croppedImage;
    }

    private void collectSubFolders(File folder, List<File> allSubFolders) {
        if (folder.isDirectory()) {
            allSubFolders.add(folder);

            File[] subFolders = folder.listFiles(File::isDirectory);
            if (subFolders != null) {
                for (File subFolder : subFolders) {
                    collectSubFolders(subFolder, allSubFolders);
                }
            }
        }
    }

}