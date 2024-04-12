package wuziqi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageValue {
    public static BufferedImage whiteImage = null;
    public static BufferedImage blackImage = null;
    private static String path = "/image/";

    //初始化方法
    public static void init() {
        try {
            whiteImage = ImageIO.read(ImageValue.class.getResourceAsStream(path+"white.png"));
            blackImage = ImageIO.read(ImageValue.class.getResourceAsStream(path+"black.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
