package com.dragonfly.generics;

public class BoundBasic {
    public static void main(String[] args) {
        ColoredDimension<RedColor> colorColoredDimension = new ColoredDimension<>(new RedColor());
        colorColoredDimension.getColor();
        colorColoredDimension.getXYZ();
    }
}


interface HasColor {
    void getColor();
}

class Dimension {
    public int x = 1, y = 2, z = 3;
}

class RedColor extends Dimension implements HasColor {

    @Override
    public void getColor() {
        System.out.println("red");
    }
}

class ColoredDimension<T extends Dimension & HasColor> extends ColorDimensionFather<T> {
    private T item;

    ColoredDimension(T arg) {
        this.item = arg;
    }


    void getColor() {
        item.getColor();
    }

    void getXYZ() {
        System.out.println(item.x + item.y + item.z);
    }
}


class ColorDimensionFather<T extends HasColor> {}

class BlackColor{ public int x , y , z;}

// 继承的时候子类不能破坏父类的边界