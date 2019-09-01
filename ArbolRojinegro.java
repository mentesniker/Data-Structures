package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            this.color=Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
            String s = null;
            if(this.color == Color.NEGRO)
                s = String.format("N{%s}",super.toString());
            else{s = String.format("R{%s}",super.toString());}
            return s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)o;
            return color == vertice.color && super.equals(o);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        // Aquí va su código.
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice==null){
        	return Color.NEGRO;
        }
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        /*if(getClass() != vertice.getClass())
            throw new ClassCastException("");*/
        return v.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
    super.agrega(elemento);
        VerticeRojinegro b = (VerticeRojinegro)super.getUltimoVerticeAgregado();
        b.color = Color.ROJO;
        if(b != null)
            rebalanceadora(b);
        return;
    }
    private void rebalanceadora(VerticeRojinegro v){
        if(!v.hayPadre()){
            v.color = Color.NEGRO;
            return;
        }
        VerticeRojinegro p = (VerticeRojinegro) v.padre;
        if(p.color == Color.NEGRO)
            return;
        if(hayTio(v)){
            VerticeRojinegro a = (VerticeRojinegro)p.padre;
            VerticeRojinegro t = tio(v);
            if(t.color == Color.ROJO){
                p.color = Color.NEGRO;
                t.color = Color.NEGRO;
                a.color = Color.ROJO;
                rebalanceadora(a);
                return;
            }
        }
        VerticeRojinegro a = (VerticeRojinegro) p.padre;
        if(v.padre != raiz){
            if(a.izquierdo == p && p.derecho == v){
                super.giraIzquierda(p);
                VerticeRojinegro aux = v;
                v = p;
                p = aux;
            }
            if(a.derecho == p && p.izquierdo == v){
                super.giraDerecha(p);
                VerticeRojinegro aux = v;
                v = p;
                p = aux;
            }
        }
        p.color = Color.NEGRO;
        a.color = Color.ROJO;
        if(p.izquierdo == v)
            super.giraDerecha(a);
        else
            super.giraIzquierda(a);
        return;
    }
    private boolean hayTio(VerticeRojinegro v){
        if(!v.hayPadre())
            return false;
        VerticeRojinegro p = (VerticeRojinegro) v.padre;
        if(!p.hayPadre())
            return false;
        VerticeRojinegro a = (VerticeRojinegro) p.padre;
        if(a.izquierdo == p){
            if(a.hayDerecho())
                return true;
            return false;
        }
        if(a.derecho == p){
            if(a.hayIzquierdo())
                return true;
            return false;
        }
        return false;
    }
    private VerticeRojinegro tio(VerticeRojinegro v){
        if(hayTio(v)){
            VerticeRojinegro p = (VerticeRojinegro) v.padre;
            VerticeRojinegro a = (VerticeRojinegro) p.padre;
            if(a.izquierdo == p){
                VerticeRojinegro t = (VerticeRojinegro) a.derecho;
                return t;
            }
            VerticeRojinegro t = (VerticeRojinegro) a.izquierdo;
            return t;
        }
        return null;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
