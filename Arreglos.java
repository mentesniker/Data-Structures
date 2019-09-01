package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar en arreglos genéricos.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo.
     * @param comparador el comparador para ordenar los elementos del arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSorts(arreglo,0,arreglo.length-1,comparador);
    }
    private static <T> void quickSorts(T[] arreglo,int ini,int fin,Comparator<T> comparador){
    	if(fin<=ini)
    		return;
    	int i = ini+1;
    	int j = fin;
    	while(i<j){
    		if(comparador.compare(arreglo[i],arreglo[ini])>0 && 
    			comparador.compare(arreglo[j],arreglo[ini])<=0)
    			intercambia(arreglo,i++,j--);
    		else if(comparador.compare(arreglo[i],arreglo[ini])<=0)
    			i++;
    		else{
    			j--;
    		}
    	}
    	if(comparador.compare(arreglo[i],arreglo[ini])>0)
    		i--;
    	intercambia(arreglo,i,ini);
    	quickSorts(arreglo,ini,i-1,comparador);
    	quickSorts(arreglo,i+1,fin,comparador);
    }
    	
    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo, que debe extender {@link
     * Comparable}.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
    	quickSort(arreglo,(a,b)->a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param comparador el comparador para ordenar los elementos del arreglo.
     */
    public static <T>
    void selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        int i =0;
        while (i<arreglo.length-1){
        	for(int j=i+1;j<arreglo.length;j++){
        		if(comparador.compare(arreglo[i],arreglo[j])>0){
        		intercambia(arreglo,i,j);
        		}
        	}
            i++;
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo, que debe extender {@link
     * Comparable}.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        // Aquí va su código.
        int i =0;
        while (i<arreglo.length-1){
        	for(int j=i+1;j<arreglo.length;j++){
        		if(arreglo[i].compareTo(arreglo[j])>0){
        		intercambia(arreglo,i,j);
        		}
        	}
            i++;
        }
        }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para buscar el elemento en el arreglo.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T>
    int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        int min = 0;
        int max = arreglo.length;
        while(min <= max){
        	int x = min + (max - min) / 2;
        	if(comparador.compare(arreglo[x],elemento)==0){
        		return x;
        	}
        	else if(comparador.compare(arreglo[x],elemento)<0){
        		//derecha
        		min = x;
        	}else if(comparador.compare(arreglo[x],elemento)>0){
        		//izquierdo
        		max = x;
        	}
        }
        return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo, que debe extender {@link
     * Comparable}.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>>
    int busquedaBinaria(T[] arreglo, T elemento) {
        // Aquí va su código.
        int min = 0;
        int max = arreglo.length;
        for(T t:arreglo){
        	int x = min + (max - min) / 2;
        	if(arreglo[x].compareTo(elemento)==0){
        		return x;
        	}
        	if(arreglo[x].compareTo(elemento)<0){
        		//derecha
        		min = x;
        	}else if(arreglo[x].compareTo(elemento)>0){
        		//izquierdo
        		max = x;
        	}
        }
        return -1;
    }
    //cambia los elementos recibidos
    public static <T> void intercambia(T[] arreglo,int d,int i){
        if(d==i){
            return;
        }
        T t = arreglo[d];
        arreglo[d] = arreglo[i];
        arreglo[i] = t;   
    }
}
