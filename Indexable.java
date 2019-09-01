package mx.unam.ciencias.edd;

/**
 * Clase para poder utilizar comparables indexables.
 */
public class Indexable<T> implements ComparableIndexable<Indexable<T>> {

    /* El elemento */
    private T elemento;
    /* Su valor */
    private double valor;
    /* Su índice. */
    private int indice;

    /**
     * Crea un nuevo indexable con el elemento y valor dados.
     * @param elemento el elemento.
     * @param valor su valor.
     */
    public Indexable(T elemento, double valor) {
        // Aquí va su código.
        this.elemento = elemento;
        this.valor = valor;
        this.indice = -1;
    }

    /**
     * Regresa el elemento del indexable.
     * @return el elemento del indexable.
     */
    public T getElemento() {
        // Aquí va su código.
        return this.elemento;
    }

    /**
     * Compara el indexable con otro indexable.
     * @param indexable el indexable.
     * @return un valor menor que cero si el indexable que llama el método es
     *         menor que el parámetro; cero si son iguales; o mayor que cero si
     *         es mayor.
     */
    @Override public int compareTo(Indexable<T> indexable) {
        // Aquí va su código.
        return (int)Math.floor(this.getValor()-indexable.getValor());
    }

    /**
     * Define el índice del indexable.
     * @param indice el nuevo índice.
     */
    @Override public void setIndice(int indice) {
        // Aquí va su código.
        this.indice = indice;
    }
    /**
     * Regresa el índice del indexable.
     * @return el índice del indexable.
     */
    @Override public int getIndice() {
        // Aquí va su código.
        return this.indice;
    }
    /**
     * Define el valor del indexable.
     * @param valor el nuevo valor.
     */
    public void setValor(double valor) {
        // Aquí va su código.
        this.valor = valor;
    }
    /**
     * Regresa el valor del indexable.
     * @return el valor del indexable.
     */
    public double getValor() {
        // Aquí va su código.
        return this.valor;
    }
    /**
     * Nos dice si el indexable es igual al objeto recibido.
     * @param o el objeto con el que queremos comparar el indexable.
     * @return <code>true</code> si el objeto recibido es un indexable igual al
     *         que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        // Aquí va su código.
        if (!(o instanceof Indexable))
            return false;
        @SuppressWarnings("unchecked") Indexable<T> in = (Indexable<T>)o;
        if(this.getIndice() != in.getIndice())
            return false;
        if(this.getElemento() != in.getElemento())
            return false;
        if(this.getValor() != in.getValor())
            return false;
        return true;
    }
    /**
     * Regresa una representación en cadena del indexable.
     * @return una representación en cadena del indexable.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = String.format("%d:%2.9f", elemento, valor);
        return s;
    }
}
