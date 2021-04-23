package com.example.myapplication;

public class Allergy {

        private String None, Peanut;
        public Allergy(){
        }
        public Allergy(String none, String peanut){
            None = none;
            Peanut = peanut;
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


}
