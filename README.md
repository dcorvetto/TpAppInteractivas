**Uso de Github**

Verificar se tiene git instalado. Instrucciones: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

1- Hacer click en "Fork" y elegir usuario

2- Posicionarse en el directorio donde se desee descargar el repositorio en consola (cmd en windows)

3- En la pantalla inicial de tu repositorio (en la url va a aparecer el nombre de tu usuario), hacer click en  el boton "clone or download" y copiar la url que se indica (si no tenes key ssh, elegir Use https y copiar la url)

4- En la consola ingresar : git clone [url del paso anterior]

5- Indicar por consola: git remote add upstream git@github.com:yair-code/TpAppInteractivas.git
(si falla, probar con git remote add upstream git@github.com:yair-code/TpAppInteractivas.git)

6- Indicar: git remote -v   y comprobar figuren 'origin' y 'upstream'. 'Origin' debe tener la url de tu repositorio y 'upstream' la url del repositorio master.

(si ya hiciste git pull del repositorio master, forkeá el repo y modificá el remote origin con los siguientes pasos:
- git remote set-url origin  [url de tu repo forkeado]
- git remote add upstream git@github.com:yair-code/TpAppInteractivas.git (si antes usaste https usar https://github.com/yair-code/TpAppInteractivas.git)
- git remote -v (verificá origin y upstream)
)


Como subir cambios al master usando Git
-----------------------------------------

Antes de empezar a trabajar en el codigo recuerden siempre hacer un ```git fetch upstream``` para obtener todo lo último del master.

1- Desde consola realizar los siguientes pasos:  (en Windows quizas usen una aplicacion y no quieran usar la consola)

- ```git add .```
- ```git commit -m "comentarios de los cambios..."```
- ```git push origin master ```

2- Ir a github web y verificar si los cambios fueron subidos a TU repositorio (ej https://github.com/**dcorvetto**/TpAppInteractivas) . La url tendra el nombre de su usuario. Veran la leyenda:
"This branch is 1 commit ahead of yair-code:master.:master." (puede ser 1 o más commits diferentes)

3- Desde github web, crear un Pull Request (PR). La opción está al costado debajo del botón "Clone or Download"

4- Una vez creado el PR pueden mergearlo desde allí mismo. Abajo de todo esta el botón "Merge"

5- Una vez que mergean el cambio, verificar que los cambios estan en el repositorio master: https://github.com/yair-code/TpAppInteractivas

6- **Todavía falta bajar los cambios de master (historial de commits realizados) a su fork!!** Para eso realizar por consola los siguientes pasos

- ```git fetch upstream```
- ```git rebase upstream/master```
- ```git push origin master```

El comando ```git status``` les puede ser de utilidad para verificar el estado de su repositorio en cualquier momento.

7- En github web podes verificar que en TU repositorio aparece una leyenda como esta:
"This branch is even with yair-code:master.:master."

De ser así, su repositorio y el master son iguales. :)
