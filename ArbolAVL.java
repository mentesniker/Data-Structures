package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            int balance = balance(this);
            //String s = String.format("%s",elemento);
            String s = " ";
            s += this.altura;
            s += "/";
            s += balance;
            return super.toString()+s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            // Aquí va su código.
            return altura == vertice.altura && super.equals(o);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
        // Aquí va su código.
        super();
    }
    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        // Aquí va su código.
        for(T t:coleccion){
            this.agrega(t);
        }
    }
    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
    	VerticeAVL u = (VerticeAVL) super.ultimoAgregado;
    	rebalanceAVL(u);
    }
    private void rebalanceAVL(VerticeAVL v){
    	if(v == null)
    		return; 
    	VerticeAVL p = (VerticeAVL) v.izquierdo;
    	VerticeAVL q = (VerticeAVL) v.derecho;
    	VerticeAVL padre = (VerticeAVL) v.padre;
    	v.altura = 1 + Math.max(alturaVer(p), alturaVer(q));
    	int balance = alturaVer(p) - alturaVer(q);
    	if(balance == -2){
    		if(balance(q) == 1)
    			girarDerecho(q);
    		girarIzquierdo(v);
    	}
    	if(balance == 2){
    		if(balance(p) == -1)
    			girarIzquierdo(p);
    		girarDerecho(v);
    	}
    	rebalanceAVL(padre);
    }
    private int balance(VerticeAVL vertice){
    	if(vertice == null)
    		return 0;
    	VerticeAVL vi = (VerticeAVL) vertice.izquierdo;
    	VerticeAVL vd = (VerticeAVL) vertice.derecho;
    	return alturaVer(vi) - alturaVer(vd);
    }
    private int alturaVer(VerticeAVL vertice){
    	if(vertice == null)
    		return -1;
    	return vertice.altura;
    }
    private void actualizaAltura(VerticeAVL vertice){
    	if(vertice != null)
    		vertice.altura = 1 + Math.max(alturaVer((VerticeAVL)vertice.izquierdo), alturaVer((VerticeAVL)vertice.derecho));
    	return;
    }
    private void girarIzquierdo(VerticeAVL vertice){
    	super.giraIzquierda(vertice);
    	actualizaAltura(vertice);
    	VerticeAVL p = (VerticeAVL) vertice.padre;
    	actualizaAltura(p);
    }
    private void girarDerecho(VerticeAVL vertice){
    	super.giraDerecha(vertice);
    	actualizaAltura(vertice);
    	VerticeAVL q = (VerticeAVL) vertice.padre;
    	actualizaAltura(q);
    }
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeAVL b = (VerticeAVL) super.busca(elemento);
        if(b == null)
        	return;
        if(b.hayIzquierdo() && b.hayDerecho()){
        	Vertice c = super.intercambiaEliminable((Vertice)b);
        	super.eliminaVertice(c);
        	rebalanceAVL((VerticeAVL)c.padre);
        }
        else{
        	super.eliminaVertice((Vertice)b);
        	rebalanceAVL((VerticeAVL)b.padre);
        }
        elementos--;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}