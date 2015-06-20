/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List of {@link PokemonInstance} with a predefined maximum size (6 per
 * default) representing a player's team.
 * 
 * @author dominik
 * 
 */
public class PokeTeam implements List<PokemonInstance> {

	private static final int DEFAULT_MAX_SIZE = 6;

	private final List<PokemonInstance> list;
	private final int maxSize;

	/**
	 * Create a new empty team with {@value #DEFAULT_MAX_SIZE} free slots.
	 */
	public PokeTeam() {
		this(DEFAULT_MAX_SIZE);
	}

	/**
	 * Create a new empty team with the specified number of free slots.
	 * 
	 * @param maxSize
	 *            Maximum number of pokemons in this team.
	 */
	private PokeTeam(int maxSize) {
		this(new ArrayList<PokemonInstance>(), maxSize);
	}

	/**
	 * Create a new team containing the elements of the specified Collection, in
	 * the order they are returned by the Collection's iterator, with
	 * {@value #DEFAULT_MAX_SIZE} as the maximum number of pokemons in this
	 * team.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this team.
	 */
	public PokeTeam(Collection<? extends PokemonInstance> coll) {
		this(coll, DEFAULT_MAX_SIZE);
	}

	/**
	 * Create a new team containing the elements of the specified Collection, in
	 * the order they are returned by the Collection's iterator, with the
	 * specified maximum number of pokemons in this team.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this team.
	 * @param maxSize
	 *            Maximum number of pokemons in this team.
	 * @throws IllegalArgumentException
	 *             If maxSize < 0 or coll.size() > maxSize
	 */
	public PokeTeam(Collection<? extends PokemonInstance> coll, int maxSize)
			throws IllegalArgumentException {
		if (maxSize < 0) {
			throw new IllegalArgumentException(
					"maxSize must be a positive int, but was " + maxSize);
		}
		if (coll.size() > maxSize) {
			throw new IllegalArgumentException("Number of PokemonInstances is "
					+ coll.size() + " but only " + maxSize + " are allowed.");
		}
		this.maxSize = maxSize;
		list = new ArrayList<>(coll);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<PokemonInstance> iterator() {
		return list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	/**
	 * If there is space left in the team, this method adds the given
	 * PokemonInstance to the end of this team, as specified in
	 * {@link List#add(Object)}. Otherwise throws an
	 * {@link IllegalStateException}.
	 * 
	 * @throws IllegalStateException
	 *             If there is no space left in the team.
	 * @see List#add(PokemonInstance)
	 */
	@Override
	public boolean add(PokemonInstance e) throws IllegalStateException {
		if (list.size() >= maxSize) {
			throw new IllegalStateException(
					"Cannot add another PokemonInstance because this team has reached its maximum size of "
							+ maxSize);
		}
		return list.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	/**
	 * If there is enough space left in the team, this method adds the given
	 * Collection of PokemonInstances to the end of this team, as specified in
	 * {@link List#addAll(Collection)}. Otherwise throws an
	 * {@link IllegalStateException}.
	 * 
	 * @throws IllegalStateException
	 *             If there is not enough space left in the team to add all
	 *             elements from the Collection, i.e. if size()+c.size() >=
	 *             maxSize
	 * @see List#add(Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends PokemonInstance> c)
			throws IllegalStateException {
		if (list.size() + c.size() >= maxSize) {
			throw new IllegalStateException(
					"Cannot add other PokemonInstances because this would exceed the team's maximum size of "
							+ maxSize);
		}
		return list.addAll(c);
	}

	/**
	 * If there is enough space left in the team, this method adds the given
	 * Collection of PokemonInstances at specified index to this team, as
	 * specified in {@link List#addAll(Collection)}. Otherwise throws an
	 * {@link IllegalStateException}.
	 * 
	 * @throws IllegalStateException
	 *             If there is not enough space left in the team to add all
	 *             elements from the Collection, i.e. if size()+c.size() >=
	 *             maxSize
	 * @see List#addAll(int, Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends PokemonInstance> c) {
		if (list.size() + c.size() >= maxSize) {
			throw new IllegalStateException(
					"Cannot add other PokemonInstances because this would exceed the team's maximum size of "
							+ maxSize);
		}
		return list.addAll(index, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	@Override
	public PokemonInstance get(int index) {
		return list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public PokemonInstance set(int index, PokemonInstance element) {
		return list.set(index, element);
	}

	/**
	 * If there is space left in the team, this method adds the given
	 * PokemonInstance at specified index to this team, as specified in
	 * {@link List#add(Object)}. Otherwise throws an
	 * {@link IllegalStateException}.
	 * 
	 * @throws IllegalStateException
	 *             If there is no space left in the team.
	 * @see List#add(int, PokemonInstance)
	 */
	@Override
	public void add(int index, PokemonInstance element) {
		if (list.size() >= maxSize) {
			throw new IllegalStateException(
					"Cannot add another PokemonInstance because this team has reached its maximum size of "
							+ maxSize);
		}
		list.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	@Override
	public PokemonInstance remove(int index) {
		return list.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<PokemonInstance> listIterator() {
		return list.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<PokemonInstance> listIterator(int index) {
		return list.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<PokemonInstance> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
