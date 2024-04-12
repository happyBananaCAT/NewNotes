package wuziqi;

import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;

public class musicTest {
    public static void main(String[] args) throws Exception {
        File file = new File("src\\music\\bj.mp3");
        Player player = new Player(new FileInputStream(file));
        player.play();
    }
}
