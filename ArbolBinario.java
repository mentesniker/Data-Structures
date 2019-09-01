package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento=elemento;
            padre=derecho=izquierdo=null;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
            // Aquí va su código.
            return !(padre==null);
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            // Aquí va su código.
            return !(izquierdo==null);
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
            // Aquí va su código.
            return !(derecho==null);
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            // Aquí va su código.
            if(!hayPadre())
            	throw new NoSuchElementException("No hay padre");
            return padre; 
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            // Aquí va su código.
            if(!(hayIzquierdo()))
            	throw new NoSuchElementException("No hay vertice izquierdo");
            return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            // Aquí va su código.
            if(!(hayDerecho()))
            	throw new NoSuchElementException("No hay vertice derecho");
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
        	return altura(this);
        }
        private int altura(Vertice v){
        	if(v==null)
        		return -1;
        	return (1 + Math.max(altura(v.izquierdo),altura(v.derecho)));
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            // Aquí va su código.
        	return profundidad(this);
        }
        private int profundidad(Vertice v){
            int i = 0;
            Vertice vp = v.padre;
            while(vp != null){
                i++;
                vp = vp.padre;
            }
            return i;
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)o;
            if(vertice == null)
                return false;
            if(!(this.elemento.equals(vertice.elemento)))
                return false;
            if(this.elemento.equals(vertice.elemento)){
                if(this.izquierdo != null && this.derecho != null){
                    return this.izquierdo.equals(vertice.izquierdo) && this.derecho.equals(vertice.derecho);
                }
                if(this.izquierdo != null && vertice.derecho == null){
                    return this.izquierdo.equals(vertice.izquierdo);
                }
                if(this.derecho != null && vertice.izquierdo == null){
                    return this.derecho.equals(vertice.derecho);
                }
            }
            return vertice.izquierdo == null && vertice.derecho == null;
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            // Aquí va su código.
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        // Aquí va su código.
        for(T t:coleccion){
        	this.agrega(t);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        // Aquí va su código.
        return altura(raiz);
        }
        private int altura(Vertice v){
        	if(v==null)
        		return -1;
        	return 1 + Math.max(altura(v.izquierdo),altura(v.derecho));
        }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if(raiz==null)
            return false;
        else if(raiz.elemento.equals(elemento))
            return true;
        Cola<Vertice> c = new Cola<Vertice>();
        c.mete(raiz);
        return contiene(c,elemento);
    }
    private boolean contiene(Cola<ArbolBinario<T>.Vertice> cc, T e){
        if(cc.esVacia())
            return false;
        Vertice v = cc.saca();
        if(v == null){
            return contiene(cc,e);
        }
        if(v.izquierdo!=null){
            if(v.izquierdo.elemento.equals(e))
                return true;
            cc.mete(v.izquierdo);
        }
        if(v.derecho!=null){
            if(v.derecho.elemento.equals(e))
                return true;
            cc.mete(v.derecho);
        }
        return contiene(cc,e);
        }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        if(elemento==null)
        	return null;
        if(raiz==null)
        	return null;
        else if(raiz.elemento.equals(elemento))
        	return raiz;
        Cola<Vertice> c = new Cola<Vertice>();
        c.mete(raiz);
        return max(c,elemento);
    }
    private Vertice max(Cola<Vertice> cc, T e){
    	if(cc.esVacia())
    		return null;
    	Vertice v = cc.saca();
        if(v == null){
            return max(cc,e);
        }
    	if(v.izquierdo!=null){
    		if(v.izquierdo.elemento.equals(e))
    			return v.izquierdo;
    		cc.mete(v.izquierdo);
    	}
    	if(v.derecho!=null){
    		if(v.derecho.elemento.equals(e))
    			return v.derecho;
    		cc.mete(v.derecho);
    	}
    	return max(cc,e);
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        // Aquí va su código.
        if(raiz==null)
        	throw new NoSuchElementException("El arbol es vacio");
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        // Aquí va su código.
        return raiz==null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        raiz=null;
        elementos=0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)o;
        // Aquí va su código.
            if(arbol == null)
            	return false;
            if(! this.esVacio())
            	return raiz.equals(arbol.raiz);
            return this.esVacio() && arbol.esVacio();
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(raiz == null)
        	return "";
        int[] arreglo = new int[altura()+1];
        for(int i = 0;i < altura()+1;i++){
        	arreglo[i] = 0;
        }
        return toString(raiz,0,arreglo);
    }
    private String toString(Vertice v,int nivel,int[] a){
    	if(v == null)
    		return "";
    	String s = v.toString() + "\n";
    	a[nivel] = 1;
    	if(v.izquierdo != null && v.derecho != null){
    		s = s + espacios(nivel,a);
    		s = s + "├─›";
    		s = s + toString(v.izquierdo,nivel + 1,a);
    		s = s + espacios(nivel,a);
    		s = s + "└─»";
    		a[nivel] = 0;
    		s = s + toString(v.derecho,nivel +1 , a);
    	}else if(v.izquierdo != null){
    		s = s + espacios(nivel,a);
    		s = s + "└─›";
    		a[nivel] = 0;
    		s = s + toString(v.izquierdo,nivel + 1,a);
    	}else if(v.derecho != null){
    		s = s + espacios(nivel,a);
    		s = s + "└─»";
    		a[nivel] = 0;
    		s = s + toString(v.derecho, nivel + 1, a);
    	}
    	return s;
    }
    private String espacios(int nivel,int[] arreglo){
    	String s = "";
    	int i = 0;
    	while(i < nivel){
    		if(arreglo[i] == 1)
    			s = s + "│  ";
    		else
    			s = s + "   ";
    		i++;
    	}
    	return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
