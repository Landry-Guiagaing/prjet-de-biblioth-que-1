package biblioteque;

public class Livre {
        private String titre;
        private String auteur;
        private String isbn;
        private int annee;
        private String genre;

        public Livre(String titre, String auteur, String isbn, int annee, String genre) {
            this.titre = titre;
            this.auteur = auteur;
            this.isbn = isbn;
            this.annee = annee;
            this.genre = genre;
        }

        // ✅ GETTERS OBLIGATOIRES
        public String getTitre() {
            return titre;
        }

        public String getAuteur() {
            return auteur;
        }

        public String getIsbn() {   // 👈 CELUI-CI MANQUE CHEZ TOI
            return isbn;
        }

        public int getAnneePublication() {
            return annee;
        }

        public String getGenre() {
            return genre;
        }

        @Override
        public String toString() {
            return titre + " | " + auteur + " | ISBN: " + isbn + " | " + annee + " | " + genre;
        }
}