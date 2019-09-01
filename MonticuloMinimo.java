package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(indice >= elementos)
                throw new NoSuchElementException("");
            T e = arbol[indice];
            indice++;
            return e;
        }
    }

    /* Clase estática privada para poder implementar HeapSort. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un indexable con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return this.elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)}, pero se ofrece este constructor por
     * completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        arbol = nuevoArreglo(200);
        elementos = 0;
    }
    public MonticuloMinimo(MonticuloMinimo<T> m,int n) {
        // Aquí va su código.
        arbol = nuevoArreglo(n);
        for(T e : m){
            agrega(e);
        }
    }

    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *              montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        // Aquí va su código.
        int n = coleccion.getElementos();
        arbol = nuevoArreglo(n);
        int i = 0;
        for(T elemento : coleccion){
            arbol[i] = elemento;
            i++;
        }
        n = (n/2) - 1;
        while(n >= 0){
            acomodaAbajo(arbol,n);
            n--;
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
    	if(elementos ==  arbol.length){
    		T[] arbolito = nuevoArreglo(2*(arbol.length));
    		int i = 0;
    		while(i<=elementos){
    			arbolito[i] = arbol[i];
    			i++;
    		}
    		arbol = arbolito;
    	}
    	arbol[elementos] = elemento;
    	arbol[elementos].setIndice(elementos);
    	elementos++;
    	reordena(elemento);	
    }


    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
        // Aquí va su código.
        if(elementos <= 0)
        	throw new IllegalStateException("elimina 1");
        T e = arbol[0];
        intercambialos(arbol,0,elementos-1);
        elementos--;
        arbol[elementos] = null;
        acomodaAbajo(arbol,0);//desde la raiz
        return e;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elementos <= 0)
        	return;
        int i = dimeElIndice(elemento);
        if(i > elementos)
            return;
        if(i < 0)
        	return;
        intercambialos(arbol,i,elementos-1);
        elementos--;
        acomodaArriba(arbol,elementos-1);//el ultimo elemento del arreglo
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        int i = dimeElIndice(elemento);
        if(i < 0 || i >= elementos)
            return false;
        return true;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacio() {
        // Aquí va su código.
        return elementos <= 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        elementos = 0;
        for(int i =0;i <= elementos;i++){
        	arbol[i] = null;
        }
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    public void reordena(T elemento) {
        // Aquí va su código.
        int i = dimeElIndice(elemento);
        if(padreMayorHijo(i))
            acomodaArriba(arbol,i);
        else if(verticeMayorHijoI(i) || verticeMayorHijoD(i))
            acomodaAbajo(arbol,i);
        return;
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
        // Aquí va su código.
        if( i < 0 || i >= elementos)
            throw new NoSuchElementException("get");
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        int i = 0;
        String s = "";
        while(i < elementos){
        	s += String.format("%s, ",arbol[i]);
        	i++;
    	}
    	return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param o el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        // Aquí va su código.
        if (!(o instanceof MonticuloMinimo))
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> mn = (MonticuloMinimo<T>)o;
        // Aquí va su código.
        if(this.getElementos() != mn.getElementos())
        	return false;
        int i = 0;
        for(T e: this){
        	if(!e.equals(mn.get(i)))
        		return false;
        	i++;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
    	Lista<Adaptador<T>> l = new Lista<Adaptador<T>>();
    	for(T t:coleccion){
    		l.agregaFinal(new Adaptador<T>(t));
    	}
    	Lista<T> h = new Lista<T>();
    	MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(l);
    	while(!monticulo.esVacio()){
    		h.agregaFinal(monticulo.elimina().elemento);
    	}
    	return h;
    }
    //Metdos privados
    private int dimeElIndice(T e){
        Adaptador<T> ad = new Adaptador<T>(e);
        return ad.getIndice();
    }
    private void acomodaArriba(T[] a,int h){
        while(padreMenorHijo(h)){
        	int padre = (int)((h-1)/2);
            intercambialos(a,h,padre);
            int aux = h;
            h = padre;
            padre = h;
        }
    }
    //puede fallar
    private void acomodaAbajo(T[] a,int v){
    	while(verticeMenorHijoI(v) || verticeMenorHijoD(v)){
    		if(hayIzquierdo(v) && !hayDerecho(v)){
    			int hi = 2*v+1;
    			intercambialos(a,v,hi);
    			int aux = hi;
    			hi = v;//puede ser alrevez, si falla,cambiar
    			v = aux;
    		}else if(!hayIzquierdo(v) && hayDerecho(v)){
    			int hd = 2*v+2;
    			intercambialos(a,v,hd);
    			int aux = hd;
    			hd = v;
    			v = aux;
    		}else{
    			int hi = 2*v+1;
    			int hd = 2*v+2;
    			if(a[hi].compareTo(a[hd]) < 0){
    				intercambialos(a,v,hi);
    				int aux = hi;
    				hi = v;
    				v = aux;
    			}else{
    				intercambialos(a,v,hd);
    				int aux = hd;
    				hd = v;
    				v = aux;
    			}
    		}
    	}
    }
    private boolean hayIzquierdo(int v){
    	if(v < 0)
    		return false;
    	if(v == elementos)
    		return false;
    	int hi = 2*v + 1;
    	return hi <= elementos;
    }
    private boolean hayDerecho(int v){
    	if(v < 0)
    		return false;
    	if(v == elementos)
    		return false;
    	int hd = 2*v + 1;
    	return hd <= elementos;
    }
    //puede fallar
    private boolean hayPadre(int j){
    	if(j < 0)
            return false;
        if(j > elementos)
        	return false;
        int p = (int)((j-1)/2);
        return p >= 0;
    }
    private boolean verticeMenorHijoD(int v){
    	if(!hayDerecho(v))
    		return false;
    	int hd = 2*v+2;
    	if(hd > elementos)
            return false;
    	return arbol[v].compareTo(arbol[hd]) < 0; 	
    }
    private boolean verticeMenorHijoI(int v){
    	if(!hayIzquierdo(v))
    		return false;
    	int hi = 2*v + 1;
    	if(hi > elementos)
    		return false;
    	return arbol[v].compareTo(arbol[hi]) < 0;
    }
    private boolean padreMenorHijo(int v){
    	if(v < 0)
    		return false;
    	if(v > elementos)
    		return false;
        int p = (int)((v-1)/2);
        if(p < 0)
            return false;
        if(arbol[v].compareTo(arbol[p])>0)
            return true;
        return false;
    }
    private boolean padreMayorHijo(int s){
    	if(s < 0)
    		return false;
    	if(s > elementos)
    		return false;
        int p = (int)((s-1)/2);
        if(p < 0)
        	return false;
        if(arbol[s].compareTo(arbol[p])<0)
            return true;
        return false;   
    }
    private boolean verticeMayorHijoD(int h){
    	if(!hayDerecho(h))
    		return false;
        int hd = 2*h + 2;
        if(hd > elementos)
            return false;
        return arbol[h].compareTo(arbol[hd]) > 0; 
    }
    private boolean verticeMayorHijoI(int v){
    	if(!hayIzquierdo(v))
    		return false;
    	int hi = 2*v + 1;
    	if(hi > elementos)
    		return false;
    	return arbol[v].compareTo(arbol[hi]) > 0;
    }
    private void intercambialos(T[] a, int i, int d ){
    	T e = a[i];
    	a[i] = a[d];
    	a[d] = e;
    }
}
