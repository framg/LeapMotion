/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package navegacion3D;

import com.leapmotion.leap.Controller;




/**
 *
 * @author framg
 */


public class Navegador {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se crea un listener.
        mainListener listener = new mainListener();
        Controller controller = new Controller();
        
        controller.addListener(listener);   

        //Con este metodo se introduce en un bucle donde ira actualizando continuamente la posicion
        //del raton con respecto a la posicion de la mano detectada por leapmotion
        listener.movimientoRaton();

        
        controller.removeListener(listener);  
    }
    
}
                     