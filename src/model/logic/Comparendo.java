package model.logic;

public class Comparendo {

	private int objectId;

	private String localidad;

	private double longitud;

	private double latitud;	


	public Comparendo(int pObjectid, String pLocalidad, double pLongitud, double pLatitud){

		objectId = pObjectid;

		localidad = pLocalidad;

		longitud = pLongitud;

		latitud = pLatitud;
	}

	public int darObjectId(){

		return objectId;
	}

	public double darLatitud(){

		return latitud;
	}

	public double darLongitud(){

		return longitud;

	} 

	public String darLocalidad(){

		return localidad;
	}


	public int compareTo(int arg0) {

		return objectId - arg0 ;
	}
}
