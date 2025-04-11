package Controleur;

import Vue.FenetreLogin;

import javax.swing.*;

public class Main {
    public static void main (String[] args){
        SwingUtilities.invokeLater(FenetreLogin::new);

    }
}
