package de.darthpumpkin.pkmnlib;

import java.util.*;

// TODO think of a more generic name that is applicable to e.g. move sets.

/**
 * Generic abstraction of a pokemon team that allows for the reuse for similar
 * data structures, e.g. a move set. A team can be seen as a list without
 * duplicates (because a pokemon cannot be in a team twice) or as an indexable
 * ordered set. Furthermore, a team implements the swap operation known from
 * {@link Collections#swap(List, int, int)}.
 * <p>
 * A team has a fixed maximum size (6 being the default in pokemon games) and
 * must not allow null elements. This implies that all elements are always at
 * the head of the team (i.e. no null "gap" in between). Accordingly, an element
 * inserted at index n will actually be located at index m if m &lt;= n where m
 * is the team's size.
 * <p>
 * Unless stated otherwise, methods will not raise exceptions when attempted to
 * insert a null or duplicate element; instead, the return value (where
 * applicable) are used to indicate that the team did not change as the result
 * of the method call. Also, no exception is thrown when indexed by a number
 * greater than or equal to this team's size, as long as it is lower than the
 * maximum size.
 * 
 * @author Dominik Fay
 * 
 * @param <E>
 *            the type of elements maintained by this team.
 */
public interface Team<E> extends Set<E> {

    /**
     * Adds the specifed element to the end of this team if it is not already
     * present and if there is enough space left in this team.
     * 
     * @param e
     *            element to be added
     * @return <tt>true</tt> if this team changed as a result of the call
     * @throws ClassCastException
     *             if the class of an element of the specified collection
     *             prevents it from being added to this list
     * @throws NullPointerException
     *             if e == null. Teams do not allow null elements.
     * @throws MaximumSizeExceededException
     *             if {@link #size()} > {@link #maximumSize()} after e had been
     *             inserted.
     */
    @Override
    boolean add(E e);

    /**
     * Inserts the specified element into this team at the specified position,
     * if it is not already present and if there is enough space left in this
     * team. Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices). If index >
     * {@link #size()}, e will be inserted at the end of this team instead of at
     * the specified index.
     * 
     * @param index
     *            index at which the specified element is to be inserted.
     * @param e
     *            element to be inserted
     * @return <tt>true</tt> if this team changed as a result of the call
     * @throws NullPointerException
     *             if e == null. Teams do not allow null elements.
     * @throws MaximumSizeExceededException
     *             if {@link #size()} > {@link #maximumSize()} after e had been
     *             inserted.
     * @throws IndexOutOfBoundsException
     *             if (<tt>index &lt; 0 || index &gt;=
     *             {@link #maximumSize()})</tt>
     * @return true if the team did not already contain the given element.
     * @see List#add(int, Object)
     */
    boolean add(int index, E e);

    /**
     * Appends all of the elements in the specified collection to the end of
     * this team, if not already present and if there is enough space left in
     * this team, in the order that they are returned by the specified
     * collection's iterator. Null elements are ignored. The behavior of this
     * operation is undefined if the specified collection is modified while the
     * operation is in progress.
     *
     * @param c
     *            collection containing elements to be added to this team
     * @return <tt>true</tt> if this team changed as a result of the call
     * @throws ClassCastException
     *             if the class of an element of the specified collection
     *             prevents it from being added to this list
     * @throws NullPointerException
     *             if the specified collection is null
     * @throws MaximumSizeExceededException
     *             if {@link #size()} > {@link #maximumSize()} after e had been
     *             inserted.
     * @see List#addAll(Collection)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Inserts all of the elements in the specified collection at the specified
     * position, if not already present and if there is enough space left in
     * this team, in the order that they are returned by the specified
     * collection's iterator. Shifts the element currently at that position (if
     * any) and any subsequent elements to the right (increases their indices).
     * If index > {@link #size()}, the elements will be inserted at the end of
     * this team instead of at the specified index. The new elements will appear
     * in this list in the order that they are returned by the specified
     * collection's iterator. Null elements are ignored. The behavior of this
     * operation is undefined if the specified collection is modified while the
     * operation is in progress.
     *
     * @param index
     *            index at which to insert the first element from the specified
     *            collection
     * @param c
     *            collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws ClassCastException
     *             if the class of an element of the specified collection
     *             prevents it from being added to this list
     * @throws NullPointerException
     *             if the specified collection is null
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             <tt>index &lt; 0 || index &gt; maximumSize()</tt>)
     * @throws MaximumSizeExceededException
     *             if {@link #size()} > {@link #maximumSize()} after e had been
     *             inserted.
     * @see List#addAll(int, Collection)
     */
    boolean addAll(int index, Collection<? extends E> c);

