package biblioteque;

import java.util.ArrayList;
import java.util.List;

public class BorrowingHistory {
    // Classe interne Node pour la liste chaînée
    private static class Node {
        String emprunteur;
        String dateEmprunt;
        Node next;

        Node(String emprunteur, String dateEmprunt) {
            this.emprunteur = emprunteur;
            this.dateEmprunt = dateEmprunt;
            this.next = null;
        }
    }

    private Node head; // Premier élément de la liste
    private int size;  // Taille de la liste

    public BorrowingHistory() {
        this.head = null;
        this.size = 0;
    }

    // 1. Ajouter un emprunt
    public void ajouterEmprunt(String emprunteur, String date) {
        Node nouveauNode = new Node(emprunteur, date);

        if (head == null) {
            head = nouveauNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = nouveauNode;
        }
        size++;
        System.out.println("✓ Emprunt ajouté pour: " + emprunteur);
    }

    // 2. Afficher tous les emprunts
    public void afficherHistorique() {
        if (head == null) {
            System.out.println("Aucun emprunt enregistré.");
            return;
        }

        System.out.println("\n=== HISTORIQUE DES EMPRUNTS (" + size + " emprunts) ===");
        Node current = head;
        int compteur = 1;

        while (current != null) {
            System.out.println(compteur + ". " + current.emprunteur + " - " + current.dateEmprunt);
            current = current.next;
            compteur++;
        }
    }

    // 3. Obtenir la liste des emprunteurs (pour interface)
    public List<String> getEmprunteurs() {
        List<String> emprunteurs = new ArrayList<>();
        Node current = head;

        while (current != null) {
            emprunteurs.add(current.emprunteur);
            current = current.next;
        }

        return emprunteurs;
    }

    // 4. Vérifier si un emprunteur a déjà emprunté
    public boolean aDejaEmprunte(String emprunteur) {
        Node current = head;

        while (current != null) {
            if (current.emprunteur.equalsIgnoreCase(emprunteur)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // 5. Nombre d'emprunts
    public int getNombreEmprunts() {
        return size;
    }

    // 6. Supprimer le dernier emprunt (retour de livre)
    public boolean retournerLivre() {
        if (head == null) {
            return false;
        }

        if (head.next == null) {
            System.out.println("✓ Livre retourné par: " + head.emprunteur);
            head = null;
        } else {
            Node current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            System.out.println("✓ Livre retourné par: " + current.next.emprunteur);
            current.next = null;
        }

        size--;
        return true;
    }
}