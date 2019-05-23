package tools;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageSet {
    private HashMap<Integer, Image> spriteSet;
    private int[] sequence;
    private int current = 0;

    public ImageSet(HashMap<Integer, Image> spriteSet, int[] sequence) {
        this.spriteSet = spriteSet;
        this.sequence = sequence;
    }

    public Image get() {
        Image toReturn = spriteSet.get(sequence[current]);
        current++;
        if (current >= sequence.length) {
            current = 0;
        }
        return toReturn;
    }
}
