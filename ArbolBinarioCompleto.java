package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
            // Aquí va su código.
            cola = new Cola<Vertice>();
            Cola<Vertice> c = new Cola<Vertice>();
            
            if (raiz != null)
                c.mete(raiz);
            auxCons(c);
            }
            private void auxCons(Cola<Vertice> c){
            	if(c.esVacia())
            		return;
            	Vertice v = c.saca();
            	cola.mete(v);
            	if(v.izquierdo!=null)
            		c.mete(v.izquierdo);
            	if(v.derecho!=null)
            		c.mete(v.derecho);
            	auxCons(c);
            }
        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !(cola.esVacia());
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            Vertice v = cola.saca();
            return v.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException ("El elemento es null");
        Vertice v = new Vertice (elemento);
        if(raiz == null){
            raiz=v;
            elementos ++;
            return;
        }
        elementos++;
        if(raiz.izquierdo == null){
            raiz.izquierdo = v;
        	v.padre=raiz;
        }
        else if(raiz.derecho == null){
            raiz.derecho = v;
        	v.padre=raiz;
        }
        else{
        Cola<Vertice> c = new Cola<Vertice>();
        c.mete(raiz);
        auxBusca(c,v,elemento);
    	}
    }
        private void auxBusca(Cola<Vertice> c, Vertice f,T e){
        if(c.esVacia())
            return;
        f = c.saca();
        if(f.izquierdo == null){
        	Vertice i = nuevoVertice(e);
            f.izquierdo=i;
            i.padre=f;
            return;
        }
        else if(f.derecho == null){
            Vertice d = nuevoVertice(e);
            f.derecho=d;
            d.padre=f;
            return;
        }
        c.mete(f.izquierdo);
        c.mete(f.derecho);
        auxBusca(c, f,e);
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice lastV = getLastElement();
        Vertice eraseV = busquedaBFS(elemento);
        if (eraseV != null){
            elementos--;
            if (lastV == eraseV){
                if (eraseV == raiz){
                    raiz = null;
                }
                else{
                    if (lastV.padre.izquierdo == lastV)
                        lastV.padre.izquierdo = null;
                    else
                        lastV.padre.derecho = null;
                }
            }
            else{
                eraseV.elemento = lastV.elemento;
                if (lastV.padre.izquierdo == lastV)
                    lastV.padre.izquierdo = null;
                else
                    lastV.padre.derecho = null;
            }
        }
    }
    private Vertice busquedaBFS(T e){
        Vertice eraseElem = null;
        Cola<Vertice> q = new Cola<Vertice>();
        if (raiz == null)
            return eraseElem;
        else{
            q.mete(raiz);
            while(!q.esVacia()){
                Vertice cVer = q.saca();
                if(cVer.elemento.equals(e)){
                    eraseElem = cVer;
                    break;
                }
                if(cVer.hayIzquierdo()){
                    q.mete(cVer.izquierdo);
                }
                if(cVer.hayDerecho()){
                q.mete(cVer.derecho);
                }
            }
        }
        return eraseElem;
    }
    private Vertice getLastElement(){
        Vertice lastElem = null;
        Cola<Vertice> q = new Cola<Vertice>();
        if (raiz == null)
            return lastElem;
        else{
            q.mete(raiz);
            while(!q.esVacia()){
                Vertice cVer = q.saca();
                if(cVer.hayIzquierdo()){
                    q.mete(cVer.izquierdo);
                }
                if(cVer.hayDerecho()){
                    q.mete(cVer.derecho);
                }
                if (!cVer.hayIzquierdo() && !cVer.hayDerecho() && q.esVacia()){
                    lastElem = cVer;
                    break;
                }
            }
        }
        return lastElem;
    }
    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        int n = (int)Math.floor(Math.log(elementos) / Math.log(2));
        return n;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
