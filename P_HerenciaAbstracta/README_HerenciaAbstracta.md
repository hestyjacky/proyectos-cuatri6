# Manual - Herencia Abstracta

_Por medio de este manual se presenta una práctica paso por paso, acompañado por explicaciones breves diseñadas para proporcionar un mejor entendimiento de los conceptos básicos relacionados con la herencia abstracta en la programación orientada a objetos._

# Práctica
Dentro de esta práctica se realizará una clase abstracta que sera la base para definir la estructura de una **figura** geométrica, para después, por medio de la herencia, crear otras clases concretas que representen las características escenciales de un **círculo** y un **rectángulo**.

### Desarrollar la clase _Figura_
Como primeras línea de código se debe encontrar lo siguiente:
```
    public class Figura {
    }
```
Para poder convertir esta clase como abstracta se debe colocar la palabra _**abstract**_ al inicio, sustituyendo la palabra _**public**_.

Posteriormente hay que declarar un método que debe ser implementado por las subclases concretas que heredan de Figura, esta va a ser _**calcularArea()**_, la cual unicamente nos define que es para calcular el área, pero que varía dependiendo de cada figura. 

Para declararla se debe colocar en el siguiente orden:
- _**public**_: Indica que el método es accesible desde cualquier parte del programa.
- _**abstract**_: Indica que el método es abstracto y se espera que las subclases concretas proporcionen la implementación.
- _**double**_: Es el tipo de dato que retorna este método que se usa para representar números flotantes.
- _**calcularArea()**_: Es el nombre que le vamos a asignar correspondiente a su función principal.

### Desarrollar la clase _Circulo_
De la misma manera, hay que modificar la primera línea de código de la siguiente manera:
- Se debe quitar _**public**_, dejando unicamente _class Circulo_. 
- Posteriormente colocando _**extends**_ seguido del nombre de la clase la cual hereda, en este caso, la clase _**Figura**_.

Luego en la siguiente línea hay que declarar el atributo de un circulo, _**radio**_:
- _**private**_: Se debe colocar al inicio porque es un atributo que unicamente es accesible dentro de la misma clase en la que está definido. 
- _**double**_ : Es el tipo de dato.
- _**radio**_: Es el nombre del atributo.

Pasamos a hacer el constructor de la clase colocando:
- _**public**_: Un método especial que se llama cuando se crea una nueva instancia de la clase _**Circulo**_. Este debe recibir un parámetro.
Ahí mismo en parentesis se debe colocar:
- _**double**_: Es el tipo de dato del parámetro.
- _**radio**_: Es la variable que se debe colocar como el parámetro que se espera.

Dentro de este debe tener:
- _**this.radio**_: Este llama a la variable _**radio**_ de la instancia antes generada, siendo este el parámetro que se le manda al constructor. Es en esta parte que el valor de esta variable se le asigna a un atributo dentro de la instancia actual. Se visualizaría de la siguiente manera:

```
    this.radio = radio;
```

Finalmente pasamos a la implementación del método abstracto para calcular el área en el caso de un círculo:
- _***@Override***_: Es una anotación dentro de java que se utiliza para indicar que _**calcularArea()**_ esta siendo sobrescrita en el método de la clase de donde la heredó.
- _**public double calcularArea()**_: Es aqui que se realiza una implementacion concreta dentro de un método abstracto.
- _**return**_: Es una palabra clave dentro de la programacion para devolver un valor desde un método, en este caso el resultado de una operación.
- _**Math.PI**_: Es una constante que representa el valor de π (pi).
- _**radio * radio**_: Se debe colocar después de Math.PI, separadas por un *, simbolizando esta expresión cuando un valor se eleva al cuadrado.

La operación se vería de la siguiente manera:
```
    return Math.PI * radio * radio;
```

### Desarrollar la clase _Rectangulo_
Así como la clase que realizamos anteriormente, hay que modificar la primera línea de código de la misma forma, quitando _**public**_, dejando el nombre la clase actual  _**Rectangulo**_ ,colocando _**extends**_ seguido de la clase _**Figura**_.

