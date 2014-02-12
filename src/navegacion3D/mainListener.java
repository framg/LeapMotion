/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package navegacion3D;

import com.leapmotion.leap.*;
import static com.leapmotion.leap.Gesture.Type.TYPE_CIRCLE;


/**
 *
 * @author framg
 */
public class mainListener extends Listener{
    //En estas variables x, y, z se almacena constantemente la posicion de la mano para actualizar el cursor.
    float x, y, z;
    MouseClass raton;
    //Var terminar si es true el programa seguira ejecutandose, si es false el programa finalizara.
    boolean noTerminar;
    
    //Variables de control, se usan principalmente para evitar o suavizar errores de leap motion.
    boolean contandoTerminar, movPulsado, movContando;
    boolean zoomActivo, rotacionActivo;
   
    long tiempoSeleccionar, tiempoTerminar;
    long tiempoMovimiento, tiempoZoom, tiempoZoom2;
    long tiempoRotacion;
    
    public mainListener(){
        noTerminar = true;
        contandoTerminar = false;
        movPulsado = false;
        movContando = false;
        zoomActivo = false;
        rotacionActivo = false;
        tiempoRotacion = 0;
        tiempoSeleccionar = 0;
        tiempoTerminar = 0;
        tiempoMovimiento = 0;
        tiempoZoom = 0;
        tiempoZoom2 = 0;
        try{
            raton = new MouseClass  ();
            
        }catch (Exception e){
            System.out.println("Error al inicar el raton");
        }
    }
    
    
    public void movimientoRaton(){
        //Mientras noTerminar sea igual a true el cursor se actualizara a la posicion x,z proporcionada por
        //leap motion
        while(noTerminar){
            double posX, posY;
            posX = x*raton.getWidth();
            posY = z*raton.getHeight();
            raton.setPosition((int)posX,(int)posY);
        }
    }
    
