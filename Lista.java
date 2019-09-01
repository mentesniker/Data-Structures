package mx.unam.ciencias.edd;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento=elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            // Aquí va su código.
            siguiente=cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente!=null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if(siguiente==null)
            	throw new NoSuchElementException("No hay elemento siguiente");
            anterior=siguiente;
            siguiente=siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return anterior!=null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código
            if(anterior==null)
                throw new NoSuchElementException("No hay elemento anterior");
            siguiente=anterior;
            anterior=anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            siguiente=cabeza;
            anterior=null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            siguiente=null;
            anterior=rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        // Aquí va su código.
        return cabeza==null && rabo==null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        if(elemento==null)
        	throw new IllegalArgumentException("El elemento es null");
        Nodo n = new Nodo(elemento);
        if(cabeza==null)
        	cabeza=rabo=n;
        else{
            n.anterior=rabo;
            rabo.siguiente=n;
            rabo=n;
        }
        longitud++; 
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if(elemento==null)
        	throw new IllegalArgumentException("El elemento es null");
        Nodo n = new Nodo(elemento);
        if(cabeza==null)
        	rabo=cabeza=n;
        else{
            n.siguiente=cabeza;
            cabeza.anterior=n;
            cabeza=n;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la
     * lista, el elemento se agrega al fina de la misma. En otro caso, después
     * de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al final, y si es mayor o igual que el número
     *          de elementos en la lista se agrega al inicio.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if(elemento==null)
        	throw new IllegalArgumentException("El elemento es null");
        else if(i<=0)
        	this.agregaInicio(elemento);
        else if(i>=this.getLongitud())
        	this.agregaFinal(elemento);
        else{
        	Nodo g = new Nodo(elemento);
        	Nodo aux=getNodo(i);
            g.siguiente=aux;
            g.anterior=aux.anterior;
            aux.anterior.siguiente=g;
            aux.anterior=g;
        	longitud++;
        }
    }
    //Metodo privado para buscar nodos, recibe un entero i que es el nodo a regresar
    private Nodo getNodo(int i){
    	int c = 0;
        Nodo aux=cabeza;
        while(aux!=null){
        	if(c==i)
        		return aux;
        	aux=aux.siguiente;
        	c++;
        }
        return null;
    }
    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Nodo aux = buscaNodo(elemento);
        if(cabeza==null)
        	return;
        if(longitud==1){
        	cabeza=rabo=null;
        	longitud--;
        }
        else if(aux==cabeza)
        	eliminaPrimero();
        else if(aux==rabo)
        	eliminaUltimo();
        else{
        aux.anterior.siguiente=aux.siguiente;
        aux.siguiente.anterior=aux.anterior;
        longitud--;
    }
    }
    //Metodo privado para buscar nodos,recibe el nodo donde esta el elemento y el elemento
    private Nodo buscaNodo(T elemento){
    	Nodo t=cabeza;
    	while(t!=null){
    		if(t.elemento.equals(elemento))
    			return t;
    		t=t.siguiente;
    	}
    	return null;
    }
    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(cabeza==null)
        	throw new NoSuchElementException("No hay elementos,la lista es vacia");
        else if(longitud==1){
        	T aux = cabeza.elemento;
        	cabeza=rabo=null;
        	longitud--;
        	return aux;
        }
        T aux=cabeza.elemento;
        cabeza=cabeza.siguiente;
        cabeza.anterior=null;
        longitud--;
        return aux;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(cabeza==null)
        	throw new NoSuchElementException("No hay elementos,la lista es vacia");
        else if(longitud==1){
        	T aux = rabo.elemento;
        	cabeza=rabo=null;
        	longitud--;
        	return aux;
        }
        T aux=rabo.elemento;
        rabo=rabo.anterior;
        rabo.siguiente=null;
        longitud--;
        return aux;
    }
    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return contiene(elemento,0,cabeza);
    }
    //Metodo auxiliar
    private boolean contiene(T elemento,int c,Nodo f){
    if(f==null){
        return false;
    }
    else if(c>longitud){
        return false;
    }
    else if(f.elemento.equals(elemento)){
        return true;	
    }
    return contiene(elemento,++c,f.siguiente);
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> la = new Lista<T>();
        for(T t: this){
        	la.agregaInicio(t);
        }
        return la;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Lista<T> la = new Lista<T>();
        for(T t: this){
        	la.agregaFinal(t);
        }
        return la;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        if(cabeza==null)
        	return;
        cabeza=null;
        rabo=null;
        longitud=0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(cabeza == null)
        	throw new NoSuchElementException("La lista es vacia");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(rabo == null)
        	throw new NoSuchElementException("La lista es vacia");
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if(cabeza==null)
        	return null;
        if(i<0 || i>=this.getLongitud())
        	throw new ExcepcionIndiceInvalido("El indice es invalido");
        int c = 0;
        Nodo aux= cabeza;
        while(aux!=null){
        	if(c==i)
        		return aux.elemento;
        	aux=aux.siguiente;
        	c++;
        }
        return null;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        int i=0;
        for(T t:this){
        	if(t.equals(elemento))
        		return i;
        	i++;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
    	if(cabeza==null)
        	return"[]";
    	return "[" + cabeza.elemento + toString(cabeza.siguiente);
    }
    //Metodo auxiliar toString
    private String toString(Nodo t){
    if(t==null)
        return "]";
    return ", " + t.elemento + toString(t.siguiente);
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (!(o instanceof Lista))
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        // Aquí va su código.
        Lista l = (Lista) o;
        if(l.getLongitud()==getLongitud()){
        int i = 0;      
            while(i <= longitud-1){
                if(!l.get(i).equals(this.get(i))){
                    return false;
                }
            i++;
            }
        return true;
        }
        return false;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        if(this.getLongitud()==1){
            return this.copia();
        }
        Lista<T> izq = new Lista<T>();
        Lista<T> der = new Lista<T>();
        Nodo aux = cabeza;
        int m=this.getLongitud()/2;
        int i =0;
        while(aux != null && i<m){
            izq.agregaFinal(aux.elemento);
            i++;
            aux=aux.siguiente;
        }
        while(aux != null){
            der.agregaFinal(aux.elemento);
            aux=aux.siguiente;
        }
        izq = izq.mergeSort(comparador);
        der = der.mergeSort(comparador);
        return  izq.mezcla(der,comparador);
    }
    private Lista<T> mezcla(Lista<T> otra,Comparator<T> comparador){
        Lista<T> r = new Lista<T>();
        Nodo i = this.cabeza;
        Nodo d = otra.cabeza;
        while(i != null && d != null){
            if(comparador.compare(i.elemento,d.elemento)<0){
                r.agregaFinal(i.elemento);
                i = i.siguiente;
            }else{
                r.agregaFinal(d.elemento);
                d=d.siguiente;
            }
        }
        Nodo n = i ==null? d:i;
        while(n!=null){
            r.agregaFinal(n.elemento);
            n=n.siguiente;
        }
        return r;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> l) {
        return l.mergeSort((a,b)->a.compareTo(b));
    }    

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador){
        if(this.getLongitud()==0){
            return false;
        }
        Nodo aux=new Nodo(elemento);
        aux = cabeza;
        while(aux != null){
            if(comparador.compare(elemento,aux.elemento)==0){
                return true;
            }
            aux=aux.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        // Aquí va su código.
        if(lista.getLongitud()==0){
            return false;
        }
        int i =0;
        for(T elem:lista){
            if(elemento.compareTo(lista.get(i))==0){
                return true;
            }
            i++;
        }
        return false;
    }
}
