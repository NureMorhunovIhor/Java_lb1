package ua.nure.task1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapViewer {

	private static Object get(Object obj, String fieldName) throws ReflectiveOperationException {
		Field f = obj.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		return f.get(obj);
	}
	private static void printTreeNodes(Object node, int indent) throws ReflectiveOperationException {
		for (int i = 0; i < indent; i++) {
			System.out.print(" ");
		}
		if (indent > 0) {
			System.out.print("-- ");
		}

		System.out.printf("TreeNode|%s=%s|%n",
				((Map.Entry<?, ?>)node).getKey(), ((Map.Entry<?, ?>)node).getValue());

		Object left = get(node, "left");
		Object right = get(node, "right");

		if (left != null) {
			printTreeNodes(left, indent + 2);
		}
		if (right != null) {
			printTreeNodes(right, indent + 2);
		}
	}

	public static void print(Map<?, ?> map, boolean printEmptyBuckets) throws ReflectiveOperationException {
		Object[] ar = (Object[]) get(map, "table");

		System.out.printf("Capacity: %d%n", ar.length);
		System.out.printf("Load factor: %.1f%n", (float) get(map, "loadFactor"));
		System.out.printf("Threshold: %d%n", (int) get(map, "threshold"));
		System.out.println("=============");

		for (int i = 0; i < ar.length; i++) {
			Object o = ar[i];
			if(o == null){
				if(printEmptyBuckets){
					System.out.printf("[%d]null%n", i);
				}
			}
			else{
				if (o.getClass().getSimpleName().equals("Node")) {
					System.out.printf("[%d] List: Node|%s|", i, o);
					Object node = o;
					while (( node = get(node, "next")) != null) {
						System.out.printf(" ==> Node|%s|" , node);
					}
					System.out.println();
				} else if (o.getClass().getSimpleName().equals("TreeNode")) {
					System.out.printf("[%d] Tree: ", i);
					printTreeNodes(o, 0);
				}
			}

		}
	}

}