Luego hay que declarar los atributos de un rectángulo: 

1. Atributo _**base**_
- _**private**_: Ya que es un atributo que unicamente es accesible dentro de la misma clase.
-  _**double**_: Es el tipo de dato para manejar decimales.
- _**base**_: Es el nombre del atributo.

2. Atributo _**altura**_
- _**private**_: De igual manera que el atributo anterior, es unicamente accesible dentro de la misma clase.
-  _**double**_: Es el tipo de dato.
- _**altura**_: Es el nombre del atributo.

Pasamos a hacer el constructor de la clase colocando:
- _**public**_: Un método especial que se llama cuando se crea una nueva instancia de la clase _**Rectángulo**_. Este debe recibir dos parámetro.

Ahí mismo en parentesis se debe colocar:
- _**double**_: Es el tipo de dato del primer parámetro.
- _**base**_: Es la variable que se debe colocar como el primer parámetro.
- Se debe colocar una _**coma**_ por cada parametro indicado.
- _**double**_: Es el tipo de dato del segundo parámetro.
- _**altura**_: Es la variable que se debe colocar como el primer parámetro.

Dentro de este debe tener:
- _**this.base**_: Este llama a la variable _**base**_ de la instancia, donde el valor de esta variable se le asigna a un atributo dentro de la instancia actual. Se visualizaría de la siguiente manera:
```
    this.base = base;
```
- _**this.altura**_: al igual que el paso anterior, hay que almacenar el valor dentro de la instancia actual _**altura**_.

Finalmente pasamos a la implementación del método abstracto para calcular el área en el caso de un rectángulo:
- _***@Override***_: Al igual que en la clase anterior, una anotación dentro de java que indica que _**calcularArea()**_ esta siendo sobrescrita.
- _**public double calcularArea()**_: Es aqui que se realiza la implementación concreta.
- _**return**_: Utilizada para devolver el resultado de una operación.
- _**base**_: Como parte de la operación a realizar.
- _**altura**_: Se coloca despues de haber colocado un *, simbolizando la multiplicación del área del rectangulo "base X altura"

### Desarrollar la clase _App_
Una vez abriendo esta clase debe aparecer el siguiente código:
```
public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

Para que el código que realizamos en las clases anteriores, debe modificarse el codigo anterior de la siguiente manera:
- A partir de la tercera línea se debe colocar la clase abstracta que se codifico anteriormente como base para diferentes tipos de figuras geométricas, en este caso _**Figura**_.
- Luego debemos asignarle una variable, _**rectangulo**_, que se está utilizando para hacer referencia a la instancia que se creará. Esta variable se declara con el tipo Figura, lo que significa que puede contener instancias de clases que sean subclases de Figura.
- Separadas por un = , se debe colocar la palabra clave _**new**_, que se utiliza en Java para crear una nueva instancia de una clase, apartando un espacio en memoria para almacenar un objeto de dicha clase, así utilizando el constructor y almacenar su valor.
- Colocando después el nombre de la subclase _**Rectangulo**_ la cual ya representa la figura que deseamos trabajar.
- Entre parentesis, se deben proporcionar los parámetros que anteriormente fueron definidos _***(base, altura)***_, que pasan al constructor para ser almacenados. Se visualizaría de la siguiente manera:
```
    new Rectangulo(5, 10);
```

Para poder mostrar los resultados en terminal con respecto a los valores que fueron ingresados como parámetros se debe utilizar la siguiente línea:
```
    System.out.println( );
```

Dentro de los parentesis de la línea anterior se coloca una cadena de texto que representa un mensaje descriptivo que será mostrado en terminal por medio de comillas "".
```
    ("Rectángulo - Área: ")
