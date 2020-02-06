package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import model.data_structures.ListaOrdenada;;

public class GeoJSONProcessing {

	private final static String comparendos_small_GEOJSON_FILE = "./data/comparendos_dei_2018_small.geojson"; // Processing JSONObject

	private String rutaArchivoJSON;
	private boolean inicioArrayComparendos;
	private boolean leyendoPropiedades;      
	private boolean leyendoGeometria;       
	private boolean crearObjComparendo; 

	private String propiedad;
	private boolean identificarObjectId;
	private int objectId;
	private boolean identificarLocalidad;
	private String localidad;

	private boolean identificarLongitud;
	private boolean identificarLatitud;
	private double longitud;				
	private double latitud;		

	public GeoJSONProcessing(String rutaArchivo){

		rutaArchivoJSON = rutaArchivo;
		inicioArrayComparendos = false;
		leyendoPropiedades = false;
		leyendoGeometria = false;
		crearObjComparendo = false;

		propiedad = "";
		identificarObjectId = false;
		objectId = -1;
		identificarLocalidad = false;
		localidad = "";
		identificarLongitud = false;
		identificarLatitud = false;		
		longitud = 0.0;
		latitud = 0.0;

	}

	private void handleObject(JsonReader reader) throws IOException{

		System.out.println("BEGIN_OBJECT");
		reader.beginObject();
		while (reader.hasNext()) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.BEGIN_ARRAY))
			{
				handleArray(reader);
			}
			else if (token.equals(JsonToken.BEGIN_OBJECT)) {
				handleObject(reader);

				// adicional
				System.out.println("END_OBJECT");
				reader.endObject();
				if ( crearObjComparendo )
				{
					crearComparendo();
				}
			}
			/*
			else if (token.equals(JsonToken.END_OBJECT)) {
				System.out.println("END_OBJECT");
				reader.endObject();
				return;
			}
			 */
			else
			{
				handleNonArrayToken(reader, token);
			}
		}


	}

	public void handleArray(JsonReader reader) throws IOException
	{
		boolean finish = false;
		System.out.println("BEGIN_ARRAY");
		reader.beginArray();
		while (!finish) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.END_ARRAY)) {
				System.out.println("END_ARRAY");
				reader.endArray();
				finish = true;
			} else if (token.equals(JsonToken.BEGIN_OBJECT)) {
				handleObject(reader);
			} else if (token.equals(JsonToken.END_OBJECT)) {
				System.out.println("END_OBJECT");
				reader.endObject();
				if ( crearObjComparendo )
				{
					crearComparendo();
				}

			} else
				handleNonArrayToken(reader, token);
		}
	}


	public void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException
	{
		if (token.equals(JsonToken.NAME))
		{
			propiedad = reader.nextName();
			System.out.println("NAME=" + propiedad);	
			if (propiedad.equalsIgnoreCase("features"))
			{  // Identificacion del JSON Array de comparendos
				inicioArrayComparendos = true;
				System.out.println("OK inicioArrayComparendos");
			}
			if (inicioArrayComparendos)
			{
				if ( propiedad.equalsIgnoreCase("properties") )
				{  // Se comienza a identificar las propiedades de un comparendo
					leyendoPropiedades = true;
					System.out.println("OK inicioPropiedades");
				}
				else if ( propiedad.equalsIgnoreCase("geometry") )
				{  // Se comienza a identificar la geometria de un comparendo
					leyendoGeometria= true;
					System.out.println("OK inicioGeometria");
				}	            

				if ( leyendoPropiedades )
				{
					if ( propiedad.equalsIgnoreCase("OBJECTID"))
					{  // Se identifica la propiedad OBJECTID de un comparendo
						identificarObjectId = true;
						System.out.println("OK identificarObjectId");
					}
					else if ( propiedad.equalsIgnoreCase("LOCALIDAD"))
					{	// Se identifica la propiedad LOCALIDAD de un comparendo
						// LOCALIDAD termina la seccion de Propiedades
						identificarLocalidad = true;
						leyendoPropiedades = false; 
						System.out.println("OK identificarLocalidad");
					}
				}
				else if ( leyendoGeometria )
				{
					if ( propiedad.equalsIgnoreCase("coordinates"))
					{  // Se identifica la propiedad coordinates de un comparendo
						identificarLongitud = true;
						identificarLatitud = true;
						System.out.println("OK identificarLongitud e identificarLatitud");
						leyendoGeometria = false; // coordinates termina la seccion de Geometry
						crearObjComparendo = true;
					}

				}

			}
		}
		else if (token.equals(JsonToken.STRING))
		{
			String valorString = reader.nextString();
			if ( identificarLocalidad )
			{
				localidad = valorString;
				identificarLocalidad = false;
				System.out.println("STRING LOCALIDAD="+localidad);				
			}
			else
			{
				System.out.println("STRING=" + valorString);
			}
		}
		else if (token.equals(JsonToken.NUMBER))
		{
			double valorNumerico = reader.nextDouble();
			if ( identificarObjectId )
			{
				objectId = (int) valorNumerico;
				identificarObjectId = false;
				System.out.println("NUMBER OBJECTID= "+objectId);
			}
			else if ( identificarLongitud )
			{
				longitud = valorNumerico;
				identificarLongitud = false;
				System.out.println("NUMBER Longitud= "+longitud);				
			}
			else
			{
				System.out.println("NUMBER=" + valorNumerico);				
			}
		}
		else if (token.equals(JsonToken.BOOLEAN))
		{
			boolean valorBool = reader.nextBoolean();
			System.out.println("BOOLEAN=" + valorBool);
		}
		/* else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleObject(reader);
	        }
	        else if (token.equals(JsonToken.END_OBJECT)) {
	        	System.out.println("END_OBJECT");
                reader.endObject();
	        } */
		else
		{
			System.out.println("SKIP_VALUE: " + token);
			reader.skipValue();
		}
	}

	public void processingJSONFile( ) 
	{
		try
		{
			BufferedReader rd = null;
			StringReader srd = null;

			rd = new BufferedReader(new FileReader(rutaArchivoJSON));
			String inputLine = null;
			StringBuilder builder = new StringBuilder();

			//Store the contents of the file to the StringBuilder.
			while((inputLine = rd.readLine()) != null)
			{
				builder.append(inputLine);
			}
			srd = new StringReader(builder.toString());

			JsonReader reader = new JsonReader( srd );

			if ( rutaArchivoJSON.equals(comparendos_small_GEOJSON_FILE))  // Definido como un JSON_OBJECT
			{
				// we call the handle object method to handle the full json object. This
				// implies that the first token in JsonToken.BEGIN_OBJECT, which is
				// always true.

				// Reading Test of a JSON object
				System.out.println("Reading the JSON Object File: " + rutaArchivoJSON);
				handleObject(reader);
			}
			else
			{
				// Reading Test of a JSON Array
				System.out.println("Reading the JSON Array File: " + rutaArchivoJSON);
				handleArray(reader);
			}
			System.out.println("End Test Handle JSON processing");

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Creacion de un comparendo a partir de la lectura de todas sus propiedades
	 */
	public void crearComparendo(ListaOrdenada pLista)
	{   
		// TODO Crear el objeto comparendo con las propiedades leidas antes
		// TODO Adicionar el comparendo a la Lista de Comparendos	
		Comparendo nuevo = new Comparendo(objectId,localidad, latitud, longitud);
		pLista.agregarOrdenado(nuevo.darObjectId());

		leyendoPropiedades = false;
		leyendoGeometria = false;
		crearObjComparendo = false;

	}

	public static void main(String[] args) 
	{
		// Inicializar el objeto de procesamiento con el nombre del archivo JSON o comparendos GEOJSON 
		GeoJSONProcessing objetoJsonGson = new GeoJSONProcessing( comparendos_small_GEOJSON_FILE );

		objetoJsonGson.processingJSONFile();


	}
}
