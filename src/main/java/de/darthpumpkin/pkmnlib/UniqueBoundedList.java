package de.darthpumpkin.pkmnlib;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * List with a predefined maximum size, not allowing multiple references to the
 * same object and not allowing null elements. This can be considered a generic
 * abstraction of a pokemon team, but it is also applicable for a move list and
 * similar data structures.<br>
 * <br>
 * The constraint of no multiple references can be described more explicitly as:
 * This list does not contain any two elements e1 and e2 such that e1 == e2. It
 * does not matter whether or not e1.equals(e2).<br>
 * The constraint of not allowing null elements implies that all elements will
 * always be at the head of the list.
 * 
 * @author dominik
 * 
 */
// TODO rename (BoundedIndexableSet ?)
public final class UniqueBoundedList<E> implements Team<E> {

	public static final int DEFAULT_MAX_SIZE = 6;

	// Actual list with entries in correct order
	private final List<E> list;
	// TODO use set instead of multimap: compare equals() instead of ==
	// Maps a hash value to the list of entries with that hash value (Idea: two
	// entries with identical hash values are likely to be identical)
	private final ListMultimap<Integer, E> hashes;
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
		hashes = ArrayListMultimap.create(initialCapacity, 1);
		addAll(coll);
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
		return hashes.values().contains(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO create a custom iterator that can handle removal properly
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#toArray(java.lang.Object[])
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
		hashes.values().remove(o);
		return list.remove(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return hashes.values().containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return addAll(list.size(), c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO compare equals() istead of ==
		if (c.isEmpty()) {
			return false;
		}
		// Step 1: Remove null elements
		Collection<? extends E> cWithoutNull = Collections
				.synchronizedCollection(new HashSet<E>(c));
		cWithoutNull.removeAll(Collections.singleton(null));

		// Step 2: Remove identical elements
		synchronized (cWithoutNull) {
			REMOVE_DUPLICATE_REFERENCES: for (E newElem : cWithoutNull) {
				List<E> elementsWithSameHashCode = hashes.get(newElem
						.hashCode());
				for (E elemSameHash : elementsWithSameHashCode) {
					if (newElem == elemSameHash) {
						cWithoutNull.remove(newElem);
						continue REMOVE_DUPLICATE_REFERENCES;
					}
				}
			}
		}
		// Step 3: Check size
		if (list.size() + cWithoutNull.size() > maxSize) {
			throw new MaximumSizeExceededException(maxSize, list.size(),
					c.size());
		}
		if (index > size()) {
			index = size();
		}
		// Step 4: Add entries
		for (E e : cWithoutNull) {
			hashes.put(e.hashCode(), e);
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
		hashes.values().removeAll(c);
		return list.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		hashes.values().retainAll(c);
		return list.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		hashes.clear();
		list.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#get(int)
	 */
	@Override
	public E get(int index) {
		// TODO allow size <= indices < maximumSize
		return list.get(index);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		// TODO allow size <= indices < maximumSize
		// TODO IllegalArgumentException if element == null
		E previous = list.set(index, element);
		hashes.remove(previous.hashCode(), previous);
		hashes.put(element.hashCode(), element);
		return previous;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#remove(int)
	 */
	@Override
	public boolean add(int index, E element) {
		if (element == null) {
			throw new NullPointerException(
					"A Team does not store null elements");
		}
		return addAll(index, Collections.singleton(element));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#remove(int)
	 */
	@Override
	public E remove(int index) {
		E removedElement = list.remove(index);
		hashes.remove(removedElement.hashCode(), removedElement);
		return removedElement;
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

	@Override
	public int maximumSize() {
		return maxSize;
	}

	@Override
	public void swap(int index1, int index2) {
		int actualIndex1 = Math.min(index1, size());
		int actualIndex2 = Math.min(index2, size());
		Collections.swap(list, actualIndex1, actualIndex2);
	}

	@Override
	public List<E> asList() {
		return Collections.unmodifiableList(list);
	}
}
