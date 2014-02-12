/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package navegacion3D;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import static java.awt.event.KeyEvent.*;

/**
 *
 * @author framg
 */
public class MouseClass {
    
  
    
    //Con la clase robot se podra controlar el raton y teclado.
    private final Robot robot;
    //Tambien se obtiene las dimensiones de la pantalla.
    private final Dimension screenSize;
    
    public MouseClass() throws Exception{
        robot = new Robot();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    //Se pausa el raton y teclada n milisegundos.
    public void pausa(int n){
        robot.delay(n);
    }
    
    //Dado un valor n, si es positivo el scroll sera descendente y si es negativo ascendente.
    public void zoomCamera(int n){
        //Abajo positivo
        //Arriba negativo
        robot.mouseWheel(n);    
    }
    
    //Click izquierdo del raton
    public void seleccionar(){
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    
    //Pulsar tecla alt, se mantiene presionado.
    public void pressRotacion(){
        robot.keyPress(VK_ALT);
    }
    
    //Pulsar tecla alt, se libera.
    public void releaseRotacion(){
        robot.keyRelease(VK_ALT);
    }
    
    //Click central del raton, se mantiene presionado.
    public void pressMovimiento(){
        robot.mousePress(InputEvent.BUTTON2_MASK);
    }
    
    //Click central del raton, se libera.
    public void releaseMovimiento(){
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
    }
    
    //Se actualiza la posicion del raton dados los valores x e y.
    public void setPosition(int x, int y){
        robot.mouseMove(x, y);       
    }
    
    //Obtener la anchura de la pantalla.
    public double getWidth(){
        return screenSize.getWidth();
    }
    
    //Obtener la altura de la pantalla.
    public double getHeight(){
        return screenSize.getHeight();
    }
}
