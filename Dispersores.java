package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave){
    	 int l = llave.length;
    	 int i = 0;
    	 int key  = 0; 
    	 int aux = 0;
        while(l>= 4){
			key ^= ((llave[i] & 0xFF) << 24) | ((llave[i+1]& 0xFF) << 16) | ((llave[i+2] & 0xFF) << 8) | ((llave[i+3] & 0xFF));
			l -= 4; 
			i += 4;
		}
		switch(l){
        	case 3 : 
        	int ij = llave[i+2] & 0xff;
        		aux = aux | (ij << 8);
        	case 2 : 
        	int ijk = llave[i+1] & 0xff;
        		aux = aux | (ijk << 16);
        	case 1 : 
        	int ijkl = llave[i] & 0xff;
        		aux = aux | (ijkl << 24);
        }
		return key ^ aux;  
	}
    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        return huella_bj(llave, llave.length);
    }
    
    private static int huella_bj(byte[]k, int n){
        int a, b, c;
        int l ,i;
        
        l = n;
        a = b = 0x9e3779b9;
        c = 0xffffffff;
        i = 0;
        
        while (l >= 12) {
            a += ((k[i]&0xFF) + ((k[i+1]&0xFF) << 8) + ((k[i+2]&0xFF)  << 16) + ((k[i+3]&0xFF)  << 24));
            b += ((k[i+4]&0xFF) + ((k[i+5]&0xFF) << 8) + ((k[i+6]&0xFF)  << 16) + ((k[i+7]&0xFF)  << 24));
            c += ((k[i+8]&0xFF) + ((k[i+9]&0xFF) << 8) + ((k[i+10]&0xFF) << 16) + ((k[i+11]&0xFF) << 24));
            

            
            a -= b; a -= c; a ^= (c >>>  13);
            b -= c; b -= a; b ^= (a << 8);
            c -= a; c -= b; c ^= (b >>> 13);
            a -= b; a -= c; a ^= (c >>> 12);
            b -= c; b -= a; b ^= (a << 16);
            c -= a; c -= b; c ^= (b >>> 5);
            a -= b; a -= c; a ^= (c >>> 3);
            b -= c; b -= a; b ^= (a << 10);
            c -= a; c -= b; c ^= (b >>> 15);
            
            i += 12;
            l -= 12;
        }
        
        c += n;
        switch (l) {
            case 11: c += ((k[i+10]&0xFF) << 24);
            case 10: c += ((k[i+9]&0xFF)  << 16);
            case  9: c += ((k[i+8]&0xFF)  <<  8);
                
            case  8: b += ((k[i+7]&0xFF)  << 24);
            case  7: b += ((k[i+6]&0xFF)  << 16);
            case  6: b += ((k[i+5]&0xFF)  <<  8);
            case  5: b += (k[i+4]&0xFF);
                
            case  4: a += ((k[i+3]&0xFF)  << 24);
            case  3: a += ((k[i+2]&0xFF)  << 16);
            case  2: a += ((k[i+1]&0xFF)  <<  8);
            case  1: a += (k[i]&0xFF);
        }
        
        a -= b; a -= c; a ^= (c >>>  13);
        b -= c; b -= a; b ^= (a << 8);
        c -= a; c -= b; c ^= (b >>> 13);
        a -= b; a -= c; a ^= (c >>> 12);
        b -= c; b -= a; b ^= (a << 16);
        c -= a; c -= b; c ^= (b >>> 5);
        a -= b; a -= c; a ^= (c >>> 3);
        b -= c; b -= a; b ^= (a << 10);
        c -= a; c -= b; c ^= (b >>> 15);
        
        return (int)c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int key = 5381;
        int i = 0;
        while(i < llave.length){
        	byte aux = llave[i];
        	int aux2 = aux & 0xff;
        	key *= 33;
        	key = suma(key,aux2); 
        	i++;
        }
        return key;
    }
    private static int suma(int a,int b){
    	long ia = a;
    	long ib = b;
    	long r = (ia + ib) & 0xffffffff;
    	return (int) r;
    }
    private static int resta(int a, int b){
    	long ia = a;
    	long ib = b;
    	long r = (ia - ib) & 0xffffffff;
    	return (int) r;	
    }
    private static int multiplica(int a,int b){
    	long la =  a;
    	long lb = b;
    	long r = (la * lb) & 0xffffffff;
    	return (int) r;
    }
}
