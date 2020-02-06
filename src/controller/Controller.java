package controller;

import java.util.Scanner;

import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		Integer dato = 0;
		Comparendo respuesta;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				modelo.cargarDatos();
				view.printMessage("--------- \nDatos cargados");				
				break;

			case 2:
				view.printMessage("--------- \nPor favor ingresa el ObjectID del comparendo");
				dato = lector.nextInt();
				respuesta = modelo.buscar(dato);
				if(respuesta !=null){
					view.printMessage("\n dos datos son: Su objectID es  " + respuesta.darObjectId() + ", su localidad es " + respuesta.darLocalidad() +
							"y su latitud y longitud son respectivamente " + respuesta.darLatitud() + ", " + respuesta.darLongitud());

				}

				else{
					view.printMessage("\n no se pudo encontrar el comparendo que buscabas");
				}


				break;



			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