    /**
     * Returns the element at the specified position in this team.
     * 
     * @param index
     *            index of the element to return
     * @return the element at the specified position in this team.
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index &lt; 0 || index &gt;=
     *             {@link #maximumSize()}.
     */
    E get(int index);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element. In contrast to {@link #add(int, Object)}, this method
     * will throw an exception if the specified element is null or already in
     * this team.
     *
     * @param index
     *            index of the element to replace
     * @param element
     *            element to be stored at the specified position
     * @return the element previously at the specified position or null if
     *         <tt>index &gt;= size()</tt>
     * @throws IllegalArgumentException
     *             if <tt>element</tt> is already present in this team
     * @throws NullPointerException
     *             if the specified element is null
     * @throws ClassCastException
     *             if the class of the specified element prevents it from being
     *             added to this list
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             <tt>index &lt; 0 || index &gt;= maximumSize()</tt>)
     */
    E set(int index, E element);

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index
     *            the index of the element to be removed
     * @return the element previously at the specified position or null if
     *         <tt>index &gt;= size()</tt>
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             <tt>index &lt; 0 || index &gt;= maximumSize()</tt>)
     */
    E remove(int index);

    /**
     * Returns an iterator over the elements in this team in proper sequence.
     *
     * @return an iterator over the elements in this team in proper sequence
     */
    @Override
    Iterator<E> iterator();

    /**
     * Returns an array containing all of the elements in this team in proper
     * sequence (from first to last element).
     *
     * <p>
     * The returned array will be "safe" in that no references to it are
     * maintained by this team. (In other words, this method must allocate a new
     * array even if this team is backed by an array). The caller is thus free
     * to modify the returned array.
     *
     * <p>
     * This method acts as bridge between array-based and collection-based APIs.
     *
     * @return an array containing all of the elements in this team in proper
     *         sequence
     */
    @Override
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this team in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array. If the team fits in the specified
     * array, it is returned therein. Otherwise, a new array is allocated with
     * the runtime type of the specified array and the size of this team.
     *
     * <p>
     * If the team fits in the specified array with room to spare (i.e., the
     * array has more elements than the team), the element in the array
     * immediately following the end of the team is set to <tt>null</tt>.
     *
     * <p>
     * Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs. Further, this method allows
     * precise control over the runtime type of the output array, and may, under
     * certain circumstances, be used to save allocation costs.
     *
     * <p>
     * Suppose <tt>x</tt> is a team known to contain only PokemonInstances. The
     * following code can be used to dump the team into a newly allocated array
     * of <tt>PokemonInstance</tt>:
     *
     * <pre>
     * PokemonInstance[] y = x.toArray(new PokemonInstance[0]);
     * </pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a
     *            the array into which the elements of this team are to be
     *            stored, if it is big enough; otherwise, a new array of the
     *            same runtime type is allocated for this purpose.
     * @return an array containing the elements of this team
     * @throws ArrayStoreException
     *             if the runtime type of the specified array is not a supertype
     *             of the runtime type of every element in this team
     * @throws NullPointerException
     *             if the specified array is null
     */
    @Override
    <T> T[] toArray(T[] a);

    /**
     * Returns the index of the specified element in this team, or -1 if this
     * list does not contain the element. More formally, returns the index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o
     *            element to search for
     * @return the index of the specified element in this team, or -1 if this
     *         team does not contain the element
     * @throws ClassCastException
     *             if the type of the specified element is incompatible with
     *             this team (
     *             <a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException
     *             if the specified element is null and this list does not
     *             permit null elements (
     *             <a href="Collection.html#optional-restrictions">optional</a>)
     */
    int indexOf(Object o);

    /**
     * Returns the upper bound for the size of this team.
     *
     * @return the upper bound for the size of this team.
     */
    int maximumSize();

    /**
     * Switches the positions of two elements in this team, such that a call to
     * get(index1) beforehand would return get(index2) afterwards, and vice
     * versa.
     * 
     * @param index1
     *            index of the first element
     * @param index2
     *            index of the second element
     * @throws IndexOutOfBoundsException
     *             if index1 < 0 or index2 < 0 or index1 >=
     *             {@link #maximumSize()} or index2 >= {@link #maximumSize()}.
     */
    void swap(int index1, int index2);

    /**
     * Returns a read-only list view of the elements in this team. Any Changes
     * made to this team after calling this method will also affect the returned
     * list. The returned list has the same size as this team and its iterator
     * will yield its elements in the same order as this team's iterator. The
     * list implements {@link RandomAccess}.
     * 
     * @return the list view of the elements in this team
     */
    List<E> asList();

}
