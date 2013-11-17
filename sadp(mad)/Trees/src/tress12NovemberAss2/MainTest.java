package tress12NovemberAss2;

import java.util.Iterator;


public class MainTest<T> {


	private static BinaryTree<Integer> bitree;

	public static void main(String[] args){
		bitree = new BinaryTree<>();

		//root
		bitree.addRoot(45);

		//left side
		//node level 1 left
		BinaryNodeI<Integer> nodelevel1left = bitree.insertLeft(bitree.root(), 22);

		//node level 2 left
		BinaryNodeI<Integer> nodelevel2left = bitree.insertLeft(nodelevel1left, 11);
		bitree.insertRight(nodelevel2left, 15);

		//node level 2 right
		BinaryNodeI<Integer> nodelevel2right = bitree.insertRight(nodelevel1left, 30);
		bitree.insertLeft(nodelevel2right, 25);


		//Right side
		//node level 1 right
		BinaryNodeI<Integer> nodelevel1right = bitree.insertRight(bitree.root(), 77);

		//node level 2 left
		BinaryNodeI<Integer> nodelevel2rightright = bitree.insertRight(nodelevel1right, 90);
		bitree.insertLeft(nodelevel2rightright, 88);


		System.out.println(heightOfTree(bitree.root()));

		//preorder call
		System.out.println("--- preorder ---");
		preorder(bitree.root());
		System.out.println("--- preorder ---");


		//inorder call
		System.out.println("--- inorder ---");
		inorder(bitree.root());
		System.out.println("--- inorder ---");


		//postorder call
		System.out.println("--- postorder ---");
		postorder(bitree.root());
		System.out.println("--- postorder ---");
		
		System.out.println("\nDepth first");
		Iterator<Integer> it = bitree.elements();
		while(it.hasNext()){
			System.out.println("Next Node " + it.next());
		}
		System.out.println("/Depth first");
		
		System.out.println("\nBreadth first");
		Iterator<Integer> it2 = bitree.elementsBreadthFirst();
		while(it2.hasNext()){
			System.out.println("Next Node " + it2.next());
		}

	}


	public static int heightOfTree(BinaryNodeI<Integer> node){
		if(node == null) return 0;
		else return 1 + Math.max(heightOfTree(bitree.left(node)), heightOfTree(bitree.right(node)));
	}

	public static void preorder(BinaryNodeI<Integer> node){
		if(node != null){
			System.out.println(node.getData());
			preorder(bitree.left(node));
			preorder(bitree.right(node));
		}
	}

	public static void inorder(BinaryNodeI<Integer> node){
		if(node != null){
			inorder(bitree.left(node));
			System.out.println(node.getData());
			inorder(bitree.right(node));
		}
	}

	public static void postorder(BinaryNodeI<Integer> node){
		if(node != null){
			postorder(bitree.left(node));
			postorder(bitree.right(node));
			System.out.println(node.getData());
		}
	}
}
