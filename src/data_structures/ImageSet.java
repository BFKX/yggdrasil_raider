package data_structures;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageSet {
    private final HashMap<Integer, Image> spriteSet;
    private final int[] sequence;
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
