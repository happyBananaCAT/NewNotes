package wuziqi;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BgMusic {
    public BgMusic() throws FileNotFoundException, JavaLayerException {
        System.out.println("音乐播放");
        File file = new File("music\\bj.mp3");
        Player player = new Player(new FileInputStream(file));
        player.play();
    }
}
