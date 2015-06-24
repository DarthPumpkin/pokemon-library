package de.darthpumpkin.pkmnlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List of {@link PokemonInstance} with a predefined maximum size (6 per
 * default) representing a player's team. A PokeTeam does not allow null
 * elements. This implies that all pokemons will always be at the head of the
 * team.
 * 
 * @author dominik
 * 
 */
public final class PokeTeam implements List<PokemonInstance> {

	public static final int DEFAULT_MAX_SIZE = 6;

	private final List<PokemonInstance> list;
	private final int maxSize;

	/**
	 * Creates a new empty team with {@value #DEFAULT_MAX_SIZE} free slots.
	 */
	public PokeTeam() {
		this(DEFAULT_MAX_SIZE);
	}

	/**
	 * Creates a new empty team using the specified number as the upper limit of
	 * pokemons in this team.
	 * 
	 * @param maxSize
	 *            Maximum number of pokemons in this team.
	 * @throws IllegalArgumentException
	 *             If maxSize < 0
	 */
	public PokeTeam(int maxSize) {
		this(new ArrayList<PokemonInstance>(), maxSize);
	}

	/**
	 * Creates a new team containing the elements of the specified Collection,
	 * in the order they are returned by the Collection's iterator, except for
	 * null elements. Sets {@value #DEFAULT_MAX_SIZE} as the maximum number of
	 * pokemons in this team.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this team.
	 * @throws MaximumSizeExceededException
	 *             if coll.size() > {@value #DEFAULT_MAX_SIZE}
	 */
	public PokeTeam(Collection<? extends PokemonInstance> coll) {
		this(coll, DEFAULT_MAX_SIZE);
	}

	/**
	 * Creates a new team containing the elements of the specified Collection,
	 * in the order they are returned by the Collection's iterator, except for
	 * null elements. Sets the specified number as the upper limit of pokemons
	 * in this team.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this team.
	 * @param maxSize
	 *            Maximum number of pokemons in this team.
	 * @throws IllegalArgumentException
	 *             if maxSize < 0
	 * @throws MaximumSizeExceededException
	 *             if coll.size() > maxSize
	 */
	public PokeTeam(Collection<? extends PokemonInstance> coll, int maxSize) {
		if (maxSize < 0) {
			throw new IllegalArgumentException(
					"maxSize must be a positive int, but was " + maxSize);
		}
		this.maxSize = maxSize;
		int initialCapacity = maxSize < 10 ? maxSize : 10;
		list = new ArrayList<>(initialCapacity);
		addAll(coll);
	}

	/**
	 * Returns the upper limit for this team's size.
	 */
	public int getMaxSize() {
		return this.maxSize;
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
	 * {@link List#add(Object)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is no space left in the team.
	 * @throws NullPointerException
	 *             if e == null
	 * @see List#add(PokemonInstance)
	 */
	@Override
	public boolean add(PokemonInstance e) {
		add(list.size(), e);
		return true;
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
	 * {@link List#addAll(Collection)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}. Null elmements are not addded to
	 * the team.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is not enough space left in the team to add all
	 *             elements from the Collection, i.e. if size()+c.size() >=
	 *             maxSize
	 * @see List#add(Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends PokemonInstance> c) {
		return addAll(list.size(), c);
	}

	/**
	 * If there is enough space left in the team, this method adds the given
	 * Collection of PokemonInstances at specified index to this team, as
	 * specified in {@link List#addAll(Collection)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}. Null elmements are not addded to
	 * the team.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is not enough space left in the team to add all
	 *             elements from the Collection, i.e. if size()+c.size() >=
	 *             maxSize
	 * @see List#addAll(int, Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends PokemonInstance> c) {
		if (c.isEmpty()) {
			return false;
		}
		Collection<? extends PokemonInstance> cWithoutNull = new ArrayList<>(c);
		cWithoutNull.removeAll(Collections.singleton(null));
		if (list.size() + cWithoutNull.size() >= maxSize) {
			throw new MaximumSizeExceededException(maxSize, list.size(),
					c.size());
		}
		return list.addAll(index, cWithoutNull);
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
	 * {@link List#add(Object)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is no space left in the team.
	 * @throws NullPointerException
	 *             if element == null
	 * @see List#add(int, PokemonInstance)
	 */
	@Override
	public void add(int index, PokemonInstance element) {
		if (element == null) {
			throw new NullPointerException(
					"A Team does not store null elements");
		}
		addAll(index, Collections.singleton(element));
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
