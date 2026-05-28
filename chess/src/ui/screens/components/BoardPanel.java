package ui.screens.components;

import javax.swing.*;

import logic.Location;
import logic.pieces.Pawn;
import ui.Controller;

public class BoardPanel extends JLayeredPane {
    private Controller controller;

    private BoardLayer boardLayer;
    private PromotionLayer promotionLayer;

    public BoardPanel(Controller controller) {
        super();

        this.controller = controller;

        boardLayer = new BoardLayer(controller);

        add(boardLayer);

        setPreferredSize(boardLayer.getPreferredSize());
    }

    @Override
    public void doLayout() {
        boardLayer.setBounds(0, 0, getWidth(), getHeight());
    }

    public void updateBoard() {
        boardLayer.updateBoard();
    }

    public void startPromotion(Pawn pawn, Location origin, Location target) {
        PromotionLayer promotionLayer = new PromotionLayer(controller, origin, target, pawn, boardLayer.getIcons());
        add("promotionLayer", promotionLayer);

        promotionLayer.setBounds(0, 0, getWidth(), getHeight());
    }

    public void closePromotion() {
        remove(promotionLayer);
    }
}
