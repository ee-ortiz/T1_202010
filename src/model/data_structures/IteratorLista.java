package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorLista <T extends Comparable<T>> implements Iterator<T> {

	private NodoLista<T> proximo;
	private NodoLista<T> antProximo;
	private NodoLista<T> antAnt;





	public IteratorLista(NodoLista<T> primero) {

		proximo = primero;
	}

	public boolean hasNext(){

		return proximo!=null;
	}

	public T next(){

		if(proximo==null){

			throw new NoSuchElementException("No hay proximo");
		}
		T elemento = proximo.darElemento();
		proximo = proximo.darSiguiente();
		return elemento;
	}

	public void remove() throws UnsupportedOperationException, IllegalStateException{

		throw new UnsupportedOperationException("No implementada");
	}
}
