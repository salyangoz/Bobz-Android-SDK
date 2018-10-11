package android.io.bobz.library.popup.v1.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class LayoutDrawable {

    public static Drawable rounded(String color) {

        return new DrawableBuilder()
                .rectangle()
                .cornerRadius(10)
                .solidColor(Color.parseColor(color))
                .build();

    }

    public static Drawable topRound(String color) {

        return new DrawableBuilder()
                .rectangle()
                .topRightRadius(10)
                .topLeftRadius(10)
                .solidColor(Color.parseColor(color))
                .build();
    }

    public static Drawable rectangle(String color) {

        return new DrawableBuilder()
                .rectangle()
                .solidColor(Color.parseColor(color))
                .build();

    }

}
