package biblioteque;

import java.util.ArrayList;
import java.util.List;

public class ActivityStack {
    private List<String> stack;
    private int capacity;

    public ActivityStack(int capacity) {
        this.stack = new ArrayList<>();
        this.capacity = capacity;
    }

    public ActivityStack() {
        this(10); // Capacité par défaut
    }

    // Empiler une activité
    public void push(String activity) {
        if (stack.size() >= capacity) {
            stack.remove(0); // Retirer le plus ancien
        }
        stack.add(activity);
    }

    // Dépiler
    public String pop() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    // Voir le sommet sans dépiler
    public String peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    // Vérifier si vide
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Taille de la pile
    public int size() {
        return stack.size();
    }

    // Afficher toutes les activités
    public void display() {
        if (stack.isEmpty()) {
            System.out.println("Aucune activité.");
            return;
        }

        System.out.println("Activités récentes (du plus récent au plus ancien):");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println("  • " + stack.get(i));
        }
    }
}