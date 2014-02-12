LeapMotion
==========

Navegacion 3D mediante leapMotion


Introducción:

Utilizando leap motion se ha diseñado un programa para actuando en background pudiendo controlar el ratón y teclado mediante gestos. Principalmente se ha diseñado para utilizarse en entornos 3D como autocad o 3Ds max, pero el objetivo real de esto es demostrar como se puede utilizar leap motion para poder interaccionar con el ordenador sin necesitar ratón o teclado. Como contra se puede decir que esta tarea es bastante cansada, hay que tener la mano alzada y en movimiento en todo momento.

Navegación:

Una vez se inicia el programa leap motion toma el control del ratón y el cursor se actualizara en función de la posición de la mano detectada. La navegación esta diseñada para realizarse con la mano abierta mostrando los cinco dedos.

Gestos:

-Seleccionar, click izquierdo del ratón. Para realizar este gesto tan solo habrá que esconder un dedo ya que se activa cuando hay cuatro dedos, por ejemplo juntar el dedo indice y el dedo corazón u ocultando el pulgar.

-Movimiento (desplazar el objeto seleccionado). Este gesto sera detectado cuando se cierre la mano es decir tiene que detectar que no hay ningún dedo, después de esto tan solo habrá que mover el puño, siempre con la mano cerrada.

-Zoom (utilizar el scroll). Cuando haya dos o tres dedos activos entonces moviendo la mano hacia arriba o abajo con respecto al eje y se realizara el zoom del objeto. Hacia abajo el objeto se aleja y hacia arriba el objeto se acerca.

-Rotar. Haciendo un gesto circular con un solo dedo se activara la rotación, solo entonces se podrá mover la mano libremente para rotar el objeto. Para terminar la rotación habrá que volver a hacer el mismo gesto.


Finalizar aplicación:

Para terminar el programa y liberar el ratón hay que mantener la dos manos sobre el leap motion 2 segundos y que ningún gesto este activo. 
