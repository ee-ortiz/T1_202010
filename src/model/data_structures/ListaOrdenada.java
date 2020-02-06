package model.data_structures;

import java.util.Iterator;

public class ListaOrdenada <T extends Comparable<T>> implements IlistaOrdenada<T> {

	private int longitud;

	private NodoLista<T> primero;

	public ListaOrdenada(){

		int x = 0;
		IteratorLista<T> iterator = (IteratorLista<T>) iterator();
		while(iterator.hasNext()){
			NodoLista<T> e = (NodoLista<T>) iterator.next();
			x++;
		}
		longitud = x;
	}

	public Iterator<T> iterator() {

		return new IteratorLista<T>(primero);
	}

	public T eliminar(T pElemento) throws Exception{

		T rta = null;

		if(primero == null){

			throw new Exception("No existe ningun elemento que eliminar");
		}

		else if (primero.darElemento().compareTo(pElemento)==0){

			NodoLista<T> siguiente = primero.darSiguiente();
			rta = primero.darElemento();
			primero = siguiente;
		}

		else{

			NodoLista<T> anterior = null;
			NodoLista<T> actual = primero;
			boolean encontro = false;
			while(actual != null && encontro == false){

				if(actual.darElemento().compareTo(pElemento)==0){

					encontro = true;
				}

				else{
					anterior = actual;
					actual = anterior.darSiguiente();
				}
			}

			if(encontro ==  true){
				rta = actual.darElemento();
				anterior.cambiarSiguiente(actual.darSiguiente());

			}


		}

		return rta;


	}

	public T buscar(T pElemento){

		T rta = null;

		if(primero.darElemento().compareTo(pElemento)==0){
			rta = primero.darElemento();
		}

		else{

			NodoLista<T> actual = primero;

			boolean encontro = false;

			while(actual != null && encontro == false){

				if(actual.darElemento().compareTo(pElemento)==0){
					encontro = true;
				}

				else{

					actual = actual.darSiguiente();
				}
			}

			if(encontro ==true){

				rta = actual.darElemento();
			}
		}

		return rta;
	}

	public int darLongitud(){

		return longitud;
	}


	public boolean agregarOrdenado(T pNuevo){

		boolean rta = false;

		NodoLista<T> nuevo = new NodoLista<T>(pNuevo);

		if(primero == null){

			primero = nuevo;
			rta = true;
		}

		else if(pNuevo.compareTo(primero.darElemento())< 0){

			nuevo.cambiarSiguiente(primero);
			primero = nuevo;
			rta = true;
		}

		else if (pNuevo.compareTo(primero.darElemento())> 0){



			Iterator<NodoLista<T>> iterator = (Iterator<NodoLista<T>>) iterator();

			while(iterator.hasNext() ){

				NodoLista<T> e = iterator.next();

				if(pNuevo.compareTo(e.darElemento())> 0){

				}

				else if( e == null || pNuevo.compareTo(e.darElemento())< 0){

					NodoLista<T> sig = e;
					e = nuevo;
					e.cambiarSiguiente(sig);
					rta = true;

				}
			}
		}

		if(rta){
			longitud++;
		}
		return rta;
	}

	public Object[] darArreglo(){

		Object[] arreglo = new Object[this.darLongitud()];

		NodoLista<T> actual = primero;

		for(int i = 0; i< longitud; i++){

			arreglo[i] = actual;
			actual = actual.darSiguiente();
		}

		return arreglo;
	}



}
