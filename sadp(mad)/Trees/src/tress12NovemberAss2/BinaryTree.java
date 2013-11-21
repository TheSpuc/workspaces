package tress12NovemberAss2;

import java.util.*;

/**
 * Abstrakt datatype: Binary Tree
 * 
 * @version (10-11-2013)
 * @author Margrethe Dybdahl
 */
public class BinaryTree<E> implements BinaryTreeI<E> {

	private int size = 0;
	private BinaryNode root;

	/**
	 * Returnerer antallet af knuder i det bin�re tr�.
	 * 
	 * @return antallet af knuder i tr�et
	 */
	public int size() {
		return size;
	}

	/**
	 * Returnerer om tr�et er tomt
	 * 
	 * @return om tr�et er tomt
	 */

	public boolean isEmpty() {
		return root == null;
	}


	/**
	 * Erstatter objektet indeholdt i knuden n med objektet o
	 * 
	 * @param v
	 *            knuden der skal have nyt element
	 * @param e
	 *            det nye element
	 * @return det objekt der oprindelig var p� v's plads
	 * 
	 */
	public E replace(BinaryNodeI<E> v, E e) {
		BinaryNode node = (BinaryNode) v;
		E result = node.getData();
		node.setData(e);
		return result;
	}

	/**
	 * Returnerer roden af det bin�re tr�.
	 * 
	 * @return roden til tr�et
	 * @exception java.lang.Exception
	 *                hvis tr�et er tomt
	 */
	public BinaryNodeI<E> root() {
		return root;
	}

	/**
	 * Returnerer om v er in intern knude.
	 * 
	 * @param v
	 *            knuden der skal afg�res om er intern
	 * @return true hvis v er intern ellers false
	 */
	public boolean isInternal(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode)v;
		return (node.getLeft()!= null) || (node.getRight()!=null);
	}

	/**
	 * Returnerer om v er in ekstern knude.
	 * 
	 * @param v
	 *            knuden der skal afg�res om er ekstern
	 * @return true hvis n er ekstern ellers false
	 */
	public boolean isExternal(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode) v;
		return (node.getLeft() == null && node.getRight() == null);
	}

	/**
	 * Returnerer om v er roden i tr�et.
	 * 
	 * @param v
	 *            knuden der skal afg�res om er roden
	 * @return true hvis v er roden ellers false
	 */
	public boolean isRoot(BinaryNodeI<E> v) {
		return v.equals(root);
	}

	/**
	 * Opretter og returnerer roden i tr�et med indhold e
	 * 
	 * @return roden til tr�et
	 * @exception hvis
	 *                tr�et ikke er tomt
	 */
	public BinaryNodeI<E> addRoot(E e) {
		root = new BinaryNode(e);
		return root;
	}

	/**
	 * Returnerer det venstre barn til knuden v.
	 * 
	 * @param v
	 *            knuden der skal findes venstre barn til
	 * @return det venstre barn til knuden v
	 * @exception hvis
	 *                det ikke findes
	 */
	public BinaryNodeI<E> left(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode)v;
		return node.getLeft();
	}

	/**
	 * Returnerer det h�jre barn til knuden v.
	 * 
	 * @param v
	 *            knuden der skal findes h�jre barn til
	 * @return det h�jre barn til knuden v.
	 * @exception hvis
	 *                det ikke findes
	 */
	public BinaryNodeI<E> right(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode) v;
		return node.getRight();
	}

	/**
	 * Returnerer om v har et venstre barn
	 * 
	 * @return om v har et venstre barn
	 */
	public boolean hasLeft(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode)v;
		return node.getLeft()!=null;
	}

	/**
	 * Returnerer om v har et h�jre barn
	 * 
	 * @return om v har et venstre barn
	 */
	public boolean hasRight(BinaryNodeI<E> v) {
		BinaryNode node = (BinaryNode) v;
		return node.getRight() != null;
	}

	/**
	 * Opretter og returnerer en ny knude som venstrebarn til v
	 * 
	 * @return ny knude
	 * @exception hvis
	 *                v allerede har et venstre barn
	 * 
	 */
	public BinaryNodeI<E> insertLeft(BinaryNodeI<E> v, E e) {
		BinaryNode node = (BinaryNode)v;
		if (hasLeft(node))
			throw new RuntimeException("Har allerede venstre barn");
		BinaryNode newNode = new BinaryNode(e);
		node.setLeft(newNode);
		size++;
		return newNode;
	}

	/**
	 * Opretter og returnerer en ny knude som h�jrebarn til v
	 * 
	 * @return ny knude
	 * @exception hvis
	 *                v allerede har et h�jre barn
	 */
	public BinaryNodeI<E> insertRight(BinaryNodeI<E> v, E e) {
		BinaryNode node = (BinaryNode) v;
		if(hasRight(node)){
			throw new RuntimeException("Already have right node");
		}
		BinaryNode newNode = new BinaryNode(e);
		node.setRight(newNode);
		size++;
		return newNode;
	}

	/**
	 * Returnerer en iterator med indholdet i det bin�re tr�.
	 * 
	 * @return iterator over knuderne i det bin�re tr�
	 */
	public Iterator<E> elements() {
		return new BinaryIteratorStack();
	}
	
	public Iterator<E> elementsBreadthFirst(){
		return new BinaryIteratorQueue();
	}

	
	//Depth first
	private class BinaryIteratorStack implements Iterator<E>{

		private Stack<BinaryNode> nodes;

		public BinaryIteratorStack(){
			nodes = new Stack<>();
			insertIntoStack(root);
		}

		@Override
		public boolean hasNext() {
			return !nodes.isEmpty();
		}

		@Override
		public E next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			BinaryNode node = nodes.pop();
			E result = node.getData();
			insertIntoStack(node.right);
			insertIntoStack(node.left);
			return result;
		}
		
		private void insertIntoStack(BinaryNode node){
			if(node != null){
				nodes.push(node);
			}
		}
		

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	//Breadth first
	private class BinaryIteratorQueue implements Iterator<E>{
		
		private Queue<BinaryNode> nodes;
		
		public BinaryIteratorQueue(){
			nodes = new ArrayDeque<>();
			insertIntoQueue(root);
		}
		
		@Override
		public boolean hasNext() {
			return !nodes.isEmpty();
		}

		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			BinaryNode node = nodes.poll();
			E result = node.data;
			insertIntoQueue(node.left);
			insertIntoQueue(node.right);
			return result;
		}
		
		private void insertIntoQueue(BinaryNode node){
			if(node != null){
				nodes.add(node);
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public class BinaryNode implements BinaryNodeI<E> {

		private E data;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode() {
			this(null);
		}

		public BinaryNode(E data) {
			this(data, null, null);
		}

		public BinaryNode(E data, BinaryNode left, BinaryNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		@Override
		public E getData() {
			return data;
		}

		public void setData(E e) {
			data = e;
		}

		public BinaryNodeI<E> getLeft() {
			return left;
		}

		public BinaryNodeI<E> getRight() {
			return right;
		}

		public void setLeft(BinaryNodeI<E> left) {
			this.left = (BinaryNode) left;
		}

		public void setRight(BinaryNodeI<E> right) {
			this.right = (BinaryNode) right;
		}
	}
}
