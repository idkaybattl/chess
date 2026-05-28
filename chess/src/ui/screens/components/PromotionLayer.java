package ui.screens.components;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import logic.ChessColor;
import logic.Location;
import logic.pieces.*;
import ui.Controller;

public class PromotionLayer extends JPanel {
    private Controller controller;

    private Location square;
    private Location origin;
    private Pawn pawn;
    private ChessColor player;

    public PromotionLayer(Controller controller, Location origin, Location square, Pawn pawn,
            EnumMap<ChessColor, HashMap<String, Image>> icons) {
        super(new GridBagLayout());

        this.controller = controller;

        this.origin = origin;
        this.square = square;
        this.pawn = pawn;
        player = pawn.getColor();

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonRow.setOpaque(false);

        ArrayList<JButton> buttons = new ArrayList<>();

        buttons.add(new JButton("queen", new ImageIcon(icons.get(player).get("queen"))));
        buttons.add(new JButton("rook", new ImageIcon(icons.get(player).get("rook"))));
        buttons.add(new JButton("bishop", new ImageIcon(icons.get(player).get("bishop"))));
        buttons.add(new JButton("knight", new ImageIcon(icons.get(player).get("knight"))));

        for (JButton button : buttons) {
            button.addActionListener(e -> promotion(button.getText()));
            buttonRow.add(button);
        }

        add(buttonRow);

        setOpaque(false);
    }

    private void promotion(String type) {
        Class<? extends Promotable> promotingClass;
        switch (type) {
            case "queen":
                promotingClass = Queen.class;
            case "rook":
                promotingClass = Rook.class;
            case "knight":
                promotingClass = Knight.class;
            case "bishop":
                promotingClass = Bishop.class;
            default:
                promotingClass = Queen.class;
        }
        controller.promote(pawn, origin, square, promotingClass);
    }
}
