package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(cabeza==null)
            return "";
        else{
            return cabeza.elemento + dibujito(cabeza.siguiente);
        }
    }
    private String dibujito(Nodo a){
        if(a == null){
            return ",";
        }
        return "," + a.elemento + dibujito(a.siguiente);
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if(elemento==null)
            throw new IllegalArgumentException("El elemento es null");
        Nodo aux = new Nodo(elemento);
        if(rabo==null){
            cabeza=rabo=aux;
            return;
        }
        rabo.siguiente=aux;
        rabo=aux;
    }
}
