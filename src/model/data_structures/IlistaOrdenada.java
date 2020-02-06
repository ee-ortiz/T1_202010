package model.data_structures;

import java.util.Iterator;

public interface IlistaOrdenada <T extends Comparable<T>> extends Iterable<T> {

	public T eliminar(T pElemento) throws Exception ;

	public T buscar(T pElemento);

	public int darLongitud();

	public boolean agregarOrdenado(T pNuevo);

	public Object[] darArreglo();

	public Iterator<T> iterator();




}