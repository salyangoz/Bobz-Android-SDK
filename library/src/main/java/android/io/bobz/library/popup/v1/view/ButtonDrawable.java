package android.io.bobz.library.popup.v1.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import top.defaults.drawabletoolbox.DrawableBuilder;

public final class ButtonDrawable {

    public static Drawable rounded(String color) {

        return new DrawableBuilder()
                .rectangle()
                .rounded()
                .solidColor(Color.parseColor(color))
                .solidColorPressed(Color.parseColor(color))
                .ripple()
                .build();

    }

    public static Drawable rectangle(String color) {

        return new DrawableBuilder()
                .rectangle()
                .solidColor(Color.parseColor(color))
                .solidColorPressed(Color.parseColor(color))
                .ripple()
                .build();

    }

    public static Drawable roundedWithBorder(String solidColor, String strokeColor) {

        return new DrawableBuilder()
                .rectangle()
                .hairlineBordered()
                .solidColor(Color.parseColor(solidColor))
                .solidColorPressed(Color.parseColor(solidColor))
                .strokeColor(Color.parseColor(strokeColor))
                .strokeColorPressed(Color.parseColor(strokeColor))
                .ripple()
                .rounded()
                .build();
    }

    public static Drawable rectangleWithBorder(String solidColor, String strokeColor) {

        return new DrawableBuilder()
                .rectangle()
                .hairlineBordered()
                .solidColor(Color.parseColor(solidColor))
                .solidColorPressed(Color.parseColor(solidColor))
                .strokeColor(Color.parseColor(strokeColor))
                .strokeColorPressed(Color.parseColor(strokeColor))
                .ripple()
                .build();
    }

    public static Drawable roundedGradient(String startColor, String endColor) {

        return new DrawableBuilder()
                .rectangle()
                .gradient()
                .linearGradient()
                .angle(180)
                .startColor(Color.parseColor(startColor))
                .endColor(Color.parseColor(endColor))
                .ripple()
                .rounded()
                .build();
    }

}
