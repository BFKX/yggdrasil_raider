package tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FPSMeter {
	private final long[] frameTimes = new long[100];
	private int frameTimeIndex = 0;
	private boolean arrayFilled = false;

	public void update(long now, GraphicsContext gc) {
		long oldFrameTime = frameTimes[frameTimeIndex];
		frameTimes[frameTimeIndex] = now;
		frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
		if (frameTimeIndex == 0) {
			arrayFilled = true;
		}
		if (arrayFilled) {
			double height = gc.getCanvas().getHeight();
			double width = gc.getCanvas().getWidth();

			long elapsedNanos = now - oldFrameTime;
			long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
			double frameRate = 1000000000. / elapsedNanosPerFrame;

			gc.save();
			gc.setFill(Color.CHARTREUSE);
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, height / 50));
			gc.fillText(Integer.toString((int) frameRate), width / 1000, height / 60);
			gc.restore();
		}
	}
}