    @Override
    public void onConnect(Controller controller) {
        System.out.println("Leap motion conectado.");
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);

    }
    
    @Override
    public void onDisconnect(Controller controller) {
        noTerminar = false;
        System.out.println("Leap motion desconectado se procede a terminar.");
        
        
    }

    @Override
    public void onExit(Controller controller) {
        System.out.println("Se ha terminado con exito.\n");  
    }
    
    @Override
    public void onInit(Controller controller) {
        System.out.println("Leap Motion ha sido inicializado.");
        System.out.println( "La navegacion 3D ha comenzado.\n"+
                            "Para salir situe ambas manos sobre el leap motion.\n");
        
        //Metodo importante para poder trabajar en background con leapMotion.
        controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
    }
    
    @Override
    public void onFrame(Controller controller) {        
        Frame frame = controller.frame();

        //Terminar el programa, si hay dos manos detectadas por leapMotion se iniciara un contador de tiempo.
        //Tambien no debe haber ningun gesto activo en este momento.
        if(frame.hands().count() == 2 && movPulsado == false && rotacionActivo == false){
            if(contandoTerminar == false){
                tiempoTerminar = frame.timestamp();
                contandoTerminar = true;
            //Si en un 1.5 s aun hay dos manos detectadas el programa finalizara.
            }else if(frame.timestamp() - tiempoTerminar >= 1500000){
                noTerminar = false;             
            }
        }else 
            contandoTerminar = false; 
        
        //Si hay una mano se procedera a la deteccion de 4 gestos.
        //Con un solo dedo y girando de forma horaria o antihoraria se iniciara la rotacion, en este caso alt + click central.
        //Si se detectan 4 dedos se hara un click izquierdo sencillo.
        //Si se cierra el puño 0 dedos detectados se ejecutara un click central, usado para el movimiento.
        //Con 3 o 2 dedos y moviendose en el eje y se utilizara el scroll de la rueda del raton para hacer zoom.
        if (!frame.hands().isEmpty()) {
            
            Hand hand = frame.hands().get(0);
            //Se normaliza la posicion obtenida de la mano para que este en el rango [0,..,1]
            //Gracias a esto conseguimos la posicion absoluta en la pantalla.
            Vector vec = frame.interactionBox().normalizePoint(hand.palmPosition());
            x = vec.getX();
            y = vec.getY();
            z = vec.getZ();
            
            
            //Rotacion, si hay un solo dedo o dos y se hace un gesto de tipo circulo se procede a la rotacion.
            //Ademas se le ha añadido una condicion por la cual solo se reconocera dicho gesto cada 0.5s para evitar
            //introducir accidentalmente dos gestos.
            if(frame.timestamp() - tiempoRotacion >= 500000){
                if (frame.gestures().get(0).type() == TYPE_CIRCLE && (hand.fingers().count() == 1 || hand.fingers().count() == 2)) {
                    // System.out.println("Rotacion"); //Debug comando
                    //Si se ejecuta una vez este gesto se pulsaran dichos botones.
                    tiempoRotacion = frame.timestamp();
                    if(rotacionActivo == false){
                        raton.pressMovimiento();
                        raton.pressRotacion();
                        rotacionActivo = true;
                    //Cuando se vuelva a realizar se soltaran ambos botones.
                    }else{
                        raton.releaseMovimiento();
                        raton.releaseRotacion();
                        rotacionActivo = false;
                    }
                }
            } 
            
            
            //Mientras la rotacion este activa desactiva los otros gestos.
            if(rotacionActivo == false){
                
                //Click izquierdo
                //Se ha utilizado timestamp para dar un intervalo de tiempo entre clicks, se puede hacer click
                //cada 0.7s haciendo asi que no se ejecute accidentalmente mas de un click a la vez.
                //Ademas si se esta moviendo la figura no se podra hacer click para evitar conflictos.
                if(frame.timestamp() - tiempoSeleccionar >= 700000 && movPulsado == false){
                    //Si hay 4 dedos se hara un click, es decir, juntando cualquier dedo de la palma de la mano se
                    //ejecutara un click
                    if(hand.fingers().count() == 4){
                        //System.out.println("ClickIzquierdo."); //Debug comando
                        raton.seleccionar();
                        tiempoSeleccionar = frame.timestamp();
                    }
                }
                
                //Movimiento 
                //Si se cierra la mano es decir no hay ningun dedo entonces se procedera a desplazar la figura.
                //Si el movimiento ya esta activo no se volvera a presionar las teclas para evitar conflictos.
                if(movPulsado == false && hand.fingers().count() == 0){
                    //System.out.println("Movimiento."); //Debug comando
                    raton.pressMovimiento();
                    movPulsado = true;
                //Si hay algun dedo o mas se procedera una cuenta de 0.5s si durante este tiempo se han detectado dedos
                //entonces se liberaran las teclas y se terminara el gesto.
                }else if (movPulsado == true && hand.fingers().count() != 0){
                    if(movContando == false){
                        tiempoMovimiento = frame.timestamp();
                        movContando = true;
                    }else if(frame.timestamp() - tiempoMovimiento >= 500000){
                        raton.releaseMovimiento();
                        movPulsado = false;
                        movContando = false;
                    }
                }else if(hand.fingers().count() == 0){
                        movContando = false;
                }
      
                //  Zoom
                //Si hay 2 o 3 dedos detectados ademas si ha pasado un periodo de 0.3s entre scrolls y no esta
                //el movimiento de la figura 3d activo se procedera a utilizar el zoom.
                
                //Gracias al tiempo de 0.5s entre movimientos del scroll hacia abajo o arriba se consigue que no
                //se hagan mas scrolls de los necesarios y el usuario pueda controlar de una forma mas precisa la
                //cantidad de movimientos necesarios. Sin esta condicion ya que hay muchos frames por segundo cuando
                //se hiciera scroll se subiria o bajaria de forma incontrolada en cuestion de microsegundos.
                if(frame.timestamp() - tiempoZoom >= 500000 && movPulsado == false){
                    if(hand.fingers().count() == 2 || hand.fingers().count() == 3){
                        // System.out.println("Zoom.");//debug comando
                        //Si hay dos dedos o tres se activara el zoom, ahora solo habra que esperar 1s para
                        //utilizarlo, para el usuario no es nada para el algoritmo supone no crear conflicto con
                        //los otros gestos.
                        if(zoomActivo == false){
                            zoomActivo = true;
                            tiempoZoom2 = frame.timestamp();
                        }
                               
                        if(frame.timestamp() - tiempoZoom2 >= 1000000){
                            //Dentro de la interactionBox creada por leap motion se obtiene el centro, si la mano
                            //se encuentra en el rango superior se hace un scroll ascendente y en el caso contrario
                            //si la mano esta en el rango inferior se hace un scroll de forma descendente.
                            if(hand.palmPosition().getY() >= frame.interactionBox().center().getY()){
                                raton.zoomCamera((int)(-2 * y));
                            }else if(hand.palmPosition().getY() < frame.interactionBox().center().getY()){
                                raton.zoomCamera((int)(2 * (1 - y)));
                            }
                            
                            tiempoZoom = frame.timestamp(); 
                       }
                    }else{
                        zoomActivo = false;
                    }
                }
            }
        }
    }
}