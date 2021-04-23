package com.example.myapplication;

public class Allergy {

<<<<<<< Updated upstream
        private String None, Peanut;
        public Allergy(){
        }
        public Allergy(String none, String peanut){
            None = none;
            Peanut = peanut;
=======
        private String None, Peanut, Dairy;
        public Allergy(){
        }
        public Allergy(String none, String peanut, String dairy){
            None = none;
            Peanut = peanut;
            Dairy = dairy;
>>>>>>> Stashed changes
        }
        public String getNone() {
            return None;
        }

        public void setNone(String none) {
            None = none;
        }

        public String getPeanut() {
            return Peanut;
        }

        public void setPeanut(String peanut) {
            Peanut = peanut;
        }

<<<<<<< Updated upstream

=======
        public String getDairy() {
        return Dairy;
    }

        public void setDairy(String dairy) {
        Dairy = dairy;
    }
>>>>>>> Stashed changes
}
