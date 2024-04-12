package wuziqi;

import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;


public class MainApp {
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        Object[] objects={"人人模式","人机模式"};
        int a = JOptionPane.showOptionDialog(null,"请选择游戏模式",
                "请选择",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                objects, objects[0]);
        if (a == 0){
            MyJFrame mj = new MyJFrame();
            mj.myJFrame();
        }else if (a == 1){
            MyJFrameAI mjAI = new MyJFrameAI();
            mjAI.myJFrame();
        }else{
            System.exit(0);
        }
    }
}
