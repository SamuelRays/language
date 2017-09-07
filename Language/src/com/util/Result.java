package com.util;

import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;

public enum Result {
    CORRECT("CORRECT", LinearGradient.valueOf("from 0% 0% to 100% 100%, #00ffff , #ff00ff 100%"),
            LinearGradient.valueOf("from 0% 0% to 100% 100%, #ff00ff  0% , #00ffff 100%")),
    WRONG("WRONG", LinearGradient.valueOf("from 0% 0% to 100% 100%, #ff4000  0% , #ffff00 100%"),
            LinearGradient.valueOf("from 0% 0% to 100% 100%, #ffff00  0% , #ff4000 100%"));

    private String name;
    private Paint textPaint;
    private Paint fieldPaint;

    Result(String name, Paint textPaint, Paint fieldPaint) {
        this.name = name;
        this.textPaint = textPaint;
        this.fieldPaint = fieldPaint;
    }

    public String getName() {
        return name;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public Paint getFieldPaint() {
        return fieldPaint;
    }
}
