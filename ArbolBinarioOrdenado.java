package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador() {
            // Aquí va su código.
            pila = new Pila<Vertice>();
            Vertice v = raiz;
            while(v!=null){
            	pila.mete(v);
            	v=v.izquierdo;
            }
        }
        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
            Vertice v = pila.saca();
            if(v.derecho != null){
            	Vertice t = v.derecho;
            	while(t!=null){
            		pila.mete(t);
            		t=t.izquierdo;
            	}
            }
            return v.elemento;
        }
    }
    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException(" ");
        Vertice vertice = nuevoVertice(elemento);
        elementos ++;
        if(raiz == null){
            raiz = vertice;
            ultimoAgregado = vertice;
            return;
        }
        else
            agregas(raiz, vertice);
        ultimoAgregado = vertice;
    }

    private void agregas(Vertice x, Vertice y){
        if(x == null)
            return;
        if(x.elemento.compareTo(y.elemento)>0){
            if(!(x.hayIzquierdo())){
                x.izquierdo = y;
                y.padre = x;
                return;
            }
            agregas(x.izquierdo, y);
        }else{
            if(!(x.hayDerecho())){
                x.derecho = y;
                y.padre = x;
                return;
            }
            agregas(x.derecho, y);
        }
        return;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice vAB = abBusca(elemento);
        if(vAB !=null)
        eliminaV(vAB);
    }

    private void eliminaV(Vertice vB)
    {

            if(vB.hayDerecho() && vB.hayIzquierdo()){
                

                Vertice vMax = max(vB.izquierdo);
                vB.elemento = vMax.elemento;
                eliminaV(vMax);
                return;
                
            }
        
            elementos--;
            if(vB.hayPadre())
            {
            
                if (vB.hayIzquierdo() && !vB.hayDerecho()){
                    vB.izquierdo.padre = vB.padre;
                    
                    if(vB == vB.padre.derecho)
                        vB.padre.derecho =vB.izquierdo;
                    else
                        vB.padre.izquierdo =vB.izquierdo;
                    
                }
                else if (!vB.hayIzquierdo() && vB.hayDerecho()){
                    
                    vB.derecho.padre = vB.padre;
                    
                    if(vB == vB.padre.derecho)
                        vB.padre.derecho =vB.derecho;
                    else
                        vB.padre.izquierdo =vB.derecho;
                    
                    
                }
                else {

                    if(vB == vB.padre.derecho)
                        vB.padre.derecho =null;
                    else
                        vB.padre.izquierdo =null;
                    

                }

                
            }
            else
            {
                
                
                
                if (vB.hayIzquierdo() && !vB.hayDerecho()){
                    raiz = vB.izquierdo;
                    raiz.padre = null;

                    
                    
                }else if (!vB.hayIzquierdo() && vB.hayDerecho()){

                    raiz = vB.derecho;
                    raiz.padre = null;

                    
                    
                }else
                    

                    raiz = null;
                
            
            }
    }
     private ArbolBinario<T>.Vertice abBusca(T elemento) {
            
            
            if (raiz == null)
                return null;
            else
                return abBusca(raiz, elemento);
        }
        
        private ArbolBinario<T>.Vertice abBusca(ArbolBinario<T>.Vertice v,T e)
        {
            
            
            if (v.get().compareTo(e)==0)
                return v;
            
            if (v.get().compareTo(e)<0)
            {
                if (v.hayDerecho())
                    return abBusca(v.derecho,e);
            }
            else
            {
                if(v.hayIzquierdo())
                    return abBusca(v.izquierdo,e);
            }
            return null;
        }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
	if(vertice.izquierdo==null && vertice.derecho==null)
	    return null;
	if(vertice.izquierdo.izquierdo!=null&&vertice.izquierdo.derecho==null)
	    return null;
    if(vertice.izquierdo.izquierdo==null&&vertice.izquierdo.derecho!=null)
        return null;
    Vertice max = max(vertice.izquierdo);
    T e = vertice.elemento;
    max.elemento=vertice.elemento;
    e=max.elemento;
    return max;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
        if(vertice.izquierdo!=null && vertice.derecho==null){
            if(vertice.padre!=null){
                if(vertice.padre.izquierdo==vertice){
                    vertice.padre.izquierdo=vertice.izquierdo;
                }else{
                    vertice.padre.derecho=vertice.izquierdo;
                }
            }else if(vertice.padre==null){
                raiz=vertice.izquierdo;
            }
        }else if(vertice.derecho!=null && vertice.izquierdo==null){
            if(vertice.padre!=null){
                if(vertice.padre.izquierdo==vertice){
                    vertice.padre.izquierdo=vertice.derecho;
                }else{
                    vertice.padre.derecho=vertice.derecho;
                }
            }else if(vertice.padre==null){
                raiz=vertice.derecho;
            }
        }
        return;
    }
    private Vertice max(Vertice v){
        if(v.derecho==null)
            return v;
        return max(v.derecho);
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        return buscaAux(elemento,raiz);
    }
    private VerticeArbolBinario<T> buscaAux(T e,Vertice v){
        if(v==null)
            return null;
        if(v.elemento.compareTo(e)==0)
            return v;
        if(v.elemento.compareTo(e)>0)
            return buscaAux(e,v.izquierdo);
        return buscaAux(e,v.derecho);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
    auxGiraDerecha(vertice(vertice));
    }

    private void auxGiraDerecha(Vertice vertice){
        if(vertice == null || !vertice.hayIzquierdo())
            return;
        Vertice hi = vertice.izquierdo;
        hi.padre = null;

        if(vertice.hayPadre()){
            Vertice a = vertice.padre;
            hi.padre = a;
            if(vertice.padre.izquierdo == vertice)
                vertice.padre.izquierdo = hi;
            else
                vertice.padre.derecho = hi;
        }
        else
            raiz = hi;

        vertice.padre = null;
        vertice.izquierdo = null;

        if(hi.hayDerecho()){
            Vertice nd = hi.derecho;
            nd.padre = vertice;
            vertice.izquierdo = nd;
        }
        hi.derecho = vertice;
        vertice.padre = hi;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
    auxGiraIzquierda(vertice(vertice));
    }

    private void auxGiraIzquierda(Vertice vertice){
        if(vertice == null || !vertice.hayDerecho())
            return;
        Vertice hd = vertice.derecho;
        hd.padre = null;

        if(vertice.hayPadre()){
            Vertice a = vertice.padre;
            hd.padre = a;
            if(vertice.padre.izquierdo == vertice)
                vertice.padre.izquierdo = hd;
            else
                vertice.padre.derecho = hd;
        }
        else
            raiz = hd;

        vertice.padre = null;
        vertice.derecho = null;

        if(hd.hayIzquierdo()){
            Vertice ni = hd.izquierdo;
            ni.padre = vertice;
            vertice.derecho = ni;
        }
            hd.izquierdo = vertice;
            vertice.padre = hd;

    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        preAux(accion,raiz);
    }
    private void preAux(AccionVerticeArbolBinario<T> a,Vertice v){
        if(v==null)
            return;
        a.actua(v);
        preAux(a,v.izquierdo);
        preAux(a,v.derecho);
    }
    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        inAux(accion,raiz);
    }
    private void inAux(AccionVerticeArbolBinario<T> a,Vertice v){
        if(v==null)
            return;
        inAux(a,v.izquierdo);
        a.actua(v);
        inAux(a,v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        posAux(accion,raiz);
    }
    private void posAux(AccionVerticeArbolBinario<T> a,Vertice v){
        if(v==null)
            return;
        posAux(a,v.izquierdo);
        posAux(a,v.derecho);
        a.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
