package org.jmedikit.lib.util;

/**
 * Die Klasse Vektor repr�sentiert eine dreidimensionale Datenstruktur. Ist der Parameter <T> vom Datentyp Float, kann mit den typischen Rechenoperationen
 * f�r Vektoren gerechnet werden.
 * 
 * @author rkorb
 *
 * @param <T> numerischer Datentyp
 */
public class Vector3D<T> {
	public T x;
	
	public T y;
	
	public T z;
	
	/**
	 * Homogener Anteil des Vektors
	 */
	public T h;
	
	public Vector3D(){

	}
	
	/**
	 * 
	 * Erstellt einen dreidimensionalen Vektor entsprechend der �bergebenen Werte
	 * 
	 * @param x x-Element
	 * @param y y-Element
	 * @param z z-Element
	 * @param h Homogener Anteil, muss bei numerischem Typ den Wert 1 haben
	 */
	public Vector3D(T x, T y, T z, T h){
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}
	
	/**
	 * Setzt die Werte des Vektors entsprechend der Parameter. Der homogene Anteil wird nicht ver�ndert.
	 * 
	 * @param x x-Element
	 * @param y y-Element
	 * @param z z-Element
	 */
	public void setVector(T x, T y, T z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString(){
		return "["+x+", "+y+", "+z+"]";
	}

	/**
	 * Gibt null zur�ck, wenn der Index nicht vorhanden ist.
	 * index muss ein Wert zwischen [0 und 2] inklusive sein.
	 * Auf die homogene Koordinate kann nicht zugegriffen werden.
	 * 
	 * @param index
	 * @return
	 */
	public T get(int index){
		switch (index) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		default:
			return h;
		}
	}
	
