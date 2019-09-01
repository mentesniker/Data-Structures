package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
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
            return "\n";
        }
        return "\n" + a.elemento + dibujito(a.siguiente);
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
       if(elemento==null)
            throw new IllegalArgumentException("El elemento es null");
        Nodo aux = new Nodo(elemento);
        if(cabeza==null){
            cabeza=rabo=aux;
            return;
        }
        aux.siguiente=cabeza;
        cabeza=aux; 
    }
}
