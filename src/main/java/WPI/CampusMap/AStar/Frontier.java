package WPI.CampusMap.AStar;

import java.util.Comparator;
import java.util.TreeSet;

import Exceptions.FrontierEmpty;

public class Frontier<Item> {
	private TreeSet<Item> tree;

	public static final Comparator<Node> stdNodeComp = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			if (n1.getCurrentScore() < n2.getCurrentScore()) {
				return -1;
			} else if (n1.getCurrentScore() > n2.getCurrentScore()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	public Frontier(Comparator comp) {
		this.tree = new TreeSet<Item>(comp);
	}

	public TreeSet<Item> getTree() {
		return tree;
	}

	public void setTree(TreeSet<Item> tree) {
		this.tree = tree;
	}

	public void add(Item newItem) {
		this.tree.add(newItem);
		System.out.println(this.tree.size());
	}

	public Item getNext() throws FrontierEmpty {
		if (!(this.tree.isEmpty())) {
			Item temp = this.tree.first();

			this.tree.remove(temp);
			return temp;
		} else {
			throw new FrontierEmpty("Cannot get from an empty Frontier");
		}
	}

	public boolean isEmpty() {
		return this.tree.isEmpty();
	}

	public int size() {
		return this.tree.size();
	}

}
