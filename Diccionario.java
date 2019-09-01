package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo..
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase para las entradas del diccionario. */
    private class Entrada{
        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor){
            // Aquí va su código.
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase privada para iteradores de diccionarios. */
    private class Iterador{
    	
        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            // Aquí va su código.
            if(elementos == 0){
                iterador = null;
				return;
            }
            indice = 0;
            recorreArreglo();
            if(entradas[indice] == null)
            	iterador = null;
            else{
            	iterador = entradas[indice].iterator();
            } 
        }
        //Metodo auxiliar que recorre el arreglo de listas de entradas hasta 
        //que encuentre una lista no nula o hasta que se acabe el arreglo
        private void recorreArreglo(){
        	while(indice < entradas.length && entradas[indice] == null)
        		indice++;
        }
        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext(){
            // Aquí va su código.
            return iterador != null;
        }
        //Metodo auxiliar que regresa si el iterador tiene un siguiente elemento
        //Si el iterador no tiene un siguiente elemento busca la siguiente lista
        //no nula para iterarla,si ya no hay anula el iterador y regreasa falso
        private boolean miNext(){
        	if(!iterador.hasNext()){
				indice++;
            	recorreArreglo();
            	if(indice < entradas.length && entradas[indice] != null)
            		iterador = entradas[indice].iterator();
                else{
                    iterador = null;
                    return false;
                }
            }
            return iterador.hasNext();
        }
        /* Regresa la siguiente entrada. */
        public Entrada siguiente(){
            // Aquí va su código.
            if(iterador == null)
            	throw new NoSuchElementException("");
			Entrada k = iterador.next();
			miNext();
        	return k;
        }
    }

    /* Clase privada para iteradores de llaves de diccionarios. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Construye un nuevo iterador de llaves del diccionario. */
        public IteradorLlaves(){
            // Aquí va su código.
        	super();
        }

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
            return super.siguiente().llave;
        }
    }

    /* Clase privada para iteradores de valores de diccionarios. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Construye un nuevo iterador de llaves del diccionario. */
        public IteradorValores() {
            // Aquí va su código.
        	super();    
        }

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
        	//return super().next().valor;
        	return super.siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores*/
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])
            Array.newInstance(new Lista().getClass(), n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        // Aquí va su código.
        this(MINIMA_CAPACIDAD,((K llave ) -> llave.hashCode()));
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        // Aquí va su código.
        this(capacidad,((K llave ) -> llave.hashCode()));
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        // Aquí va su código.
        this(MINIMA_CAPACIDAD,dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor){
        // Aquí va su código.
        if(capacidad < MINIMA_CAPACIDAD)
        	this.entradas = nuevoArreglo(MINIMA_CAPACIDAD);
        else{
        	int i = 2;
        	while(i < capacidad*2){
        		i = i << 1;
        	}
        	this.entradas = nuevoArreglo(i);
    	}
        elementos = 0;
        this.dispersor = dispersor;
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor){
        // Aquí va su código.
        if(llave == null)
        	throw new IllegalArgumentException("");
        if(valor == null)
            throw new IllegalArgumentException("");
        int i = aplicaMascara(llave);
        if(entradas[i] == null){
        	Lista<Entrada> l = new Lista<Entrada>();
        	Entrada e = new Entrada(llave,valor);
        	l.agregaFinal(e);
        	entradas[i] = l;
            elementos++;
        }
        else{
        	Lista<Entrada> l = entradas[i];
        	for(Entrada f : l){
        		if(f.llave.equals(llave)){
        			f.valor = valor;
        			f.llave = llave;
        			return;
        		}
        	}
        	Entrada nueva = new Entrada(llave,valor);
        	l.agregaFinal(nueva);
            elementos++;
        }
    	if(carga() >= MAXIMA_CARGA)
    		creceArreglo();
    }

    private void creceArreglo(){
    	Lista<Entrada>[] aux = entradas;
    	entradas = nuevoArreglo(entradas.length*2);
    	elementos = 0;
    	for(Lista<Entrada> l : aux){
    		if(l == null)
    			continue;
    		for(Entrada e : l){
    			agrega(e.llave,e.valor);
    		}
    	}
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave){
        // Aquí va su código.
        if(llave == null)
        	throw new IllegalArgumentException("");
        int i = aplicaMascara(llave);
        if(entradas[i]==null)
        	throw new NoSuchElementException("");
        return busca(llave,i);
    }

    private V busca(K llave,int i){
    	Lista<Entrada> l = entradas[i];
    	for(Entrada e : l){
    		if(llave.equals(e.llave))
    			return e.valor;
    	}
    	throw new NoSuchElementException("");
    }

    private int aplicaMascara(K i){
    	int j = dispersor.dispersa(i);
    	int mascara = entradas.length - 1;
    	j = j & mascara;
    	return j;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        if(llave == null)
        	return false;
        int i = aplicaMascara(llave);
        if(entradas[i]==null)
        	return false;
        return buscaBoolean(llave,i);   
    }

    private boolean buscaBoolean(K llave,int i){
    	Lista<Entrada> l = entradas[i];
    	for(Entrada e : l){
    		if(llave.equals(e.llave))
    			return true;
    	}
    	return false;
    }	

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if(llave == null)
        	throw new IllegalArgumentException("");
        int i = aplicaMascara(llave);
        if(entradas[i]==null)
        	throw new NoSuchElementException("");
        if(!contiene(llave))
            throw new NoSuchElementException("");
        Lista<Entrada> l = entradas[i];
        for(Entrada x : l){
        	if(llave.equals(x.llave)){
        		l.elimina(x);
        		elementos--;
                return;
        	}
        }
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int i = 0;
        for(Lista<Entrada> l : entradas){
        	if(l != null){
        		i += l.getElementos()-1;
        	}
        }
        return i;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        int max = 0;
        for(Lista<Entrada> l : entradas){
        	if(l != null){
        		max = l.getElementos();
        		if(max < l.getElementos()){
        			max = l.getElementos();
        		}
        	}
        }
        return max - 1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
        return (double)elementos/entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacio() {
        // Aquí va su código.
        return elementos <= 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        entradas = nuevoArreglo(entradas.length);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(elementos == 0)
            return "{}";
        String s = "{ ";
        for(Lista<Entrada> l : entradas){
            if(l != null){
        	   for(Entrada e : l){
        		  s += String.format("'%d': '%d', ", e.valor, e.llave);
        	   }
            }
        }
        return s + "}";
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        // Aquí va su código.
    	if(!(o instanceof Diccionario))
            return false;
        @SuppressWarnings("unchecked") Diccionario<K,V> diccionario = (Diccionario<K,V>) o;
        if(this.elementos != diccionario.getElementos())
        	return false;
        /*for(Lista<Entrada> l : entradas){
            if(l != null){
                for(Entrada e : l){
                    if(e.llave.equals())
                }
            }
        }*/
        return false;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