	/**
	 * Setzt den Wert des Vektors
	 * index darf Werte von [0 bis 2] annehmen.
	 * 0 entspricht dem x-Wert - 2 dem z-Wert
	 * 
	 * @param index
	 * @param value
	 */
	public void set(int index, T value){
		switch (index) {
		case 0:
			x = value;
			break;
		case 1:
			y = value;
			break;
		case 2:
			z = value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Vector3D other = (Vector3D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}
	
	/**
	 * Berechnet die L�nge eines Vektors
	 *  
	 * @param v
	 * @return
	 */
	public static float length(final Vector3D<Float> v){
		return (float)(Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z));
	}
	
	/**
	 * Addition des Vektors b auf Vektor a
	 * 
	 * @return
	 */
	public static Vector3D<Float> add(final Vector3D<Float> a, final Vector3D<Float> b){
		return new Vector3D<Float>(a.x+b.x, a.y+b.y, a.z+b.z, 1f);
	}
	
	/**
	 * Subtraktion des Vektors b von Vektor a
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3D<Float> substract(final Vector3D<Float> a, final Vector3D<Float> b){
		return new Vector3D<Float>(a.x-b.x, a.y-b.y, a.z-b.z, 1f);
	}	
	
	/**
	 * Skalarmultiplikation
	 * 
	 * @param scalar
	 * @param v
	 * @return
	 */
	public static Vector3D<Float> smult(final float scalar, final Vector3D<Float> v){
		return new Vector3D<Float>(scalar*v.x, scalar*v.y, scalar*v.z, 1f);
	}
	
	/**
	 * Berechnet das Skalarprodukt aus Vektor a und Vektor b
	 * 
	 * Vektoren sind orthogonal wenn das Skalarprodukt 0 ergibt
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static float dotProduct(final Vector3D<Float> a, final Vector3D<Float> b){
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	/**
	 * Durch die beiden Richtungsvektoren a und b wird das Kreuzprodukt berechnet.
	 * Das Kreuzprodukt wird verwendet, um die Normale der Ebene zu bestimmen.
	 * Die Rotation erfolgt durch Rotieren des Normalen Vektors.
	 * 
	 * @param a Vector
	 * @param b Vector
	 * @return
	 */
	public static Vector3D<Float> crossProduct(final Vector3D<Float> a, final Vector3D<Float> b){
		float cpX = a.y*b.z - a.z*b.y;
		float cpY = a.z*b.x - a.x*b.z;
		float cpZ = a.x*b.y - a.y*b.x;
		return new Vector3D<Float>(cpX, cpY, cpZ, 1f);
	}
	
	/**
	 * Rotation um die X-Achse um den angegebenen Winkel alpha im Bogenmass
	 * 
	 */
	public static Vector3D<Float> rotateX(final float alpha, final Vector3D<Float> normal){
		Vector3D<Float> rotatedNormal = new Vector3D<Float>(0f, 0f, 0f, 1f);

		float[][] rotationsMatrix = new float[4][4];
		rotationsMatrix[0][0] = 1; 	rotationsMatrix[0][1] = 0; 									rotationsMatrix[0][2] = 0;								rotationsMatrix[0][3] = 0;
		rotationsMatrix[1][0] = 0; 	rotationsMatrix[1][1] = (float) Math.cos((double)alpha); 	rotationsMatrix[1][2] = (float) (-1*Math.sin(alpha));	rotationsMatrix[1][3] = 0;
		rotationsMatrix[2][0] = 0; 	rotationsMatrix[2][1] = (float) Math.sin(alpha); 			rotationsMatrix[2][2] = (float) Math.cos(alpha);		rotationsMatrix[2][3] = 0;
		rotationsMatrix[3][0] = 0; 	rotationsMatrix[3][1] = 0; 									rotationsMatrix[3][2] = 0; 								rotationsMatrix[3][3] = 1;
		
		for(int i = 0; i < rotationsMatrix.length; i++){
			for(int j = 0; j < rotationsMatrix[i].length; j++){
				rotatedNormal.set(i, rotatedNormal.get(i)+normal.get(j)*rotationsMatrix[i][j]); 
			}
		}
		
		return rotatedNormal;
	}
	
	/**
	 * Rotation um die Y-Achse um den angegebenen Winkel alpha im Bogenmass
	 * 
	 */
	public static Vector3D<Float> rotateY(final float alpha, final Vector3D<Float> normal){
		Vector3D<Float> rotatedNormal = new Vector3D<Float>(0f, 0f, 0f, 1f);

		float[][] rotationsMatrix = new float[4][4];
		rotationsMatrix[0][0] = (float)Math.cos(alpha); 	rotationsMatrix[0][1] = 0;	rotationsMatrix[0][2] = (float) Math.sin(alpha);	rotationsMatrix[0][3] = 0;
		rotationsMatrix[1][0] = 0; 							rotationsMatrix[1][1] = 1; 	rotationsMatrix[1][2] = 0;							rotationsMatrix[1][3] = 0;
		rotationsMatrix[2][0] = -1*(float)Math.sin(alpha);	rotationsMatrix[2][1] = 0; 	rotationsMatrix[2][2] = (float) Math.cos(alpha);	rotationsMatrix[2][3] = 0;
		rotationsMatrix[3][0] = 0; 							rotationsMatrix[3][1] = 0; 	rotationsMatrix[3][2] = 0; 							rotationsMatrix[3][3] = 1;
		
		for(int i = 0; i < rotationsMatrix.length; i++){
			for(int j = 0; j < rotationsMatrix[i].length; j++){
				rotatedNormal.set(i, rotatedNormal.get(i)+normal.get(j)*rotationsMatrix[i][j]); 
			}
		}
		
		return rotatedNormal;
	}
	
	/**
	 * Rotation um die Z-Achse um den angegebenen Winkel alpha im Bogenmass
	 * 
	 */
	public static Vector3D<Float> rotateZ(final float alpha, final Vector3D<Float> normal){
		Vector3D<Float> rotatedNormal = new Vector3D<Float>(0f, 0f, 0f, 1f);

		float[][] rotationsMatrix = new float[4][4];
		rotationsMatrix[0][0] = (float)Math.cos(alpha); rotationsMatrix[0][1] = -1*(float)Math.sin(alpha); 	rotationsMatrix[0][2] = 0;	rotationsMatrix[0][3] = 0;
		rotationsMatrix[1][0] = (float)Math.sin(alpha); rotationsMatrix[1][1] = (float)Math.cos(alpha); 	rotationsMatrix[1][2] = 0;	rotationsMatrix[1][3] = 0;
		rotationsMatrix[2][0] = 0;						rotationsMatrix[2][1] = 0; 							rotationsMatrix[2][2] = 1;	rotationsMatrix[2][3] = 0;
		rotationsMatrix[3][0] = 0; 						rotationsMatrix[3][1] = 0; 							rotationsMatrix[3][2] = 0; 	rotationsMatrix[3][3] = 1;
		
		for(int i = 0; i < rotationsMatrix.length; i++){
			for(int j = 0; j < rotationsMatrix[i].length; j++){
				rotatedNormal.set(i, rotatedNormal.get(i)+normal.get(j)*rotationsMatrix[i][j]); 
			}
		}
		
		return rotatedNormal;
	}
	
	public static Vector3D<Float> translate(final float x, final float y, final float z, final Vector3D<Float> vector){
		Vector3D<Float> translatedVector = new Vector3D<Float>(0f, 0f, 0f, 1f);
		
		float[][] translationMatrix = new float[4][4];
		translationMatrix[0][0] = 1; translationMatrix[0][1] = 0; 	translationMatrix[0][2] = 0;	translationMatrix[0][3] = x;
		translationMatrix[1][0] = 0; translationMatrix[1][1] = 1; 	translationMatrix[1][2] = 0;	translationMatrix[1][3] = y;
		translationMatrix[2][0] = 0; translationMatrix[2][1] = 0; 	translationMatrix[2][2] = 1;	translationMatrix[2][3] = z;
		translationMatrix[3][0] = 0; translationMatrix[3][1] = 0;   translationMatrix[3][2] = 0; 	translationMatrix[3][3] = 1;		
		
		for(int i = 0; i < translationMatrix.length; i++){
			for(int j = 0; j < translationMatrix[i].length; j++){
				translatedVector.set(i, translatedVector.get(i)+vector.get(j)*translationMatrix[i][j]); 
			}
		}
		
		return translatedVector;
	}
}
