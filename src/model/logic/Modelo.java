package model.logic;

import model.data_structures.ArregloDinamico;
import model.data_structures.IArregloDinamico;
import model.data_structures.IlistaOrdenada;
import model.data_structures.ListaOrdenada;
import model.data_structures.NodoLista;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */

	private NodoLista<Comparendo> comparendo;
	private IlistaOrdenada lista; 
	GeoJSONProcessing objetoJsonGson = new GeoJSONProcessing( "./data/comparendos_dei_2018_small.geojson" );




	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{

		lista = new ListaOrdenada();
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return lista.darLongitud();
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Comparendo dato)
	{	
		lista.agregar(dato);
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 */
	public Comparendo  buscar(int dato)
	{
		return (Comparendo) lista.buscar(dato);
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 */
	public Comparendo eliminar( Comparendo dato)
	{
		return lista.eliminar(dato); 
	}



	public void cargarDatos(){

		objetoJsonGson.processingJSONFile();

	}
}
