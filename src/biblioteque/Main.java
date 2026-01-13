package biblioteque;
import java.util.Scanner;

public class Main {
        // Tableau pour stocker les livres
        private static Livre[] bibliotheque = new Livre[100];
        private static int nombreLivres = 0;

        // Pile pour les activités récentes
        private static String[] pileActivites = new String[10];
        private static int sommetPile = 0;

        // Scanner unique pour tout le programme
        private static final Scanner scanner = new Scanner(System.in);

    // Liste chaînée pour historique d'emprunts
    private static BorrowingHistory historiqueEmprunts = new BorrowingHistory();

        // ========== MÉTHODES POUR LES LIVRES ==========

        // 1. Ajouter un livre
        public static void ajouterLivre(Livre livre) {
            if (nombreLivres < bibliotheque.length) {
                bibliotheque[nombreLivres] = livre;
                nombreLivres++;
                ajouterActivite("Ajout: " + livre.getTitre());
                System.out.println("Livre ajouté avec succès !");
            } else {
                System.out.println("Erreur: Bibliothèque pleine !");
            }
        }

        // 2. Afficher tous les livres
        public static void afficherLivres() {
            if (nombreLivres == 0) {
                System.out.println("Aucun livre dans la bibliothèque.");
                return;
            }
            System.out.println("\n=== LISTE DES LIVRES (" + nombreLivres + " livres) ===");
            for (int i = 0; i < nombreLivres; i++) {
                System.out.println((i+1) + ". " + bibliotheque[i]);
            }
        }

        // 3. Recherche linéaire par titre
        public static void rechercherParTitre() {
            System.out.print("Entrez le titre à rechercher: ");
            String titre = scanner.nextLine();

            boolean trouve = false;
            for (int i = 0; i < nombreLivres; i++) {
                if (bibliotheque[i].getTitre().equalsIgnoreCase(titre)) {
                    System.out.println("✓ Livre trouvé: " + bibliotheque[i]);
                    trouve = true;
                    ajouterActivite("Recherche: " + titre);
                    break;
                }
            }
            if (!trouve) {
                System.out.println("✗ Aucun livre avec ce titre.");
            }
        }

        // 4. Supprimer un livre par ISBN
        public static void supprimerLivre() {
            System.out.print("Entrez l'ISBN du livre à supprimer: ");
            String isbn = scanner.nextLine();

            for (int i = 0; i < nombreLivres; i++) {
                if (bibliotheque[i].getIsbn().equals(isbn)) {
                    // Décalage des éléments
                    for (int j = i; j < nombreLivres - 1; j++) {
                        bibliotheque[j] = bibliotheque[j + 1];
                    }
                    bibliotheque[nombreLivres - 1] = null;
                    nombreLivres--;
                    ajouterActivite("Suppression: ISBN " + isbn);
                    System.out.println("✓ Livre supprimé avec succès !");
                    return;
                }
            }
            System.out.println("✗ Aucun livre avec cet ISBN.");
        }
    // ========== ALGORITHMES DE TRI ==========

    // 5. Tri à bulles
    public static void triBullesParTitre() {
        if (nombreLivres < 2) return;

        for (int i = 0; i < nombreLivres - 1; i++) {
            for (int j = 0; j < nombreLivres - i - 1; j++) {
                if (bibliotheque[j].getTitre().compareToIgnoreCase(bibliotheque[j + 1].getTitre()) > 0) {
                    // Échanger les livres
                    Livre temp = bibliotheque[j];
                    bibliotheque[j] = bibliotheque[j + 1];
                    bibliotheque[j + 1] = temp;
                }
            }
        }
        ajouterActivite("Tri: à bulles par titre");
        System.out.println("✓ Tri à bulles par titre effectué !");
    }

