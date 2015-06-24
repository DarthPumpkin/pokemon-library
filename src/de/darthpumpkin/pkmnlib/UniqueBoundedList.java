package de.darthpumpkin.pkmnlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List with a predefined maximum size, not allowing multiple references to the
 * same object and not allowing null elements. This can be considered a generic
 * abstraction of a pokemon team, but it is also applicable for a move list and
 * similar data structures.<br>
 * <br>
 * The constraint of no multiple refrences can be described more explicitly as:
 * This list does not contain any two elements e1 and e2 such that e1 == e2. It
 * does not matter whether or not e1.equals(e2).<br>
 * The constraint of not allowing null elements implies that all elements will
 * always be at the head of the list.
 * 
 * @author dominik
 * 
 */
public final class UniqueBoundedList<E> implements List<E> {

	public static final int DEFAULT_MAX_SIZE = 6;

	private final List<E> list;
	private final int maxSize;

	/**
	 * Creates a new empty list with {@value #DEFAULT_MAX_SIZE} free slots.
	 */
	public UniqueBoundedList() {
		this(DEFAULT_MAX_SIZE);
	}

	/**
	 * Creates a new empty list using the specified number as the upper limit of
	 * elements in this list.
	 * 
	 * @param maxSize
	 *            Maximum number of elements in this list.
	 * @throws IllegalArgumentException
	 *             If maxSize < 0
	 */
	public UniqueBoundedList(int maxSize) {
		this(new ArrayList<E>(), maxSize);
	}

	/**
	 * Creates a new list containing the elements of the specified Collection,
	 * in the order they are returned by the Collection's iterator, except for
	 * null elements. Sets {@value #DEFAULT_MAX_SIZE} as the maximum number of
	 * elements in this list.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this list.
	 * @throws MaximumSizeExceededException
	 *             if coll.size() > {@value #DEFAULT_MAX_SIZE}
	 */
	public UniqueBoundedList(Collection<? extends E> coll) {
		this(coll, DEFAULT_MAX_SIZE);
	}

	/**
	 * Creates a new list containing the elements of the specified Collection,
	 * in the order they are returned by the Collection's iterator, except for
	 * null elements. Sets the specified number as the upper limit of elements
	 * in this list.
	 * 
	 * @param coll
	 *            Collection whose elements are to be placed in this list.
	 * @param maxSize
	 *            Maximum number of elements in this list.
	 * @throws IllegalArgumentException
	 *             if maxSize < 0
	 * @throws MaximumSizeExceededException
	 *             if coll.size() > maxSize
	 */
	public UniqueBoundedList(Collection<? extends E> coll, int maxSize) {
		if (maxSize < 0) {
			throw new IllegalArgumentException(
					"maxSize must be a positive int, but was " + maxSize);
		}
		this.maxSize = maxSize;
		/*
		 * Minimize memory allocated by ArrayList: If maxSize < 10, we don't
		 * need to use the ArrayList's default allocation size of 10.
		 */
		int initialCapacity = maxSize < 10 ? maxSize : 10;
		list = new ArrayList<>(initialCapacity);
		addAll(coll);
	}

	/**
	 * Returns the upper limit for this list's size.
	 */
	public int maxSize() {
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
	public Iterator<E> iterator() {
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
	 * If there is space left in this list, this method adds the given element
	 * to the end of this list, as specified in {@link List#add(Object)}.
	 * Otherwise throws a {@link MaximumSizeExceededException}.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is no space left in the list.
	 * @throws NullPointerException
	 *             if e == null
	 * @see List#add(E)
	 */
	@Override
	public boolean add(E e) {
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
	 * If there is enough space left in this list, this method adds the elements
	 * of given Collection to the end of this list, as specified in
	 * {@link #addAll(Collection)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}. Null elmements are not addded to
	 * the list.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is not enough space left in the list to add all
	 *             elements from the Collection, i.e. if size()+c.size() >=
	 *             {@link #maxSize()}
	 * @see List#add(Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return addAll(list.size(), c);
	}

	/**
	 * If there is enough space left in the list, this method adds the elements
	 * of given Collection at specified index to this list, as specified in
	 * {@link #addAll(Collection)}. Otherwise throws a
	 * {@link MaximumSizeExceededException}. Null elmements are not addded to
	 * the list. If index > {@link #size()}, the elements will be appended to
	 * the end of this list. No {@link IndexOutOfBoundsException} is thrown.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is not enough space left in the list to add all
	 *             elements from the Collection, i.e. if {@link #size()}
	 *             +c.size() >= maxSize
	 * @see List#addAll(int, Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (c.isEmpty()) {
			return false;
		}
		Collection<? extends E> cWithoutNull = new ArrayList<>(c);
		cWithoutNull.removeAll(Collections.singleton(null));
		if (list.size() + cWithoutNull.size() >= maxSize) {
			throw new MaximumSizeExceededException(maxSize, list.size(),
					c.size());
		}
		if (index > size()) {
			index = size();
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
	public E get(int index) {
		return list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		return list.set(index, element);
	}

	/**
	 * If there is space left in the list, this method adds the given element at
	 * specified index to this list, as specified in {@link List#add(Object)}.
	 * Otherwise throws a {@link MaximumSizeExceededException}. If index >
	 * {@link #size()}, the elements will be appended to the end of this list.
	 * No {@link IndexOutOfBoundsException} is thrown.
	 * 
	 * @throws MaximumSizeExceededException
	 *             If there is no space left in the list.
	 * @throws NullPointerException
	 *             if element == null
	 * @see List#add(int, E)
	 */
	@Override
	public void add(int index, E element) {
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
	public E remove(int index) {
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
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
