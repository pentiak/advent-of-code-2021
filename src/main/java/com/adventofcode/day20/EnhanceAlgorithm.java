package com.adventofcode.day20;

public class EnhanceAlgorithm {
    private final char[] input;

    public EnhanceAlgorithm(char[] input) {
        this.input = input;
    }

    public Image enhance(Image inputImage) {
        int margin = 5;
        char[][] newImageInput = new char[inputImage.getMaxI() - inputImage.getMinI() + margin][];
        for (int i = inputImage.getMinI(); i < inputImage.getMaxI() + margin; i++) {
            char[] newImageRow = new char[inputImage.getMaxJ() - inputImage.getMinJ() + margin];
            for (int j = inputImage.getMinJ(); j < inputImage.getMaxJ() + margin; j++) {
                newImageRow[j] = calculatePixel(inputImage, i - 2, j - 2);
            }
            newImageInput[i] = newImageRow;
        }
        char[][] croppedImage = new char[newImageInput.length - 2][];
        for (int i = 1; i < newImageInput.length - 1; i++) {
            croppedImage[i - 1] = new char[newImageInput[i].length - 2];
            System.arraycopy(newImageInput[i], 1, croppedImage[i - 1], 0, newImageInput[i].length - 2);
        }

        return new Image(croppedImage, calculatePixel(inputImage, -2, -2));
    }

    private char calculatePixel(Image inputImage, int imageI, int imageJ) {
        int algIdx = 0;
        for (int i = imageI - 1; i <= imageI + 1; i++) {
            for (int j = imageJ - 1; j <= imageJ + 1; j++) {
                algIdx = (algIdx << 1) | (inputImage.getPixel(i, j) == '#' ? 1 : 0);
            }
        }
        return input[algIdx];
    }
}