    // 6. Tri par sélection
    public static void triSelectionParAuteur() {
        if (nombreLivres < 2) return;

        for (int i = 0; i < nombreLivres - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nombreLivres; j++) {
                if (bibliotheque[j].getAuteur().compareToIgnoreCase(bibliotheque[minIndex].getAuteur()) < 0) {
                    minIndex = j;
                }
            }
            // Échanger
            Livre temp = bibliotheque[minIndex];
            bibliotheque[minIndex] = bibliotheque[i];
            bibliotheque[i] = temp;
        }
        ajouterActivite("Tri: par sélection par auteur");
        System.out.println("✓ Tri par sélection par auteur effectué !");
    }

    // 7. Tri rapide (Quick Sort) - méthode récursive
    public static void triRapideParAnnee() {
        triRapideRecursif(0, nombreLivres - 1);
        ajouterActivite("Tri: rapide par année");
        System.out.println("✓ Tri rapide par année effectué !");
    }

    private static void triRapideRecursif(int debut, int fin) {
        if (debut < fin) {
            int pivotIndex = partitionner(debut, fin);
            triRapideRecursif(debut, pivotIndex - 1);
            triRapideRecursif(pivotIndex + 1, fin);
        }
    }

    private static int partitionner(int debut, int fin) {
        // ESSAIE UNE DE CES DEUX OPTIONS :
        // Option 1 (si ta méthode s'appelle getAnnee()):
        int anneePivot = bibliotheque[fin].getAnneePublication();
        // Option 2 (si ta méthode s'appelle getPublicationYear()):
        // int anneePivot = bibliotheque[fin].getPublicationYear();
        // Option 3 (si ton attribut est public - déconseillé):
        // int anneePivot = bibliotheque[fin].anneePublication;

        int i = debut - 1;

        for (int j = debut; j < fin; j++) {
            // Même correction ici :
            if (bibliotheque[j].getAnneePublication() <= anneePivot) { // Change getAnnee() selon ton cas
                i++;
                // Échanger
                Livre temp = bibliotheque[i];
                bibliotheque[i] = bibliotheque[j];
                bibliotheque[j] = temp;
            }
        }

        // Placer le pivot à sa position finale
        Livre temp = bibliotheque[i + 1];
        bibliotheque[i + 1] = bibliotheque[fin];
        bibliotheque[fin] = temp;

        return i + 1;
    }

    // 8. Recherche binaire (nécessite un tableau trié par titre d'abord)
    public static void rechercheBinaireParTitre() {
        // Vérifier si le tableau est trié par titre
        System.out.println("Note: La recherche binaire nécessite un tableau trié par titre.");
        System.out.println("Voulez-vous trier d'abord ? (O/N)");
        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("O")) {
            triBullesParTitre();
        }

        System.out.print("Entrez le titre à rechercher: ");
        String titre = scanner.nextLine();

        int gauche = 0;
        int droite = nombreLivres - 1;
        boolean trouve = false;

        while (gauche <= droite) {
            int milieu = (gauche + droite) / 2;
            int comparaison = bibliotheque[milieu].getTitre().compareToIgnoreCase(titre);

            if (comparaison == 0) {
                System.out.println("✓ Livre trouvé (recherche binaire): " + bibliotheque[milieu]);
                trouve = true;
                ajouterActivite("Recherche binaire: " + titre);
                break;
            } else if (comparaison < 0) {
                gauche = milieu + 1;
            } else {
                droite = milieu - 1;
            }
        }

        if (!trouve) {
            System.out.println("✗ Aucun livre avec ce titre.");
        }
    }

    // ========== GESTION DES EMPRUNTS ==========

    // 10. Ajouter un emprunt
    public static void ajouterEmprunt() {
        System.out.println("\n--- NOUVEL EMPRUNT ---");
        System.out.print("Nom de l'emprunteur: ");
        String emprunteur = scanner.nextLine();
        System.out.print("Date (JJ/MM/AAAA): ");
        String date = scanner.nextLine();

        historiqueEmprunts.ajouterEmprunt(emprunteur, date);
        ajouterActivite("Emprunt: " + emprunteur);
    }

    // 11. Afficher l'historique des emprunts
    public static void afficherHistoriqueEmprunts() {
        historiqueEmprunts.afficherHistorique();
    }

    // 12. Retourner un livre
    public static void retournerLivre() {
        if (historiqueEmprunts.retournerLivre()) {
            ajouterActivite("Retour de livre");
        } else {
            System.out.println("Aucun emprunt à retourner.");
        }
    }

        // ========== MÉTHODES POUR LA PILE D'ACTIVITÉS ==========
        public static void ajouterActivite(String activite) {
            if (sommetPile < pileActivites.length) {
                pileActivites[sommetPile] = activite;
                sommetPile++;
            } else {
                // Décaler les anciennes activités (FIFO)
                for (int i = 0; i < pileActivites.length - 1; i++) {
                    pileActivites[i] = pileActivites[i + 1];
                }
                pileActivites[pileActivites.length - 1] = activite;
            }
        }

        public static void afficherActivites() {
            System.out.println("\n=== ACTIVITÉS RÉCENTES ===");
            if (sommetPile == 0) {
                System.out.println("Aucune activité enregistrée.");
                return;
            }
            for (int i = sommetPile - 1; i >= 0; i--) {
                System.out.println("- " + pileActivites[i]);
            }
        }

    // ========== MENU PRINCIPAL COMPLET ==========
    public static void menuPrincipal() {
        int choix;

        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("       SYSTÈME DE GESTION DE BIBLIOTHÈQUE");
            System.out.println("=".repeat(50));
            System.out.println("GESTION DES LIVRES:");
            System.out.println("  1. Ajouter un livre");
            System.out.println("  2. Afficher tous les livres");
            System.out.println("  3. Rechercher un livre (linéaire)");
            System.out.println("  4. Supprimer un livre par ISBN");
            System.out.println("-".repeat(50));
            System.out.println("ALGORITHMES DE TRI:");
            System.out.println("  5. TRI: À bulles (par titre)");
            System.out.println("  6. TRI: Par sélection (par auteur)");
            System.out.println("  7. TRI: Rapide (par année)");
            System.out.println("  8. RECHERCHE: Binaire (par titre)");
            System.out.println("-".repeat(50));
            System.out.println("GESTION DES EMPRUNTS:");
            System.out.println("  9. Ajouter un emprunt");
            System.out.println(" 10. Afficher l'historique des emprunts");
            System.out.println(" 11. Retourner un livre");
            System.out.println("-".repeat(50));
            System.out.println("SYSTÈME:");
            System.out.println(" 12. Voir les activités récentes");
            System.out.println("  0. Quitter");
            System.out.println("=".repeat(50));
            System.out.print("\nVotre choix (0-12): ");

            try {
                choix = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Erreur: Veuillez entrer un nombre !");
                scanner.nextLine();
                choix = -1;
                continue;
            }

            switch (choix) {
                case 1:
                    System.out.println("\n--- AJOUT D'UN LIVRE ---");
                    System.out.print("Titre: ");
                    String titre = scanner.nextLine();
                    System.out.print("Auteur: ");
                    String auteur = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Année: ");
                    int annee = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();

                    ajouterLivre(new Livre(titre, auteur, isbn, annee, genre));
                    break;

                case 2:
                    afficherLivres();
                    break;

                case 3:
                    rechercherParTitre();
                    break;

                case 4:
                    supprimerLivre();
                    break;

                case 5:
                    triBullesParTitre();
                    afficherLivres();
                    break;

                case 6:
                    triSelectionParAuteur();
                    afficherLivres();
                    break;

                case 7:
                    triRapideParAnnee();
                    afficherLivres();
                    break;

                case 8:
                    rechercheBinaireParTitre();
                    break;

                case 9:
                    ajouterEmprunt();
                    break;

                case 10:
                    afficherHistoriqueEmprunts();
                    break;

                case 11:
                    retournerLivre();
                    break;

                case 12:
                    afficherActivites();
                    break;

                case 0:
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("  MERCI D'AVOIR UTILISÉ NOTRE SYSTÈME !");
                    System.out.println("  Statistiques:");
                    System.out.println("  - Livres dans la bibliothèque: " + nombreLivres);
                    System.out.println("  - Emprunts enregistrés: " + historiqueEmprunts.getNombreEmprunts());
                    System.out.println("  - Activités récentes: " + sommetPile);
                    System.out.println("=".repeat(50));
                    break;

                default:
                    System.out.println("❌ Choix invalide ! Veuillez entrer un nombre entre 0 et 12.");
            }
        } while (choix != 0);
    }
        // ========== MÉTHODE MAIN (POINT D'ENTRÉE) ==========
        public static void main(String[] args) {
            System.out.println("=".repeat(60));
            System.out.println("  SYSTÈME DE GESTION DE BIBLIOTHÈQUE - PROJET COMC-06");
            System.out.println("=".repeat(60));

            // Demander si l'utilisateur veut exécuter les tests
            Scanner choixScanner = new Scanner(System.in);
            System.out.print("\nVoulez-vous exécuter les tests automatiques ? (O/N): ");
            String reponse = choixScanner.nextLine();

            if (reponse.equalsIgnoreCase("O")) {
                executerTests();
                System.out.println("\nAppuyez sur Entrée pour continuer vers le menu principal...");
                choixScanner.nextLine();
            }

            // Initialisation avec des livres d'exemple
            System.out.println("\n📦 Initialisation avec des livres d'exemple...");

            ajouterLivre(new Livre("Le Petit Prince", "Antoine de Saint-Exupéry",
                    "978-207061275-8", 1943, "Conte philosophique"));
            ajouterLivre(new Livre("1984", "George Orwell",
                    "978-207036822-6", 1949, "Science-Fiction"));
            ajouterLivre(new Livre("Harry Potter à l'école des sorciers", "J.K. Rowling",
                    "978-207061236-8", 1997, "Fantasy"));
            ajouterLivre(new Livre("Les Misérables", "Victor Hugo",
                    "978-225301067-6", 1862, "Roman historique"));
            ajouterLivre(new Livre("Voyage au centre de la Terre", "Jules Verne",
                    "978-225301234-2", 1864, "Science-Fiction"));

            System.out.println("✅ " + nombreLivres + " livres ajoutés avec succès !");

            // Démarrer le menu principal
            menuPrincipal();

            // Fermer le scanner
            scanner.close();
            choixScanner.close();
        }
    // ========== TESTS AUTOMATISÉS ==========
    public static void executerTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("               TESTS AUTOMATISÉS");
        System.out.println("=".repeat(60));

        int testsReussis = 0;
        int testsTotaux = 0;

        // Test 1: Ajout de livres
        System.out.println("\n📚 Test 1: Ajout de livres");
        int livresAvant = nombreLivres;
        ajouterLivre(new Livre("Test Livre 1", "Auteur Test", "TEST-001", 2020, "Test"));
        ajouterLivre(new Livre("Test Livre 2", "Auteur Test", "TEST-002", 2021, "Test"));

        if (nombreLivres == livresAvant + 2) {
            System.out.println("   ✅ Ajout de livres: RÉUSSI");
            testsReussis++;
        } else {
            System.out.println("   ❌ Ajout de livres: ÉCHEC");
        }
        testsTotaux++;

        // Test 2: Recherche linéaire
        System.out.println("\n🔍 Test 2: Recherche linéaire");
        boolean trouve = false;
        for (int i = 0; i < nombreLivres; i++) {
            if (bibliotheque[i].getTitre().equals("Test Livre 1")) {
                trouve = true;
                break;
            }
        }
        if (trouve) {
            System.out.println("   ✅ Recherche linéaire: RÉUSSI");
            testsReussis++;
        } else {
            System.out.println("   ❌ Recherche linéaire: ÉCHEC");
        }
        testsTotaux++;

        // Test 3: Tri à bulles
        System.out.println("\n🔄 Test 3: Tri à bulles");
        String titreAvant = bibliotheque[0].getTitre();
        triBullesParTitre();
        String titreApres = bibliotheque[0].getTitre();

        // Vérifier si le tri a changé l'ordre
        if (!titreAvant.equals(titreApres)) {
            System.out.println("   ✅ Tri à bulles: RÉUSSI (ordre modifié)");
            testsReussis++;
        } else {
            System.out.println("   ⚠️  Tri à bulles: Ordre inchangé (peut être normal)");
        }
        testsTotaux++;

        // Test 4: Liste chaînée d'emprunts
        System.out.println("\n📖 Test 4: Liste chaînée d'emprunts");
        int empruntsAvant = historiqueEmprunts.getNombreEmprunts();
        historiqueEmprunts.ajouterEmprunt("Testeur", "01/01/2024");

        if (historiqueEmprunts.getNombreEmprunts() == empruntsAvant + 1) {
            System.out.println("   ✅ Liste chaînée: RÉUSSI");
            testsReussis++;
        } else {
            System.out.println("   ❌ Liste chaînée: ÉCHEC");
        }
        testsTotaux++;

        // Test 5: Pile d'activités
        System.out.println("\n📊 Test 5: Pile d'activités");
        int activitesAvant = sommetPile;
        ajouterActivite("Test activité");

        if (sommetPile > activitesAvant) {
            System.out.println("   ✅ Pile d'activités: RÉUSSI");
            testsReussis++;
        } else {
            System.out.println("   ❌ Pile d'activités: ÉCHEC");
        }
        testsTotaux++;

        // Résumé des tests
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RÉSULTAT DES TESTS: " + testsReussis + "/" + testsTotaux + " réussis");
        System.out.println("=".repeat(60));

        // Nettoyage des tests
        System.out.println("\n🧹 Nettoyage des données de test...");
        supprimerLivreParISBN("TEST-001");
        supprimerLivreParISBN("TEST-002");
    }

    // Méthode utilitaire pour suppression par ISBN (pour les tests)
    private static boolean supprimerLivreParISBN(String isbn) {
        for (int i = 0; i < nombreLivres; i++) {
            if (bibliotheque[i].getIsbn().equals(isbn)) {
                for (int j = i; j < nombreLivres - 1; j++) {
                    bibliotheque[j] = bibliotheque[j + 1];
                }
                bibliotheque[nombreLivres - 1] = null;
                nombreLivres--;
                return true;
            }
        }
        return false;
    }
}