```

Posteriormente, de igual manera dentro de los parentesis, se debe colocar un + , se utiliza en este contexto para concatenar (unir) dos cadenas de texto. En este caso, se está concatenando la cadena de texto anterior con el valor del área que sacaremos de la siguiente manera:
- _**rectangulo**_: Esta es una variable que hace referencia a un objeto de tipo Figura (o una subclase de Figura, como Rectangulo).
- _***.calcularArea()***_: Esto es una llamada al método calcularArea() en el objeto rectangulo. El punto representando el acceso a los métodos y atributos de un objeto.

En otras palabras, es una forma de obtener el área de la figura geométrica representada por el objeto, utilizando el método _**calcularArea()**_ implementado en la subclase correspondiente. Se visualizaría de la siguiente manera:
```
    rectangulo.calcularArea()
```

Finalmente, todos los pasos que se realizaron en esta clase se deben repetir si se desea conocer el área correspondiente a la clase _**Circulo**_


* [Solución de la Práctica](https://drive.google.com/drive/folders/16PAqS7_9t-pt0DdYaGrXOFo8HZupCv2p?usp=sharing)

# Conceptos clave dentro de esta práctica

### ¿Qué significa la palabra "abstracta"?

La palabra "abstracta" se utiliza para describir algo que no tiene una representación concreta o física en sí mismo, sino que representa una idea, un concepto o una abstracción. 

En el contexto de la programación y la orientación a objetos, el término se refiere a una clase o método que está **declarado pero no está completamente implementado en sí mismo**, dejando ciertos detalles para que las clases derivadas o subclases proporcionen implementaciones específicas.

En pocas palabras, en el contexto de la programación orientada a objetos, se aplica a clases y métodos que actúan como **plantillas o estructuras generales que requieren implementación adicional** por parte de las clases derivadas.

### ¿Qué es una clase abstracta?
- Una clase abstracta es una clase que no se puede instanciar directamente, es decir, no se pueden crear objetos de ella. 
- Su principal propósito es proporcionar una estructura base común para otras clases que la heredan (subclases). 
- Puede contener tanto métodos concretos (métodos con implementación) como métodos abstractos (métodos sin implementación).
- Las subclases que heredan de la clase abstracta **deben proporcionar implementaciones para los métodos abstractos.**
- Se declara utilizando la palabra clave _abstract_ antes de la palabra clave _class_.
```
    abstract class Ejemplo1
```

### ¿Qué es una método abstracto?
- Un método abstracto es un método declarado en una clase abstracta que no tiene una implementación concreta en la clase donde se declara. 
- Las subclases que heredan de la clase abstracta **deben implementar los métodos abstractos, proporcionando las implementaciones específicas.**
- Sirve como una forma de asegurar que las subclases proporcionen una implementación específica del método en función de sus necesidades.
- No tiene un cuerpo de método y se termina con un punto y coma después de su firma.

```
    public abstract Ejemplo2();
```

### ¿Qué significa la palabra "herencia"?
La herencia es un concepto fundamental en la programación orientada a objetos que permite la creación de nuevas clases basadas en clases existentes. En términos simples, **la herencia permite que una clase adquiera las propiedades (atributos y métodos) de otra clase**, lo que facilita la reutilización de código y la creación de jerarquías de clases.

Por ejemplo, la clase base podría ser **Vehiculo**, con atributos y métodos que tienen todos los vehículos, mientras que las clases que heredan de esta serían un **automóvil, motocicleta, camión, entre otros,** cada uno con atributos y métodos específicos de su tipo de vehículo. Se vería de esta manera:

```
    public class Motocicleta extends Vehiculo{
    }
```

## **En resumen**
- Una clase abstracta es una clase que no se puede instanciar y que puede contener tanto métodos concretos como métodos abstractos. 
- Un método abstracto es un método declarado en una clase abstracta que no tiene una implementación y debe ser proporcionado en las subclases. 
- La herencia es un mecanismo que permite modelar y organizar relaciones entre clases de manera eficiente, fomentando la reutilización de código y la creación de una estructura modular en la programación.

Estos conceptos son fundamentales en la programación orientada a objetos para establecer una jerarquía de clases y proporcionar una estructura base para la implementación de clases derivadas evitando la reutilización de código.
